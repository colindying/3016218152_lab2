package test;

import java.util.regex.Pattern;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

@RunWith(Parameterized.class)
public class TestBaidu {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  
  private static ArrayList<String> id = new ArrayList<String>();
  private static ArrayList<String> name = new ArrayList<String>();
  private static ArrayList<String> url = new ArrayList<String>();
  
	private String id1;  
	private String name1; 
	private String url1;
    public TestBaidu(String id1, String name1, String url1){ 
	this.id1 = id1;
	this.name1 = name1;
	this.url1 = url1;
	}


  @Before
  public void setUp() throws Exception {
	  String driverPath = System.getProperty("user.dir") + "/src/geckodriver.exe";
	  System.setProperty("webdriver.gecko.driver", driverPath);
	  driver = new FirefoxDriver();
	  baseUrl = "http://121.193.130.195:8800";
	  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  
      

  }

  @Parameters 
	public static Collection<Object[]> getData() throws IOException{ 
		  FileInputStream excelFileInputStream = new FileInputStream("E:/大三下/软件测试技术/Lab2/软件测试名单.xlsx");
	      XSSFWorkbook workbook = new XSSFWorkbook(excelFileInputStream);
	      excelFileInputStream.close();
		  XSSFSheet sheet = workbook.getSheetAt(0); 
	      for (int rowIndex = 2; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
		  XSSFRow row = sheet.getRow(rowIndex);
		  row.getCell(1).setCellType(XSSFCell.CELL_TYPE_STRING);
		  XSSFCell idd = row.getCell(1);
		  XSSFCell namee = row.getCell(2);
		  XSSFCell urll = row.getCell(3);
		  id.add(idd.getStringCellValue());
	      name.add( namee.getStringCellValue());
		  url.add(urll.getStringCellValue()); 
	      }
		 workbook.close();
		List<Object[]> cl = new ArrayList<>();
		for(int i = 0; i < id.size(); i++) {
		    
		    cl.add(new Object[]{id.get(i), name.get(i), url.get(i)});
		}
        return cl;
	}
  
  @Test
  public void testBaidu() throws Exception {
    driver.get(baseUrl + "/");
//    WebElement we = driver.findElement(By.id("kw"));
//    we.click();
//    driver.findElement(By.id("kw")).click();
//    driver.findElement(By.id("kw")).clear();
    driver.findElement(By.name("id")).sendKeys(id1);
    driver.findElement(By.name("password")).sendKeys(id1.substring(id1.length() - 6));
    driver.findElement(By.id("btn_login")).click();
    
    WebElement we = driver.findElement(By.id("student-git"));
    WebElement i = driver.findElement(By.id("student-id"));
    WebElement n = driver.findElement(By.id("student-name"));
    
    assertEquals(url1, we.getText());
    assertEquals(id1,i.getText());
    assertEquals(name1,n.getText());
    driver.close();
  }

  @After
  public void tearDown() throws Exception {
//    driver.quit();
//    String verificationErrorString = verificationErrors.toString();
//    if (!"".equals(verificationErrorString)) {
//      fail(verificationErrorString);
//    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}

