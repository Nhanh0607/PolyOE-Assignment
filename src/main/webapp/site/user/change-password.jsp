<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card border-0 shadow-sm mt-4">
            
            <div class="card-header bg-white border-bottom-0 pt-4 pb-0">
                <h3 class="text-center" style="color: #ff6b00; font-weight: bold;">CHANGE PASSWORD</h3>
            </div>

            <div class="card-body p-4">
                
                <c:if test="${not empty message}">
                    <div class="alert ${error ? 'alert-danger' : 'alert-success'} alert-dismissible fade show" role="alert">
                        ${message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                </c:if>

                <form action="<c:url value='/change-password'/>" method="post">
                    
                    <div class="mb-3">
                        <label for="currentPassword" class="form-label fw-bold text-secondary">Current Password</label>
                        <input type="password" class="form-control" id="currentPassword" name="currentPassword" required placeholder="Enter your current password">
                    </div>

                    <div class="mb-3">
                        <label for="newPassword" class="form-label fw-bold text-secondary">New Password</label>
                        <input type="password" class="form-control" id="newPassword" name="newPassword" required placeholder="Enter new password">
                    </div>

                    <div class="mb-3">
                        <label for="confirmPassword" class="form-label fw-bold text-secondary">Confirm New Password</label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required placeholder="Re-enter new password">
                    </div>

                    <div class="d-grid gap-2 mt-4">
                        <button type="submit" class="btn text-white fw-bold" style="background-color: #ff9900;">
                            Change Password
                        </button>
                    </div>
                    
                </form>
            </div>
            
            <div class="card-footer bg-white text-center py-3 border-top-0">
                <a href="<c:url value='/index'/>" class="text-decoration-none text-muted small">
                    <i class="fa-solid fa-arrow-left"></i> Back to Home
                </a>
            </div>
        </div>
    </div>
</div>