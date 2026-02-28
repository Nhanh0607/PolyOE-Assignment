<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h2 class="text-primary">My Favorite Videos</h2>
<hr>

<div class="row">
    <%-- Lưu ý: Biến 'videos' ở đây thực chất là List<Favorite> được gửi từ Servlet --%>
    <c:forEach var="item" items="${videos}">
        
        <div class="col-md-4 mb-4">
            <div class="card video-card">
                
                <%-- item.video vì 'item' là đối tượng Favorite, cần .video để lấy thông tin --%>
                <a href="<c:url value='/detail?id=${item.video.id}'/>">
                    <img class="card-img-top" 
                         src="<c:url value='${item.video.poster}'/>" 
                         alt="${item.video.title}">
                </a>
                
                <div class="card-body">
                    <h5 class="card-title">
                        <a href="<c:url value='/detail?id=${item.video.id}'/>">${item.video.title}</a>
                    </h5>
                    
                    <div class="d-flex justify-content-between align-items-center">
                        <small class="text-muted">
                            Liked on: <fmt:formatDate value="${item.likeDate}" pattern="dd/MM/yyyy"/>
                        </small>
                        
                        <%-- Nút Bỏ thích (Unlike) --%>
                        <a href="<c:url value='/unlike?id=${item.video.id}'/>" 
                           class="btn btn-sm btn-danger">Unlike</a>
                    </div>
                    
                    <div class="mt-2">
                        <a href="<c:url value='/share?id=${item.video.id}'/>" 
                           class="btn btn-sm btn-block btn-share" style="width:100%">Share to Friend</a>
                    </div>
                </div>
            </div>
        </div>
        
    </c:forEach>
    
    <c:if test="${empty videos}">
        <div class="col-12 text-center">
            <p>Bạn chưa thích video nào.</p>
            <a href="<c:url value='/index'/>" class="btn btn-primary">Xem video ngay</a>
        </div>
    </c:if>
</div>