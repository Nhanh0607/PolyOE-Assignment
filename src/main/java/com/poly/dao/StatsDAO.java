package com.poly.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import com.poly.util.JpaUtil;

public class StatsDAO {
    private EntityManager em = JpaUtil.getEntityManager();

    // 1. Thống kê số lượng yêu thích theo từng Video
    // Kết quả trả về List các mảng Object[]: {Title, Count, NewestDate, OldestDate}
    public List<Object[]> findVideoLikedInfo() {
        String jpql = "SELECT f.video.title, count(f), max(f.likeDate), min(f.likeDate) "
                    + "FROM Favorite f GROUP BY f.video.title";
        TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
        return query.getResultList();
    }
}