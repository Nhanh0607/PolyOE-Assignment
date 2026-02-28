<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg bg-success" id="custom-header">
    <div class="container-fluid">
        <a class="navbar-brand custom-brand" href="<c:url value='/index'/>">
            ONLINE ENTERTAINMENT
        </a>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto align-items-center">
                
                <c:if test="${sessionScope.user.admin}">
                    <li class="nav-item me-3">
                        <a href="<c:url value='/admin'/>" class="btn btn-danger btn-sm fw-bold shadow-sm">
                            <i class="fa-solid fa-screwdriver-wrench"></i> Administration
                        </a>
                    </li>
                </c:if>

                <c:if test="${not empty sessionScope.user}">
                    <li class="nav-item">
                        <a class="nav-link custom-link" href="<c:url value='/favorites'/>">MY FAVORITES</a>
                    </li>
                </c:if>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle custom-link" href="#" role="button" data-bs-toggle="dropdown">
                        MY ACCOUNT
                    </a>
                    <ul class="dropdown-menu custom-dropdown dropdown-menu-end">
                        <c:if test="${empty sessionScope.user}">
                            <li><a class="dropdown-item" href="<c:url value='/login'/>">Login</a></li>
                            <li><a class="dropdown-item" href="<c:url value='/forgot-password'/>">Forgot Password</a></li>
                            <li><a class="dropdown-item" href="<c:url value='/register'/>">Registration</a></li>
                        </c:if>
                        
                        <c:if test="${not empty sessionScope.user}">
                            <li><a class="dropdown-item" href="<c:url value='/change-password'/>">Change Password</a></li>
                            <li><a class="dropdown-item" href="<c:url value='/edit-profile'/>">Edit Profile</a></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item text-danger fw-bold" href="<c:url value='/logout'/>">Logoff</a></li>
                        </c:if>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>