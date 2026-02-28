package com.poly.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class DienMayXanhLoginTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito"); 
        options.addArguments("--disable-notifications"); 
        options.addArguments("--disable-popup-blocking"); 
        options.addArguments("--headless=new");
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); 
        
        driver.get("https://www.dienmayxanh.com/");
    }

    // Hàm ép Click có Phương Án Dự Phòng (Fallback)
    private void openLoginPopup() throws InterruptedException {
        try {
            WebElement loginBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(), 'Tài khoản & Đơn hàng')] | //a[contains(@href, '/lich-su-mua-hang')] | //div[contains(@class, 'header__history')]")
            ));
            
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", loginBtn);
            Thread.sleep(2000); 
        } catch (Exception e) {
            System.out.println("⚠️ Đang chuyển hướng thẳng đến form Đăng nhập...");
            driver.get("https://www.dienmayxanh.com/lich-su-mua-hang/dang-nhap");
            Thread.sleep(2000); 
        }
    }

    // TC01 – Nhập SĐT hợp lệ
    @Test
    public void TC01_ValidPhone() throws InterruptedException {
        openLoginPopup();
        
        // SỬ DỤNG XPATH CỦA CHÍNH BẠN TÌM ĐƯỢC VÀO ĐÂY:
        WebElement txtPhone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='txtPhoneNumber']")));
        txtPhone.sendKeys("0901234777");
        
        // Bấm nút Tiếp tục
        WebElement btnSubmit = driver.findElement(By.xpath("//button[contains(text(),'Tiếp tục') or @type='submit']"));
        btnSubmit.click();
        
        Thread.sleep(2000); 
        
        boolean isOtpScreen = driver.getPageSource().contains("Mã xác nhận");
        Assert.assertTrue(isOtpScreen, "Lỗi: Không chuyển sang màn hình OTP.");
    }

    // TC02 – SĐT không hợp lệ (ít hơn 10 số)
    @Test
    public void TC02_InvalidPhone() throws InterruptedException {
        openLoginPopup();
        
        WebElement txtPhone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='txtPhoneNumber']")));
        txtPhone.sendKeys("09123"); 
        
        WebElement btnSubmit = driver.findElement(By.xpath("//button[contains(text(),'Tiếp tục') or @type='submit']"));
        btnSubmit.click();
        Thread.sleep(1000); 
        
        boolean isErrorDisplayed = driver.getPageSource().contains("Số điện thoại không hợp lệ") 
                                || driver.getPageSource().contains("không đúng");
        Assert.assertTrue(isErrorDisplayed, "Lỗi: Không hiển thị cảnh báo SĐT không hợp lệ.");
    }

    // TC03 – SĐT chứa chữ cái
    @Test
    public void TC03_PhoneWithLetters() throws InterruptedException {
        openLoginPopup();
        
        WebElement txtPhone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='txtPhoneNumber']")));
        txtPhone.sendKeys("09abc12345"); 
        
        WebElement btnSubmit = driver.findElement(By.xpath("//button[contains(text(),'Tiếp tục') or @type='submit']"));
        btnSubmit.click();
        Thread.sleep(1000);
        
        boolean isErrorDisplayed = driver.getPageSource().contains("Số điện thoại không hợp lệ") || driver.getPageSource().contains("định dạng");
        Assert.assertTrue(isErrorDisplayed, "Lỗi: Hệ thống không báo lỗi khi nhập chữ.");
    }

    // TC04 – Số điện thoại để trống
    @Test
    public void TC04_EmptyPhone() throws InterruptedException {
        openLoginPopup();
        
        // 1. Click vào ô SĐT nhưng cố tình không gõ gì cả
        WebElement txtPhone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='txtPhoneNumber']")));
        txtPhone.click();
        
        // 2. Bấm nút Tiếp tục
        WebElement btnSubmit = driver.findElement(By.xpath("//button[contains(text(),'Tiếp tục') or @type='submit']"));
        btnSubmit.click();
        Thread.sleep(1500); // Chờ 1.5s cho web nháy đỏ hiển thị lỗi
        
        // 3. Quét toàn bộ trang để tìm bất kỳ từ khóa báo lỗi trống nào có thể xảy ra
        String pageSource = driver.getPageSource();
        boolean isErrorDisplayed = pageSource.contains("Vui lòng nhập") 
                                || pageSource.contains("để trống")
                                || pageSource.contains("chưa nhập")
                                || pageSource.contains("không hợp lệ")
                                || pageSource.contains("bắt buộc");
                                
        Assert.assertTrue(isErrorDisplayed, "Lỗi: Không tìm thấy bất kỳ câu cảnh báo SĐT trống nào!");
    }

    // TC05 – SĐT ký tự đặc biệt
    @Test
    public void TC05_SpecialCharacters() throws InterruptedException {
        openLoginPopup();
        
        WebElement txtPhone = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='txtPhoneNumber']")));
        txtPhone.sendKeys("09012###89"); 
        
        WebElement btnSubmit = driver.findElement(By.xpath("//button[contains(text(),'Tiếp tục') or @type='submit']"));
        btnSubmit.click();
        Thread.sleep(1000);
        
        boolean isErrorDisplayed = driver.getPageSource().contains("không hợp lệ") 
                                || driver.getPageSource().contains("định dạng");
        Assert.assertTrue(isErrorDisplayed, "Lỗi: Không báo lỗi ký tự đặc biệt.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit(); 
        }
    }
}