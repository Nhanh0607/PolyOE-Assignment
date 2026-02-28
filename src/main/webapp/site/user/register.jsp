<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3>REGISTRATION</h3>

<c:if test="${not empty message}">
    <div class="alert alert-danger">${message}</div>
</c:if>

<form action="<c:url value='/register'/>" method="post">
    <div class="form-group">
        <label>Username?</label>
        <input type="text" class="form-control" name="username" required>
    </div>
    <div class.form-group">
        <label>Password?</label>
        <input type="password" class="form-control" name="password" required>
    </div>
     <div class="form-group">
        <label>Fullname?</label>
        <input type="text" class="form-control" name="fullname" required>
    </div>
    <div class.form-group">
        <label>Email Address?</label>
        <input type="email" class="form-control" name="email" required>
    </div>
    <br>
    <button type="submit" class="btn btn-primary">Sign Up</button>
</form>