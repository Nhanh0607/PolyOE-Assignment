package com.poly.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.poly.util.CookieUtil;

public class CookieUtilTest {

    @Mock HttpServletRequest request;
    @Mock HttpServletResponse response;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // TC13: Lấy cookie tồn tại
    @Test
    public void testGet_CookieExists() {
        Cookie[] cookies = { new Cookie("user", "NVTeo") };
        when(request.getCookies()).thenReturn(cookies);
        Assert.assertEquals(CookieUtil.get("user", request), "NVTeo");
    }

    // TC14: Lấy cookie không tồn tại
    @Test
    public void testGet_CookieNotExists() {
        Cookie[] cookies = { new Cookie("other", "value") };
        when(request.getCookies()).thenReturn(cookies);
        Assert.assertEquals(CookieUtil.get("user", request), "");
    }

    // TC15: Thêm cookie
    @Test
    public void testAdd_Cookie() {
        CookieUtil.add("userid", "LTPhuc", 1, response);
        // Kiểm tra xem hàm addCookie đã được gọi chưa
        verify(response, times(1)).addCookie(any(Cookie.class));
    }

    // TC16: Xóa cookie (Set thời gian = 0)
    @Test
    public void testAdd_DeleteCookie() {
        CookieUtil.add("user", "", 0, response);

        // Bắt lấy cookie được gửi đi để kiểm tra xem MaxAge có bằng 0 không
        org.mockito.ArgumentCaptor<Cookie> cookieCaptor = org.mockito.ArgumentCaptor.forClass(Cookie.class);
        verify(response).addCookie(cookieCaptor.capture());

        Cookie capturedCookie = cookieCaptor.getValue();
        Assert.assertEquals(capturedCookie.getMaxAge(), 0, "Muốn xóa cookie thì MaxAge phải bằng 0");
    }
}