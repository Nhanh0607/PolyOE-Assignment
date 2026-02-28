package com.poly.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;

import com.poly.dao.*;
import com.poly.entity.*;

@WebServlet({
    "/admin", 
    "/admin/videos", "/admin/video/edit", "/admin/video/create", "/admin/video/update", "/admin/video/delete", "/admin/video/reset",
    "/admin/users", "/admin/user/edit", "/admin/user/create", "/admin/user/update", "/admin/user/delete", "/admin/user/reset",
    "/admin/reports"
})
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VideoDAO videoDAO = new VideoDAO();
    private UserDAO userDAO = new UserDAO();
    private StatsDAO statsDAO = new StatsDAO();
    private FavoriteDAO favDAO = new FavoriteDAO();
    private ShareDAO shareDAO = new ShareDAO();

    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String uri = request.getRequestURI();
        String view = "";
        String message = "";
        
        try {
            // --- QUẢN LÝ VIDEO ---
            if (uri.contains("video")) {
                Video video = new Video();
                if (uri.contains("edit")) {
                    String id = request.getParameter("id");
                    video = videoDAO.findById(Video.class, id);
                } else if (uri.contains("create")) {
                    BeanUtils.populate(video, request.getParameterMap());
                    if(request.getParameter("active") != null) video.setActive(Boolean.parseBoolean(request.getParameter("active")));
                    
                    if (videoDAO.findById(Video.class, video.getId()) == null) {
                        videoDAO.create(video);
                        message = "Thêm video mới thành công!";
                        video = new Video(); // Reset form
                    } else message = "Lỗi: ID video đã tồn tại!";
                } else if (uri.contains("update")) {
                    BeanUtils.populate(video, request.getParameterMap());
                    if(request.getParameter("active") != null) video.setActive(Boolean.parseBoolean(request.getParameter("active")));
                    
                    if (videoDAO.findById(Video.class, video.getId()) != null) {
                        videoDAO.update(video);
                        message = "Cập nhật video thành công!";
                    } else message = "Lỗi: Không tìm thấy video để sửa!";
                } else if (uri.contains("delete")) {
                    String id = request.getParameter("id");
                    try {
                        videoDAO.delete(videoDAO.findById(Video.class, id));
                        message = "Xóa video thành công!";
                    } catch (Exception e) { message = "Không thể xóa video này (đang có người Like/Share)!"; }
                    video = new Video();
                } else if (uri.contains("reset")) {
                    video = new Video();
                }
                request.setAttribute("video", video);
                view = "/admin/videos.jsp";
            } 
            
            // --- QUẢN LÝ USER ---
            else if (uri.contains("user")) {
                User user = new User();
                if (uri.contains("edit")) {
                    String id = request.getParameter("id");
                    user = userDAO.findById(User.class, id);
                } else if (uri.contains("create")) {
                    BeanUtils.populate(user, request.getParameterMap());
                    if(request.getParameter("admin") != null) user.setAdmin(Boolean.parseBoolean(request.getParameter("admin")));
                    
                    if (userDAO.findById(User.class, user.getId()) == null) {
                        userDAO.create(user);
                        message = "Thêm người dùng thành công!";
                        user = new User();
                    } else message = "Username đã tồn tại!";
                } else if (uri.contains("update")) {
                    BeanUtils.populate(user, request.getParameterMap());
                    if(request.getParameter("admin") != null) user.setAdmin(Boolean.parseBoolean(request.getParameter("admin")));
                    userDAO.update(user);
                    message = "Cập nhật người dùng thành công!";
                } else if (uri.contains("delete")) {
                    String id = request.getParameter("id");
                    User current = (User) request.getSession().getAttribute("user");
                    if(current.getId().equals(id)) message = "Không thể xóa chính mình!";
                    else {
                        try {
                            userDAO.delete(userDAO.findById(User.class, id));
                            message = "Xóa người dùng thành công!";
                        } catch(Exception e) { message = "Không thể xóa user này (đang có dữ liệu liên quan)!"; }
                    }
                    user = new User();
                } else if (uri.contains("reset")) {
                    user = new User();
                }
                request.setAttribute("formUser", user);
                view = "/admin/users.jsp";
            }
            
            // --- BÁO CÁO ---
            else if (uri.contains("reports")) {
                request.setAttribute("favStats", statsDAO.findVideoLikedInfo());
                request.setAttribute("vidList", videoDAO.findAll(Video.class));
                
                String vid = request.getParameter("vid");
                if (vid != null && !vid.isEmpty()) {
                    request.setAttribute("favUsers", favDAO.findByVideoId(vid));
                    request.setAttribute("vidSelected", vid);
                    request.setAttribute("tab", "tab2");
                }
                String vidShare = request.getParameter("vidShare");
                if (vidShare != null && !vidShare.isEmpty()) {
                    request.setAttribute("shareList", shareDAO.findByVideoId(vidShare));
                    request.setAttribute("vidShareSelected", vidShare);
                    request.setAttribute("tab", "tab3");
                }
                view = "/admin/reports.jsp";
            }

            // Điều hướng mặc định & Nạp lại danh sách
            if (view.isEmpty()) {
                if (uri.endsWith("videos")) view = "/admin/videos.jsp";
                else if (uri.endsWith("users")) view = "/admin/users.jsp";
                else if (uri.endsWith("reports")) view = "/admin/reports.jsp";
                else view = "/admin/home.jsp";
            }
            
            // QUAN TRỌNG: Luôn load lại list mới nhất để hiển thị bảng
            if (view.contains("videos.jsp")) request.setAttribute("videos", videoDAO.findAll(Video.class));
            if (view.contains("users.jsp")) request.setAttribute("users", userDAO.findAll(User.class));

        } catch (Exception e) {
            e.printStackTrace();
            message = "Lỗi hệ thống: " + e.getMessage();
        }

        request.setAttribute("message", message);
        request.setAttribute("view", view);
        request.getRequestDispatcher("/admin/layout.jsp").forward(request, response);
    }
}