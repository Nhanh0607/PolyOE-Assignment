<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<h3 class="mb-3 text-uppercase text-warning fw-bold">Report & Statistics</h3>

<ul class="nav nav-tabs" id="myTab" role="tablist">
    <li class="nav-item">
        <button class="nav-link ${empty tab ? 'active' : ''}" id="fav-tab" data-bs-toggle="tab" data-bs-target="#fav" type="button">FAVORITES</button>
    </li>
    <li class="nav-item">
        <button class="nav-link ${tab == 'tab2' ? 'active' : ''}" id="fav-user-tab" data-bs-toggle="tab" data-bs-target="#fav-user" type="button">FAVORITE USERS</button>
    </li>
    <li class="nav-item">
        <button class="nav-link ${tab == 'tab3' ? 'active' : ''}" id="share-tab" data-bs-toggle="tab" data-bs-target="#share" type="button">SHARED FRIENDS</button>
    </li>
</ul>

<div class="tab-content p-4 border border-top-0 bg-white shadow-sm rounded-bottom" id="myTabContent">
    
    <div class="tab-pane fade ${empty tab ? 'show active' : ''}" id="fav" role="tabpanel">
        <table class="table table-bordered table-hover text-center">
            <thead class="table-dark">
                <tr>
                    <th>Video Title</th>
                    <th>Favorite Count</th>
                    <th>Latest Date</th>
                    <th>Oldest Date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${favStats}">
                    <tr>
                        <td class="text-start fw-bold text-primary">${item[0]}</td> <td>${item[1]}</td> <td><fmt:formatDate value="${item[2]}" pattern="dd-MM-yyyy"/></td> <td><fmt:formatDate value="${item[3]}" pattern="dd-MM-yyyy"/></td> </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="tab-pane fade ${tab == 'tab2' ? 'show active' : ''}" id="fav-user" role="tabpanel">
        
        <form action="<c:url value='/admin/reports'/>" method="get" class="row g-3 mb-4 align-items-center bg-light p-3 rounded">
            <div class="col-auto">
                <label class="col-form-label fw-bold">Video Title:</label>
            </div>
            <div class="col-auto flex-grow-1">
                <select name="vid" class="form-select" onchange="this.form.submit()">
                    <option value="">-- Select a Video --</option>
                    <c:forEach var="v" items="${vidList}">
                        <option value="${v.id}" ${v.id == vidSelected ? 'selected' : ''}>${v.title}</option>
                    </c:forEach>
                </select>
            </div>
        </form>

        <table class="table table-bordered table-hover">
            <thead class="table-dark">
                <tr>
                    <th>Username</th>
                    <th>Fullname</th>
                    <th>Email</th>
                    <th>Like Date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="fav" items="${favUsers}">
                    <tr>
                        <td class="fw-bold">${fav.user.id}</td>
                        <td>${fav.user.fullname}</td>
                        <td>${fav.user.email}</td>
                        <td class="text-center"><fmt:formatDate value="${fav.likeDate}" pattern="dd-MM-yyyy"/></td>
                    </tr>
                </c:forEach>
                <c:if test="${empty favUsers && not empty vidSelected}">
                    <tr><td colspan="4" class="text-center text-muted">Chưa có ai thích video này.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>

    <div class="tab-pane fade ${tab == 'tab3' ? 'show active' : ''}" id="share" role="tabpanel">
        
        <form action="<c:url value='/admin/reports'/>" method="get" class="row g-3 mb-4 align-items-center bg-light p-3 rounded">
            <div class="col-auto">
                <label class="col-form-label fw-bold">Video Title:</label>
            </div>
            <div class="col-auto flex-grow-1">
                <select name="vidShare" class="form-select" onchange="this.form.submit()">
                    <option value="">-- Select a Video --</option>
                    <c:forEach var="v" items="${vidList}">
                        <option value="${v.id}" ${v.id == vidShareSelected ? 'selected' : ''}>${v.title}</option>
                    </c:forEach>
                </select>
            </div>
        </form>

        <table class="table table-bordered table-hover">
            <thead class="table-dark">
                <tr>
                    <th>Sender Name</th>
                    <th>Sender Email</th>
                    <th>Receiver Email</th>
                    <th>Sent Date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="share" items="${shareList}">
                    <tr>
                        <td class="fw-bold">${share.user.fullname}</td>
                        <td>${share.user.email}</td>
                        <td>${share.emails}</td>
                        <td class="text-center"><fmt:formatDate value="${share.shareDate}" pattern="dd-MM-yyyy"/></td>
                    </tr>
                </c:forEach>
                 <c:if test="${empty shareList && not empty vidShareSelected}">
                    <tr><td colspan="4" class="text-center text-muted">Chưa có ai chia sẻ video này.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>