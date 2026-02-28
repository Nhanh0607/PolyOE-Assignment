package com.poly.dao;

import com.poly.entity.Share;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ShareDAO extends AbstractDAO<Share> {

    public ShareDAO() {
        super();
    }

    /**
     * Lấy tất cả lịch sử share của một người dùng
     * @param userId ID của người dùng
     * @return Danh sách Share
     */
    public List<Share> findByUser(String userId) {
        String jsql = "SELECT o FROM Share o WHERE o.user.id = :uid";
        TypedQuery<Share> query = em.createQuery(jsql, Share.class);
        query.setParameter("uid", userId);
        return query.getResultList();
    }
    public List<Share> findByVideoId(String videoId) {
        String jpql = "SELECT o FROM Share o WHERE o.video.id = :vid";
        TypedQuery<Share> query = em.createQuery(jpql, Share.class);
        query.setParameter("vid", videoId);
        return query.getResultList();
    }
    
    // Bạn có thể thêm các phương thức tìm kiếm khác nếu cần,
    // ví dụ: tìm các lượt share của 1 video...
}