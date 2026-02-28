package com.poly.controller;

import java.io.IOException;
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

@WebServlet(urlPatterns = {"/favorites", "/like", "/unlike"})
public class FavoriteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FavoriteDAO favoriteDAO = new FavoriteDAO();
    private VideoDAO videoDAO = new VideoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        User user = (User) request.getSession().getAttribute("user");
        
        // QUAN TRỌNG: Lấy đường dẫn trang người dùng vừa đứng
        String referer = request.getHeader("referer"); 
        
        if (uri.contains("/favorites")) {
            // Trang danh sách yêu thích
            List<Favorite> favorites = favoriteDAO.findByUser(user.getId());
            request.setAttribute("videos", favorites);
            request.setAttribute("view", "/site/home/favorites.jsp");
            request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
            return; // Dừng tại đây, không chạy xuống dưới
            
        } else if (uri.contains("/like")) {
            handleLike(request, response, user);
        } else if (uri.contains("/unlike")) {
            handleUnlike(request, response, user);
        }

        // QUAN TRỌNG: Quay lại đúng trang cũ (Trang chủ hoặc Trang chi tiết)
        if (referer != null && !referer.isEmpty()) {
            response.sendRedirect(referer);
        } else {
            // Nếu không tìm thấy trang cũ thì về trang chủ
            response.sendRedirect(request.getContextPath() + "/index");
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void handleLike(HttpServletRequest request, HttpServletResponse response, User user) {
        String videoId = request.getParameter("id");
        if (videoId == null) return;
        
        Video video = videoDAO.findById(Video.class, videoId);
        
        if (!favoriteDAO.isFavorited(user.getId(), videoId)) {
            Favorite fav = new Favorite();
            fav.setUser(user);
            fav.setVideo(video);
            favoriteDAO.create(fav);
        }
    }
    
    private void handleUnlike(HttpServletRequest request, HttpServletResponse response, User user) {
        String videoId = request.getParameter("id");
        if (videoId == null) return;
        
        Favorite fav = favoriteDAO.findFavorite(user.getId(), videoId);
        if (fav != null) {
            favoriteDAO.delete(fav);
        }
    }
}