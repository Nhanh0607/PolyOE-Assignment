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

@WebServlet("/change-password")
public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public ChangePasswordServlet() {
        super();
        this.userDAO = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 1. Gọi giao diện change-password.jsp
        request.setAttribute("view", "/site/user/change-password.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 2. Lấy thông tin từ form
        String currentPass = request.getParameter("currentPassword");
        String newPass = request.getParameter("newPassword");
        String confirmPass = request.getParameter("confirmPassword");
        
        // 3. Lấy user đang đăng nhập từ Session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        // Kiểm tra nếu session hết hạn (dù AuthFilter đã chặn, nhưng check thêm cho chắc)
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // 4. Validate dữ liệu
        if (!currentPass.equals(user.getPassword())) {
            // Lỗi: Mật khẩu hiện tại không đúng
            request.setAttribute("message", "Sai mật khẩu hiện tại!");
            request.setAttribute("error", true); // Cờ để hiện màu đỏ
        } else if (!newPass.equals(confirmPass)) {
            // Lỗi: Xác nhận mật khẩu không khớp
            request.setAttribute("message", "Xác nhận mật khẩu không khớp!");
            request.setAttribute("error", true);
        } else {
            // 5. Thành công: Cập nhật mật khẩu mới
            user.setPassword(newPass);
            
            try {
                userDAO.update(user);
                request.setAttribute("message", "Đổi mật khẩu thành công!");
                request.setAttribute("error", false); // Cờ để hiện màu xanh
                
                // Cập nhật lại user trong session để đồng bộ
                session.setAttribute("user", user);
                
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "Lỗi hệ thống: " + e.getMessage());
                request.setAttribute("error", true);
            }
        }
        
        // 6. Trả về lại trang đổi mật khẩu kèm thông báo
        request.setAttribute("view", "/site/user/change-password.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
}