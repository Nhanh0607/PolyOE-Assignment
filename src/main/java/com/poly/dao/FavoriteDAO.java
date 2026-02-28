package com.poly.dao;

import com.poly.entity.Favorite;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class FavoriteDAO extends AbstractDAO<Favorite> {

    public FavoriteDAO() {
        super();
    }

    /**
     * Lấy tất cả video yêu thích của một người dùng
     * @param userId ID của người dùng
     * @return Danh sách Favorite
     */
    public List<Favorite> findByUser(String userId) {
        String jsql = "SELECT o FROM Favorite o WHERE o.user.id = :uid";
        TypedQuery<Favorite> query = em.createQuery(jsql, Favorite.class);
        query.setParameter("uid", userId);
        return query.getResultList();
    }

    /**
     * Tìm một record Favorite cụ thể dựa trên user và video
     * @param userId ID người dùng
     * @param videoId ID video
     * @return Đối tượng Favorite hoặc null nếu không tìm thấy
     */
    public Favorite findFavorite(String userId, String videoId) {
        String jsql = "SELECT o FROM Favorite o WHERE o.user.id = :uid AND o.video.id = :vid";
        TypedQuery<Favorite> query = em.createQuery(jsql, Favorite.class);
        query.setParameter("uid", userId);
        query.setParameter("vid", videoId);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Không tìm thấy
        }
    }
    
    /**
     * Kiểm tra xem người dùng đã thích video này chưa
     * @param userId ID người dùng
     * @param videoId ID video
     * @return true nếu đã thích, false nếu chưa
     */
    public boolean isFavorited(String userId, String videoId) {
        return findFavorite(userId, videoId) != null;
    }
    public List<Favorite> findByVideoId(String videoId) {
        String jpql = "SELECT o FROM Favorite o WHERE o.video.id = :vid";
        TypedQuery<Favorite> query = em.createQuery(jpql, Favorite.class);
        query.setParameter("vid", videoId);
        return query.getResultList();
    }
}