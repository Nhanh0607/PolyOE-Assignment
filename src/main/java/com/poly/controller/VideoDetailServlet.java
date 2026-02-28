package com.poly.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.poly.dao.FavoriteDAO;
import com.poly.dao.VideoDAO;
import com.poly.entity.Favorite;
import com.poly.entity.User;
import com.poly.entity.Video;

@WebServlet("/detail")
public class VideoDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Khởi tạo các DAO cần thiết
    private VideoDAO videoDAO = new VideoDAO();
    private FavoriteDAO favDAO = new FavoriteDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Lấy ID video từ URL (ví dụ: detail?id=KV3)
        String videoId = request.getParameter("id");	
        
        if (videoId != null) {
            // Tìm video trong database 
            Video video = videoDAO.findById(Video.class, videoId);
            
            if (video != null) {
                // === A. TĂNG LƯỢT XEM (VIEWS) ===
                video.setViews(video.getViews() + 1);
                videoDAO.update(video); // Lưu số view mới vào CSDL
                
                request.setAttribute("video", video);// gửi sang detail.jsp
                
                // === B. DANH SÁCH VIDEO LIÊN QUAN (SIDEBAR) ===
                List<Video> related = videoDAO.findAll(Video.class);
                
                // Loại bỏ video đang xem khỏi danh sách liên quan (để không bị lặp)
                related.removeIf(v -> v.getId().equals(video.getId())); 
                
                // Chỉ lấy tối đa 4 video để hiển thị cho đẹp
                if (related.size() > 4) {
                    related = related.subList(0, 4);
                }
                request.setAttribute("relatedVideos", related);
                
                // === C. KIỂM TRA TRẠNG THÁI LIKE (Để đổi màu nút) ===
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                
                if (user != null) {
                    // Lấy danh sách các video mà user này đã thích
                    List<Favorite> myFavs = favDAO.findByUser(user.getId());
                    
                    // Tạo một list chỉ chứa ID các video đã thích
                    List<String> likedVideoIds = new ArrayList<>();
                    for (Favorite f : myFavs) {
                        likedVideoIds.add(f.getVideo().getId());
                    }
                    
                    // Gửi sang JSP để kiểm tra: 
                    // Nếu video hiện tại có ID nằm trong list này -> Hiện nút Unlike
                    request.setAttribute("likedVideoIds", likedVideoIds);
                }
            }
        }
        
        // 2. Điều hướng sang trang giao diện chi tiết
        request.setAttribute("view", "/site/home/detail.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}