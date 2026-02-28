package com.poly.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import com.poly.dao.VideoDAO;
import com.poly.entity.Video;

public class VideoDAOTest {

    @Mock EntityManager entityManager;
    @Mock EntityTransaction transaction;
    @InjectMocks VideoDAO videoDAO;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(entityManager.getTransaction()).thenReturn(transaction);
    }

    // TC08: Thêm mới Video
    @Test
    public void testCreate_Video() {
        Video v = new Video(); v.setId("V01");
        
        doNothing().when(transaction).begin();
        doNothing().when(entityManager).persist(v);
        doNothing().when(transaction).commit();

        try {
            videoDAO.create(v);
        } catch (Exception e) {
            Assert.fail("Lỗi thêm video: " + e.getMessage());
        }
    }

    // TC09: Cập nhật Video
    @Test
    public void testUpdate_Video() {
        Video v = new Video();
        v.setId("V01");
        v.setTitle("Title Updated");

        doNothing().when(transaction).begin();
        when(entityManager.merge(v)).thenReturn(v);
        doNothing().when(transaction).commit();

        Video result = videoDAO.update(v);
        Assert.assertEquals(result.getTitle(), "Title Updated");
    }

    // TC10: Xóa Video
    @Test
    public void testDelete_Video() {
        Video v = new Video(); v.setId("V01");

        doNothing().when(transaction).begin();
        when(entityManager.contains(v)).thenReturn(true);
        doNothing().when(entityManager).remove(v);
        doNothing().when(transaction).commit();

        try {
            videoDAO.delete(v);
        } catch (Exception e) {
            Assert.fail("Lỗi xóa video: " + e.getMessage());
        }
    }

    // TC11: Tìm Video có tồn tại
    @Test
    public void testFindById_Found() {
        Video v = new Video(); v.setId("V01");
        when(entityManager.find(Video.class, "V01")).thenReturn(v);
        Assert.assertNotNull(videoDAO.findById(Video.class, "V01"));
    }
    
    // TC12: Tìm Video không tồn tại
    @Test
    public void testFindById_NotFound() {
        when(entityManager.find(Video.class, "V999")).thenReturn(null);
        Assert.assertNull(videoDAO.findById(Video.class, "V999"));
    }
}