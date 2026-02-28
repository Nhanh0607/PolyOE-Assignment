package com.poly.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import com.poly.dao.UserDAO;
import com.poly.entity.User;

@WebServlet("/edit-profile")
public class EditProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("view", "/site/user/edit-profile.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // 1. Lấy user hiện tại từ Session
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            
            // 2. Cập nhật thông tin mới vào đối tượng user này
            // (Chỉ cập nhật Fullname và Email, giữ nguyên ID, Pass, Admin)
            BeanUtils.populate(currentUser, request.getParameterMap());
            
            // 3. Lưu xuống Database
            userDAO.update(currentUser);
            
            // 4. Cập nhật lại Session (để hiển thị tên mới ngay lập tức trên menu)
            session.setAttribute("user", currentUser);
            
            request.setAttribute("message", "Cập nhật thông tin thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi: " + e.getMessage());
            request.setAttribute("error", true); // Hiện màu đỏ
        }
        
        request.setAttribute("view", "/site/user/edit-profile.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
}