package com.poly.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.poly.dao.UserDAO;
import com.poly.entity.User;
import com.poly.util.EmailUtils;

@WebServlet("/forgot-password")
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("view", "/site/user/forgot-password.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        
        try {
            // 1. Tìm user theo ID
            User user = userDAO.findById(User.class, username);
            
            // 2. Kiểm tra xem Username có tồn tại và Email có khớp không
            if (user == null) {
                request.setAttribute("message", "Sai tên đăng nhập!");
                request.setAttribute("error", true);
            } else if (!user.getEmail().equalsIgnoreCase(email)) {
                request.setAttribute("message", "Email không khớp với tài khoản đăng ký!");
                request.setAttribute("error", true);
            } else {
                // 3. Thông tin đúng -> Gửi mật khẩu về mail
                String subject = "Your Password Recovery - PolyOE";
                String content = "Xin chào " + user.getFullname() + ",<br>"
                               + "Mật khẩu của bạn là: <b style='color:red; font-size:1.2em'>" + user.getPassword() + "</b><br>"
                               + "Vui lòng đăng nhập và đổi mật khẩu ngay.";
                
                EmailUtils.send(email, subject, content);
                
                request.setAttribute("message", "Mật khẩu đã được gửi về email của bạn!");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
            request.setAttribute("error", true);
        }
        
        request.setAttribute("view", "/site/user/forgot-password.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
}