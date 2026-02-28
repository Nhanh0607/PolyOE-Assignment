<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/fmt" prefix = "fmt" %>

<html>
<head>
    <title>Thống kê Video yêu thích</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <div class="container">
        
        <h2>Danh sách các video đã được yêu thích</h2>
        
        <table class="report-table">
            <thead>
                <tr>
                    <th>Video Title</th>
                    <th>Người thích</th>
                    <th>Ngày thích</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="fav" items="${favorites}">
                    <tr>
                        <td>${fav.video.title}</td>
                        <td>${fav.user.fullname}</td>
                        <td>
                            <fmt:formatDate value="${fav.likeDate}" pattern="dd/MM/yyyy" />
                        </td>
                    </tr>
                </c:forEach>
                
                <c:if test="${empty favorites}">
                    <tr>
                        <td colspan="3" style="text-align: center;">Chưa có video nào được thích.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
    </div> </body>
</html>