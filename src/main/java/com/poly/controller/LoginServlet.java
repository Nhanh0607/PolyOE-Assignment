package com.poly.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import com.poly.dao.UserDAO;
import com.poly.entity.User;
import com.poly.util.CookieUtil; // Nhớ import cái này

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Kiểm tra Cookie xem có lưu username không
        String username = CookieUtil.get("username", request);
        request.setAttribute("username", username);
        
        // Nếu có lưu cookie thì tự động check vào ô "Remember me" luôn cho tiện
        if (!username.isEmpty()) {
            request.setAttribute("remember", true); // Biến này để JSP check box
        }
        
        request.setAttribute("view", "/site/user/login.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String userId = request.getParameter("username");
        String pass = request.getParameter("password");
        String remember = request.getParameter("remember"); // Checkbox
        
        User user = userDAO.checkLogin(userId, pass);
        
        if (user != null) {
            // Đăng nhập thành công
            HttpSession session = request.getSession();
            session.setAttribute("user", user); 
            
            // --- XỬ LÝ REMEMBER ME ---
            if (remember != null) {
                // Nếu chọn: Lưu username vào cookie trong 30 ngày (24 * 30 giờ)
                CookieUtil.add("username", userId, 24 * 30, response);
            } else {
                // Nếu không chọn: Xóa cookie cũ đi (set thời gian = 0)
                CookieUtil.add("username", userId, 0, response);
            }
            // -------------------------
            
            // ... (Đoạn code chuyển hướng giữ nguyên như cũ) ...
            String redirectUri = (String) session.getAttribute("securityUri");
            if (redirectUri != null) {
                session.removeAttribute("securityUri");
                response.sendRedirect(redirectUri);
            } else {
                if (user.getAdmin()) response.sendRedirect(request.getContextPath() + "/admin");
                else response.sendRedirect(request.getContextPath() + "/index");
            }
            
        } else {
            // Đăng nhập thất bại
            request.setAttribute("message", "Sai tên đăng nhập hoặc mật khẩu!");
            request.setAttribute("username", userId); // Giữ lại tên vừa nhập để đỡ gõ lại
            request.setAttribute("view", "/site/user/login.jsp");
            request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
        }
    }
}