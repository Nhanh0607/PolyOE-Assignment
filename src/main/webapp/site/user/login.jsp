<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-5">
        <div class="card border-0 shadow-sm mt-5">
            
            <div class="card-header bg-white border-0 text-center pt-4">
                <h3 style="color: #ff6b00; font-weight: bold; text-transform: uppercase;">Login</h3>
                <p class="text-muted small">Welcome back! Please login to continue.</p>
            </div>
            
            <div class="card-body p-4">
                
                <c:if test="${not empty message}">
                    <div class="alert alert-danger text-center" role="alert">
                        <i class="fa-solid fa-circle-exclamation"></i> ${message}
                    </div>
                </c:if>
                
                <c:if test="${not empty param.message}">
                    <div class="alert alert-warning text-center" role="alert">
                        <i class="fa-solid fa-triangle-exclamation"></i> ${param.message}
                    </div>
                </c:if>

                <form action="<c:url value='/login'/>" method="post">
                    
                    <div class="mb-3">
                        <label class="form-label fw-bold text-secondary">Username</label>
                        <div class="input-group">
                            <span class="input-group-text bg-light"><i class="fa-solid fa-user"></i></span>
                            
                            <input type="text" class="form-control" name="username" 
                                   value="${username}" 
                                   placeholder="Enter username" required autofocus>
                        </div>
                    </div>
                    
                    <div class="mb-3">
                        <div class="d-flex justify-content-between">
                            <label class="form-label fw-bold text-secondary">Password</label>
                            <a href="<c:url value='/forgot-password'/>" class="text-decoration-none small">Forgot Password?</a>
                        </div>
                        <div class="input-group">
                            <span class="input-group-text bg-light"><i class="fa-solid fa-lock"></i></span>
                            
                            <input type="password" class="form-control" name="password" 
                                   value="" autocomplete="off" 
                                   placeholder="Enter password" required>
                        </div>
                    </div>
                    
                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" name="remember" id="remember" ${remember ? 'checked' : ''}>
                        <label class="form-check-label text-muted" for="remember">Remember me</label>
                    </div>
                    
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn text-white fw-bold py-2" style="background-color: #ff9900; font-size: 1.1rem;">
                            LOGIN
                        </button>
                    </div>
                </form>
            </div>
            
            <div class="card-footer bg-light text-center py-3">
                <span class="text-muted">Don't have an account?</span>
                <a href="<c:url value='/register'/>" class="text-decoration-none fw-bold ms-1" style="color: #ff6b00;">
                    Sign Up Now
                </a>
            </div>
            
        </div>
    </div>
</div>