<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card border-0 shadow-sm mt-4">
            <div class="card-header bg-white border-0 text-center pt-4">
                <h3 style="color: #ff6b00; font-weight: bold;">SHARE VIDEO</h3>
            </div>
            
            <div class="card-body p-4">
                <c:if test="${not empty message}">
                    <div class="alert ${error ? 'alert-danger' : 'alert-success'} mb-3">
                        ${message}
                    </div>
                </c:if>

                <div class="row">
                    <div class="col-md-5 text-center">
                        <img src="${video.poster}" class="img-fluid rounded mb-3" alt="${video.title}" 
                             onerror="this.src='https://placehold.co/300x200'">
                        <h5 class="fw-bold text-secondary">${video.title}</h5>
                    </div>
                    
                    <div class="col-md-7">
                        <form action="<c:url value='/share'/>" method="post">
                            <input type="hidden" name="videoId" value="${video.id}">
                            
                            <div class="mb-3">
                                <label class="form-label fw-bold">To Friend's Email</label>
                                <input type="email" class="form-control" name="email" required placeholder="nhập email bạn bè...">
                            </div>
                            
                            <div class="mb-3">
                                <label class="form-label fw-bold">Message (Optional)</label>
                                <textarea class="form-control" rows="3" placeholder="Lời nhắn..."></textarea>
                            </div>
                            
                            <button class="btn text-white fw-bold w-100" style="background-color: #ff9900;">
                                <i class="fa-solid fa-paper-plane"></i> Send Now
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>