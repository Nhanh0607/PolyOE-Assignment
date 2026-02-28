package com.poly.dao;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import com.poly.util.JpaUtil;

public abstract class AbstractDAO<T> {
    protected EntityManager em;

    public AbstractDAO() {
        this.em = JpaUtil.getEntityManager();
    }

    public T create(T entity) {
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(entity);
            trans.commit();
            return entity;
        } catch (Exception e) {
            trans.rollback();
            throw new RuntimeException(e);
        }
    }

    public T update(T entity) {
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(entity);
            trans.commit();
            return entity;
        } catch (Exception e) {
            trans.rollback();
            throw new RuntimeException(e);
        }
    }

    public T delete(T entity) {
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            trans.commit();
            return entity;
        } catch (Exception e) {
            trans.rollback();
            throw new RuntimeException(e);
        }
    }

    public T findById(Class<T> clazz, Object id) {
        // Xóa cache trước khi tìm để đảm bảo dữ liệu mới nhất
        em.clear();
        return em.find(clazz, id);
    }

    public List<T> findAll(Class<T> clazz) {
        // CỰC KỲ QUAN TRỌNG: Xóa cache để danh sách luôn mới sau khi Thêm/Sửa/Xóa
        em.clear();
        String jsql = "SELECT o FROM " + clazz.getSimpleName() + " o";
        TypedQuery<T> query = em.createQuery(jsql, clazz);
        return query.getResultList();
    }
}