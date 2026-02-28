<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>PolyOE Administration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
</head>
<body class="bg-light">

    <jsp:include page="/admin/header.jsp" />

    <div class="container my-4 p-4 bg-white shadow-sm rounded" style="min-height: 600px;">
        <jsp:include page="${view}" />
    </div>

    <footer class="bg-dark text-white text-center py-3 mt-auto">
        <p class="m-0">&copy; 2025 PolyOE Administration System</p>
    </footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>