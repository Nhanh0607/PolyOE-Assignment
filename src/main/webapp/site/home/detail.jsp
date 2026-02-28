<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">

    <div class="col-md-8">
        <div class="card video-detail-card border-0 shadow-sm">
            
            <div class="video-player-box ratio ratio-16x9 bg-dark">
                <iframe 
                    src="https://www.youtube.com/embed/${video.id}?autoplay=1&rel=0" 
                    title="${video.title}" 
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" 
                    allowfullscreen>
                </iframe>
            </div>
            
            <div class="card-body p-4">
                <h4 class="fw-bold text-uppercase text-dark mb-2">${video.title}</h4>
                
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <span class="text-muted">
                        <i class="fa-solid fa-eye me-1"></i> 
                        <fmt:formatNumber value="${video.views}" /> views
                    </span>
                    <span class="text-muted small">
                        ID: ${video.id}
                    </span>
                </div>

                <div class="d-flex gap-2 pb-3 border-bottom">
                    
                    <%-- LOGIC LIKE/UNLIKE (Kiểm tra xem user đã like video này chưa) --%>
                    <c:choose>
                        <c:when test="${not empty sessionScope.user and likedVideoIds.contains(video.id)}">
                            <a href="<c:url value='/unlike?id=${video.id}'/>" class="btn btn-danger fw-bold px-4">
                                <i class="fa-solid fa-heart"></i> Unlike
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value='/like?id=${video.id}'/>" class="btn btn-primary fw-bold px-4" style="background-color: #0d6efd;">
                                <i class="fa-regular fa-thumbs-up"></i> Like
                            </a>
                        </c:otherwise>
                    </c:choose>

                    <a href="<c:url value='/share?id=${video.id}'/>" class="btn btn-warning fw-bold px-4 text-white">
                        <i class="fa-solid fa-share"></i> Share
                    </a>
                </div>
                
                <div class="mt-3">
                    <h6 class="fw-bold text-secondary">DESCRIPTION</h6>
                    <p class="text-dark" style="white-space: pre-line;">${video.description}</p>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <div class="bg-white p-3 rounded shadow-sm border">
            <h5 class="fw-bold text-uppercase text-warning mb-3 border-bottom pb-2">
                Related Videos
            </h5>
            
            <c:forEach var="related" items="${relatedVideos}">
                <a href="<c:url value='/detail?id=${related.id}'/>" class="text-decoration-none">
                    <div class="d-flex align-items-center mb-3 p-2 rounded hover-effect" style="transition: 0.3s;">
                        <div class="flex-shrink-0 position-relative" style="width: 120px;">
                            <img src="${related.poster}" class="img-fluid rounded" alt="${related.title}"
                                 onerror="this.src='https://placehold.co/120x70'">
                            <div class="position-absolute top-50 start-50 translate-middle text-white small opacity-75">
                                <i class="fa-solid fa-play"></i>
                            </div>
                        </div>
                        <div class="flex-grow-1 ms-3">
                            <h6 class="mb-1 text-dark fw-bold" style="font-size: 0.9rem; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden;">
                                ${related.title}
                            </h6>
                            <small class="text-muted">
                                <i class="fa-solid fa-eye"></i> <fmt:formatNumber value="${related.views}" />
                            </small>
                        </div>
                    </div>
                </a>
            </c:forEach>
            
            <c:if test="${empty relatedVideos}">
                <p class="text-muted text-center small">No related videos found.</p>
            </c:if>
        </div>
    </div>

</div>

<style>
    .hover-effect:hover {
        background-color: #f8f9fa;
        transform: translateX(5px);
    }
</style>