package com.poly.controller;

import java.io.IOException;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.poly.dao.ShareDAO;
import com.poly.dao.VideoDAO;
import com.poly.entity.Share;
import com.poly.entity.User;
import com.poly.entity.Video;
import com.poly.util.EmailUtils;

@WebServlet("/share")
public class ShareServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VideoDAO videoDAO = new VideoDAO();
    private ShareDAO shareDAO = new ShareDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // 1. Lấy ID video muốn share để hiển thị thông tin
        String videoId = request.getParameter("id");
        if (videoId != null) {
            Video video = videoDAO.findById(Video.class, videoId);
            request.setAttribute("video", video);
        }
        
        request.setAttribute("view", "/site/home/share.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String emailTo = request.getParameter("email");
        String videoId = request.getParameter("videoId");
        
        try {
            // 1. Lấy thông tin người gửi (User đang login)
            User user = (User) request.getSession().getAttribute("user");
            Video video = videoDAO.findById(Video.class, videoId);
            
            if (user == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }

            // 2. Tạo nội dung Email
            String subject = "Share Video: " + video.getTitle();
            String link = request.getRequestURL().toString().replace("share", "detail?id=" + videoId);
            String content = "Xin chào, <br>Bạn của bạn là <b>" + user.getFullname() + "</b> đã chia sẻ video này cho bạn:<br>"
                           + "<h3>" + video.getTitle() + "</h3>"
                           + "<a href='" + link + "'>Xem ngay tại đây</a><br>"
                           + "<img src='" + video.getPoster() + "' width='300'/>";

            // 3. Gửi Email
            EmailUtils.send(emailTo, subject, content);
            
            // 4. Lưu lịch sử vào Database (Bảng Share)
            Share share = new Share();
            share.setEmails(emailTo);
            share.setShareDate(new Date());
            share.setUser(user);
            share.setVideo(video);
            shareDAO.create(share);
            
            request.setAttribute("message", "Đã gửi video thành công tới " + emailTo);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Lỗi gửi mail: " + e.getMessage());
            request.setAttribute("error", true);
        }
        
        // Giữ lại thông tin video để hiện lại form
        if (videoId != null) {
            request.setAttribute("video", videoDAO.findById(Video.class, videoId));
        }
        
        request.setAttribute("view", "/site/home/share.jsp");
        request.getRequestDispatcher("/site/layout.jsp").forward(request, response);
    }
}