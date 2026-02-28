<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3 class="mb-3 text-uppercase text-warning fw-bold">User Management</h3>

<c:if test="${not empty message}">
    <div class="alert alert-info alert-dismissible fade show">
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>

<ul class="nav nav-tabs" id="myTab" role="tablist">
    <li class="nav-item">
        <button class="nav-link ${not empty formUser.id ? 'active' : ''}" id="edit-tab" data-bs-toggle="tab" data-bs-target="#edit" type="button">USER EDITION</button>
    </li>
    <li class="nav-item">
        <button class="nav-link ${empty formUser.id ? 'active' : ''}" id="list-tab" data-bs-toggle="tab" data-bs-target="#list" type="button">USER LIST</button>
    </li>
</ul>

<div class="tab-content p-4 border border-top-0 bg-white shadow-sm rounded-bottom">
    
    <div class="tab-pane fade ${not empty formUser.id ? 'show active' : ''}" id="edit">
        <form method="post" class="row g-3">
            <div class="col-md-6">
                <label class="form-label fw-bold">Username</label>
                <input type="text" class="form-control" name="id" value="${formUser.id}" placeholder="Username" required>
            </div>
            <div class="col-md-6">
                <label class="form-label fw-bold">Password</label>
                <input type="text" class="form-control" name="password" value="${formUser.password}" required>
            </div>
            <div class="col-md-6">
                <label class="form-label fw-bold">Fullname</label>
                <input type="text" class="form-control" name="fullname" value="${formUser.fullname}" placeholder="Họ và tên">
            </div>
            <div class="col-md-6">
                <label class="form-label fw-bold">Email</label>
                <input type="email" class="form-control" name="email" value="${formUser.email}" placeholder="Email">
            </div>
            
            <div class="col-12">
                <label class="form-label fw-bold">Role</label> <br>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="admin" value="true" ${formUser.admin ? 'checked' : ''}>
                    <label class="form-check-label text-danger fw-bold">Admin</label>
                </div>
                <div class="form-check form-check-inline">
                    <input class="form-check-input" type="radio" name="admin" value="false" ${!formUser.admin ? 'checked' : ''} checked>
                    <label class="form-check-label text-primary">User</label>
                </div>
            </div>
            
            <div class="col-12 text-end mt-3">
                <button formaction="<c:url value='/admin/user/create'/>" class="btn btn-primary px-4">Create</button>
                <button formaction="<c:url value='/admin/user/update'/>" class="btn btn-success px-4">Update</button>
                <button formaction="<c:url value='/admin/user/delete'/>" class="btn btn-danger px-4" onclick="return confirm('Xóa user này?');">Delete</button>
                <button formaction="<c:url value='/admin/user/reset'/>" class="btn btn-secondary px-4">Reset</button>
            </div>
        </form>
    </div>

    <div class="tab-pane fade ${empty formUser.id ? 'show active' : ''}" id="list">
        <table class="table table-hover table-bordered align-middle">
            <thead class="table-dark text-center">
                <tr>
                    <th>Username</th>
                    <th>Fullname</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="u" items="${users}">
                    <tr>
                        <td class="fw-bold">${u.id}</td>
                        <td>${u.fullname}</td>
                        <td>${u.email}</td>
                        <td class="text-center">
                            <span class="badge ${u.admin ? 'bg-danger' : 'bg-primary'}">
                                ${u.admin ? 'Admin' : 'User'}
                            </span>
                        </td>
                        <td class="text-center">
                            <a href="<c:url value='/admin/user/edit?id=${u.id}'/>" class="btn btn-sm btn-warning">
                                <i class="fa-solid fa-pen-to-square"></i> Edit
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>