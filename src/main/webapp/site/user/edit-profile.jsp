<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-8 col-lg-6">
        <div class="card border-0 shadow-sm mt-4">
            
            <div class="card-header bg-white border-bottom-0 pt-4 pb-0 text-center">
                <h3 style="color: #ff6b00; font-weight: bold; text-transform: uppercase;">Edit Profile</h3>
            </div>

            <div class="card-body p-4">
                <c:if test="${not empty message}">
                    <div class="alert ${error ? 'alert-danger' : 'alert-success'} alert-dismissible fade show">
                        ${message}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <form action="<c:url value='/edit-profile'/>" method="post">
                    <div class="row g-3">
                        <div class="col-md-6">
                            <label class="form-label fw-bold text-muted">Username</label>
                            <input type="text" class="form-control bg-light" value="${sessionScope.user.id}" readonly>
                        </div>
                        
                        <div class="col-md-6">
                            <label class="form-label fw-bold text-muted">Password</label>
                            <input type="password" class="form-control bg-light" value="${sessionScope.user.password}" readonly>
                            <small><a href="<c:url value='/change-password'/>" class="text-decoration-none">Change Password?</a></small>
                        </div>

                        <div class="col-12">
                            <label class="form-label fw-bold">Fullname</label>
                            <input type="text" class="form-control" name="fullname" value="${sessionScope.user.fullname}" required>
                        </div>

                        <div class="col-12">
                            <label class="form-label fw-bold">Email Address</label>
                            <input type="email" class="form-control" name="email" value="${sessionScope.user.email}" required>
                        </div>

                        <div class="col-12 text-end mt-4">
                            <button type="submit" class="btn text-white fw-bold px-4" style="background-color: #ff9900;">
                                Update
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>