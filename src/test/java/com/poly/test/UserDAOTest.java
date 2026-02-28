package com.poly.test;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

// Import chuẩn Jakarta
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction; 
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.TypedQuery; 
import com.poly.dao.UserDAO;
import com.poly.entity.User;

public class UserDAOTest {

    @Mock EntityManager entityManager;
    @Mock EntityTransaction transaction;
    @Mock TypedQuery<User> query; // Mock đối tượng Query để test Login

    @InjectMocks UserDAO userDAO;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Quan trọng: Tránh lỗi NullPointerException khi DAO gọi getTransaction()
        when(entityManager.getTransaction()).thenReturn(transaction);
    }

    // TC01: Tìm kiếm User tồn tại
    @Test
    public void testFindById_ValidId() {
        User u = new User(); 
        u.setId("NVTeo");
        when(entityManager.find(User.class, "NVTeo")).thenReturn(u);
        
        User result = userDAO.findById(User.class, "NVTeo");
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), "NVTeo");
    }

    // TC02: Tìm kiếm User không tồn tại
    @Test
    public void testFindById_InvalidId() {
        when(entityManager.find(User.class, "UserAo")).thenReturn(null);
        Assert.assertNull(userDAO.findById(User.class, "UserAo"));
    }

    // TC03: Thêm mới User thành công
    @Test
    public void testCreate_Success() {
        User u = new User(); u.setId("NewUser");
        
        doNothing().when(transaction).begin();
        doNothing().when(entityManager).persist(u);
        doNothing().when(transaction).commit();
        
        try {
            userDAO.create(u);
        } catch (Exception e) {
            Assert.fail("Thêm mới thất bại: " + e.getMessage());
        }
    }

    // TC04: Thêm user trùng ID (Test ngoại lệ)
    @Test(expectedExceptions = RuntimeException.class)
    public void testCreate_DuplicateId() {
        User u = new User(); u.setId("NVTeo");
        
        doNothing().when(transaction).begin();
        // Giả lập lỗi trùng khóa từ DB
        doThrow(new EntityExistsException("Trùng khóa")).when(entityManager).persist(u);
        doNothing().when(transaction).rollback();
        
        userDAO.create(u);
    }

    // TC05: Test biên (Lỗi dữ liệu quá dài)
    @Test(expectedExceptions = RuntimeException.class)
    public void testCreate_Boundary_Error() {
        User u = new User(); 
        u.setId("ID_QUÁ_DÀI_TRÊN_50_KÝ_TỰ_ABC_XYZ_123456789_ABC_XYZ_123456789"); 
        
        doNothing().when(transaction).begin();
        doThrow(new jakarta.persistence.PersistenceException("Data too long")).when(entityManager).persist(u);
        doNothing().when(transaction).rollback();
        
        userDAO.create(u);
    }

    // TC06: Check Login thành công
    @Test
    public void testCheckLogin_Success() {
        User u = new User();
        u.setId("NVTeo");
        u.setPassword("123");

        // Giả lập luồng chạy của Query
        when(entityManager.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getSingleResult()).thenReturn(u);

        User result = userDAO.checkLogin("NVTeo", "123");
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), "NVTeo");
    }

    // TC07: Check Login thất bại (Sai pass hoặc user null)
    @Test
    public void testCheckLogin_Fail() {
        when(entityManager.createQuery(anyString(), eq(User.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        // Giả lập không tìm thấy kết quả
        when(query.getSingleResult()).thenThrow(new RuntimeException("No result"));

        User result = userDAO.checkLogin("NVTeo", "SaiPass");
        Assert.assertNull(result);
    }
}