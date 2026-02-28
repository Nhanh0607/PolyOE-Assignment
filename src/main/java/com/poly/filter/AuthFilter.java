package com.poly.filter;

import java.io.IOException;
import java.net.URLEncoder; // Nhớ import cái này để xử lý tiếng Việt trên URL

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.poly.entity.User;

@WebFilter({"/favorites", "/like", "/share", "/admin/*", "/edit-profile", "/change-password"}) 
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        
        User user = (User) req.getSession().getAttribute("user");
        String error = "";

        if (user == null) {
            // Chưa đăng nhập -> Báo lỗi
            error = "Vui lòng đăng nhập để sử dụng chức năng này!";
            
            // Lưu lại trang hiện tại (Referer) để đăng nhập xong thì quay lại
            // Ví dụ: Đang ở trang chủ bấm Like -> Login -> Quay lại trang chủ
            String referer = req.getHeader("referer");
            if(referer != null) {
                req.getSession().setAttribute("securityUri", referer);
            }
            
        } else if (!user.getAdmin() && uri.contains("/admin/")) {
            // Đã đăng nhập nhưng không phải Admin
            error = "Bạn không có quyền quản trị!";
        }

        if (!error.isEmpty()) {
            // === SỬA ĐOẠN NÀY ===
            // Mã hóa câu thông báo tiếng Việt để không bị lỗi trên URL
            String encodedError = URLEncoder.encode(error, "UTF-8");
            
            // Chuyển hướng (Redirect) sang trang LoginServlet
            // Nó sẽ nạp lại CSS, Header, Footer đầy đủ
            resp.sendRedirect(req.getContextPath() + "/login?message=" + encodedError);
        } else {
            // Hợp lệ -> Cho đi tiếp
            chain.doFilter(request, response);
        }
    }
}