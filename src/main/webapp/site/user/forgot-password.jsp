<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card border-0 shadow-sm mt-5">
            <div class="card-header bg-white border-0 text-center pt-4">
                <h3 style="color: #ff6b00; font-weight: bold;">FORGOT PASSWORD</h3>
                <p class="text-muted small">Nhập thông tin để lấy lại mật khẩu</p>
            </div>
            
            <div class="card-body p-4">
                <c:if test="${not empty message}">
                    <div class="alert ${error ? 'alert-danger' : 'alert-success'}">
                        ${message}
                    </div>
                </c:if>

                <form action="<c:url value='/forgot-password'/>" method="post">
                    <div class="mb-3">
                        <label class="form-label fw-bold">Username</label>
                        <input type="text" class="form-control" name="username" required placeholder="Tên đăng nhập...">
                    </div>
                    
                    <div class="mb-3">
                        <label class="form-label fw-bold">Email Address</label>
                        <input type="email" class="form-control" name="email" required placeholder="Email đăng ký...">
                    </div>
                    
                    <div class="d-grid gap-2 mt-4">
                        <button class="btn text-white fw-bold" style="background-color: #ff9900;">Retrieve Password</button>
                    </div>
                </form>
            </div>
            
            <div class="card-footer bg-white text-center py-3">
                <a href="<c:url value='/login'/>" class="text-decoration-none text-primary fw-bold">Back to Login</a>
            </div>
        </div>
    </div>
</div>