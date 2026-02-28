package com.poly.filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*") // Áp dụng cho TẤT CẢ các trang
public class EncodingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Ép buộc UTF-8 cho cả chiều đi (Request) và chiều về (Response)
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        chain.doFilter(request, response);
    }
}