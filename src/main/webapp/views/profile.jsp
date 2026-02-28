<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Trang cá nhân</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="container">
        
        <c:if test="${not empty user}">
            <h1>${user.fullname}</h1>
            <h3>Các video đã yêu thích</h3>
            
            <ul class="favorite-list">
                <c:forEach var="fav" items="${user.favorites}">
                    <li>
                        ${fav.video.title}
                    </li>
                </c:forEach>

                <c:if test="${empty user.favorites}">
                    <li>Bạn chưa thích video nào.</li>
                </c:if>
            </ul>
        </c:if>
        
        <c:if test="${empty user}">
            <p>Không tìm thấy người dùng.</p>
        </c:if>
        
    </div> </body>
</html>