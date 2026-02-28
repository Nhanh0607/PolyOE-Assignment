package com.poly.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.poly.dao.FavoriteDAO;
import com.poly.dao.VideoDAO;
import com.poly.entity.Favorite;
import com.poly.entity.User;
import com.poly.entity.Video;

@WebServlet("/index")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VideoDAO videoDAO;
    private FavoriteDAO favDAO;

    public HomeServlet() {
        super();
        this.videoDAO = new VideoDAO();
        this.favDAO = new FavoriteDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
    	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0
        response.setDateHeader("Expires", 0); // Proxies
    	
        // 1. Xử lý Tìm kiếm & Phân trang
        String keyword = request.getParameter("keyword");
        List<Video> videos;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            videos = videoDAO.findByTitle(keyword);
            request.setAttribute("message", "Kết quả tìm kiếm cho: " + keyword);
            request.setAttribute("searchKeyword", keyword);
        } else {
            String pageParam = request.getParameter("page");
            int page = 1;
            try {
                if (pageParam != null) page = Integer.parseInt(pageParam);
            } catch (Exception e) { page = 1; }
            
            int pageSize = 6;
            long totalItems = videoDAO.count();
            int totalPages = (int) Math.ceil((double) totalItems / pageSize);
            
            if (page > totalPages) page = totalPages;
            if (page < 1) page = 1;
            
            videos = videoDAO.findAll(page, pageSize);
            
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
        }
        
        // 2. QUAN TRỌNG: Kiểm tra User đã like video nào chưa
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            // Lấy danh sách các video user này đã thích	
            List<Favorite> myFavs = favDAO.findByUser(user.getId());
            List<String> likedVideoIds = new ArrayList<>();
            for (Favorite f : myFavs) {
                likedVideoIds.add(f.getVideo().getId());
            }
            // Gửi danh sách ID đã like sang JSP để đổi màu nút
            request.setAttribute("likedVideoIds", likedVideoIds);
        }
        
        request.setAttribute("videos", videos);
        request.setAttribute("view", "/site/home/index.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
}