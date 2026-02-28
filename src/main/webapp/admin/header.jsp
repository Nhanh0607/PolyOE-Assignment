<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark py-3 shadow">
    <div class="container">
        <a class="navbar-brand fw-bold text-warning" href="<c:url value='/admin'/>">
            <i class="fa-solid fa-user-shield me-2"></i>ADMINISTRATION
        </a>
        
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="<c:url value='/admin'/>">HOME</a></li>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/videos'/>">VIDEOS</a></li>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/users'/>">USERS</a></li>
                <li class="nav-item"><a class="nav-link" href="<c:url value='/admin/reports'/>">REPORTS</a></li>
                
                <li class="nav-item ms-3">
                    <a class="btn btn-outline-light btn-sm fw-bold mt-1" href="<c:url value='/index'/>">
                        <i class="fa-solid fa-globe"></i> Preview Website
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>