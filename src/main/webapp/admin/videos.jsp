<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h3 class="mb-3 text-uppercase text-warning fw-bold">Video Management</h3>

<c:if test="${not empty message}">
    <div class="alert alert-info alert-dismissible fade show">
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
</c:if>

<ul class="nav nav-tabs" id="myTab" role="tablist">
    <li class="nav-item" role="presentation">
        <button class="nav-link ${not empty video.title ? 'active' : ''}" id="edit-tab" data-bs-toggle="tab" data-bs-target="#edit" type="button">VIDEO EDITION</button>
    </li>
    <li class="nav-item" role="presentation">
        <button class="nav-link ${empty video.title ? 'active' : ''}" id="list-tab" data-bs-toggle="tab" data-bs-target="#list" type="button">VIDEO LIST</button>
    </li>
</ul>

<div class="tab-content p-4 border border-top-0 bg-white shadow-sm rounded-bottom" id="myTabContent">
    
    <div class="tab-pane fade ${not empty video.title ? 'show active' : ''}" id="edit" role="tabpanel">
        <form method="post" class="row g-3">
            <div class="col-md-4 text-center">
                <div class="border p-1 mb-2 rounded bg-light d-flex align-items-center justify-content-center" style="height: 230px;">
                    <img src="${video.poster}" alt="Poster Preview" class="img-fluid" style="max-height: 100%;" 
                         onerror="this.src='https://placehold.co/300x200?text=No+Image'">
                </div>
                <div class="text-muted small">Poster Preview</div>
            </div>
            
            <div class="col-md-8">
                <div class="mb-3">
                    <label class="form-label fw-bold">Youtube ID / Video ID</label>
                    
                    <input type="text" class="form-control" name="id" value="${video.id}" 
                           placeholder="Ví dụ: KV3" required
                           ${not empty video.title ? 'readonly style="background-color: #e9ecef;"' : ''}>
                           
                </div>
                <div class="mb-3">
                    <label class="form-label fw-bold">Video Title</label>
                    <input type="text" class="form-control" name="title" value="${video.title}" placeholder="Nhập tiêu đề video" required>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-bold">View Count</label>
                    <input type="number" class="form-control" name="views" value="${video.views == null ? 0 : video.views}" readonly>
                </div>
                
                <div class="mb-3">
                    <label class="form-label fw-bold">Status</label> <br>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="active" value="true" ${video.active || video.active == null ? 'checked' : ''}>
                        <label class="form-check-label text-success">Active</label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="active" value="false" ${!video.active && video.active != null ? 'checked' : ''}>
                        <label class="form-check-label text-danger">Inactive</label>
                    </div>
                </div>
            </div>
            
            <div class="col-12">
                <label class="form-label fw-bold">Poster URL</label>
                <input type="text" class="form-control" name="poster" value="${video.poster}" placeholder="https://img.youtube.com/vi/ID/maxresdefault.jpg">
            </div>

            <div class="col-12">
                <label class="form-label fw-bold">Description</label>
                <textarea class="form-control" name="description" rows="3">${video.description}</textarea>
            </div>
            
            <div class="col-12 text-end mt-4">
                <button formaction="<c:url value='/admin/video/create'/>" class="btn btn-primary px-4">Create</button>
                <button formaction="<c:url value='/admin/video/update'/>" class="btn btn-success px-4">Update</button>
                <button formaction="<c:url value='/admin/video/delete'/>" class="btn btn-danger px-4" onclick="return confirm('Bạn có chắc muốn xóa video này?');">Delete</button>
                <button formaction="<c:url value='/admin/video/reset'/>" class="btn btn-secondary px-4">Reset</button>
            </div>
        </form>
    </div>

    <div class="tab-pane fade ${empty video.title ? 'show active' : ''}" id="list" role="tabpanel">
        <table class="table table-hover table-bordered align-middle">
            <thead class="table-dark text-center">
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Views</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${videos}">
                    <tr>
                        <td class="text-center fw-bold text-primary">${item.id}</td>
                        <td>${item.title}</td>
                        <td class="text-center">${item.views}</td>
                        <td class="text-center">
                            <span class="badge ${item.active ? 'bg-success' : 'bg-danger'}">
                                ${item.active ? 'Active' : 'Inactive'}
                            </span>
                        </td>
                        <td class="text-center">
                            <a href="<c:url value='/admin/video/edit?id=${item.id}'/>" class="btn btn-sm btn-warning">
                                <i class="fa-solid fa-pen-to-square"></i> Edit
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>