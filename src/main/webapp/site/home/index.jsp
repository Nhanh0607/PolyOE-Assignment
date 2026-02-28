<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row mb-4 justify-content-center">
    <div class="col-md-6">
        <form action="<c:url value='/index'/>" method="get" class="d-flex shadow-sm rounded overflow-hidden bg-white">
            <input class="form-control border-0 py-2 ps-4" type="search" name="keyword" 
                   placeholder="Nhập tên video bạn muốn tìm..." value="${searchKeyword}" aria-label="Search">
            <button class="btn fw-bold px-4" type="submit" style="background-color: #ff9900; color: white; border-radius: 0;">
                <i class="fa-solid fa-magnifying-glass"></i> Tìm
            </button>
            <c:if test="${not empty searchKeyword}">
                <a href="<c:url value='/index'/>" class="btn btn-secondary px-3" style="border-radius: 0; line-height: 2.2;">
                   <i class="fa-solid fa-xmark"></i>
                </a>
            </c:if>
        </form>
    </div>
</div>

<c:if test="${not empty message}">
    <div class="alert alert-info text-center mb-4 shadow-sm border-0" style="background-color: #e3f2fd; color: #0d47a1;">
        <i class="fa-solid fa-circle-info me-2"></i> ${message}
    </div>
</c:if>

<div class="row">
    <c:forEach var="video" items="${videos}">
        <div class="col-md-4 mb-4">
            <div class="card video-card h-100">
                
                <a href="<c:url value='/detail?id=${video.id}'/>" class="card-img-wrapper d-block">
                    <img class="card-img-top" 
                         src="${video.poster}" 
                         alt="${video.title}"
                         onerror="this.src='https://placehold.co/600x400?text=No+Image'">
                    
                    
                </a>
                
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title mb-2">
                        <a href="<c:url value='/detail?id=${video.id}'/>">${video.title}</a>
                    </h5>
                    
                    <div class="mb-3 text-muted small">
                        <span class="me-3"><i class="fa-solid fa-eye me-1"></i> <fmt:formatNumber value="${video.views}" /></span>
                        <span><i class="fa-solid fa-calendar-days me-1"></i> 2025</span>
                    </div>
                    
                    <div class="mt-auto pt-3 border-top d-flex justify-content-between align-items-center">
                        <%-- Logic Like/Unlike giữ nguyên --%>
                        <c:choose>
                            <c:when test="${not empty sessionScope.user and likedVideoIds.contains(video.id)}">
                                <a href="<c:url value='/unlike?id=${video.id}'/>" class="btn-action btn-action-unlike w-100 me-2 text-center">
                                    <i class="fa-solid fa-heart"></i> Unlike
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="<c:url value='/like?id=${video.id}'/>" class="btn-action btn-action-like w-100 me-2 text-center">
                                    <i class="fa-regular fa-thumbs-up"></i> Like
                                </a>
                            </c:otherwise>
                        </c:choose>

                        <a href="<c:url value='/share?id=${video.id}'/>" class="btn-action btn-action-share w-100 text-center">
                            <i class="fa-solid fa-share"></i> Share
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<c:if test="${empty searchKeyword && totalPages > 1}">
    <nav aria-label="Page navigation" class="mt-4 mb-5 d-flex justify-content-center">
        <ul class="pagination shadow-sm">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="<c:url value='/index?page=1'/>"><i class="fa-solid fa-angles-left"></i></a>
            </li>
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="<c:url value='/index?page=${currentPage - 1}'/>"><i class="fa-solid fa-angle-left"></i></a>
            </li>
            <c:forEach begin="1" end="${totalPages}" var="i">
                <li class="page-item ${currentPage == i ? 'active' : ''}">
                    <a class="page-link" href="<c:url value='/index?page=${i}'/>">${i}</a>
                </li>
            </c:forEach>
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="<c:url value='/index?page=${currentPage + 1}'/>"><i class="fa-solid fa-angle-right"></i></a>
            </li>
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="<c:url value='/index?page=${totalPages}'/>"><i class="fa-solid fa-angles-right"></i></a>
            </li>
        </ul>
    </nav>
</c:if>