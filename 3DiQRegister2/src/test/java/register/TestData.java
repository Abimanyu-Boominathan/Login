package register;


import java.io.FileInputStream;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import jxl.Sheet;
import jxl.Workbook;

import jxl.read.biff.BiffException;

public class TestData {

	public static WebDriver driver;
	Object data[][] = null;

	@DataProvider(name = "RegisterTest")
	public Object[][] LoginDataProvider() throws BiffException, IOException {
		data = getdata();
		return data;
	}

	public Object[][] getdata() throws BiffException, IOException {

		FileInputStream excel = new FileInputStream("F:\\3DiQ_SCRIPT\\Register.xls");
		

		Workbook workbook = Workbook.getWorkbook(excel);  

		Sheet sheet = workbook.getSheet(0);
		
		int rowcount = sheet.getRows();
		int columncount = sheet.getColumns();

		String testdata[][] = new String[rowcount - 1][columncount];

		for (int i = 1; i < rowcount; i++) {
			for (int j = 0; j < columncount; j++) {
				testdata[i - 1][j] = sheet.getCell(j, i).getContents();
			}
		}
		return testdata;
	}

	@BeforeTest
	public static void BeforeTest() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		
		
	}

	@Test(dataProvider = "RegisterTest")
	public void Login(String username, String email, String password) throws InterruptedException {
		
		Logger logger = Logger.getLogger("root");
		

		PropertyConfigurator.configure("log4j.properties");
		

		driver.get("http://3.129.255.177:4202/#/account/register");
		Thread.sleep(1000);
	
	
		WebElement Username = driver.findElement(By.xpath("/html/body/miq-root/app-layout/div/div/div[2]/app-sign-up/div/div/div/form/div[1]/div/input"));
		Username.sendKeys(username);
		Thread.sleep(1000);
		
		

		WebElement Email = driver.findElement(By.xpath("/html/body/miq-root/app-layout/div/div/div[2]/app-sign-up/div/div/div/form/div[2]/div/input"));
		Email.sendKeys(email);
		Thread.sleep(1000);
		
		
		
		WebElement Password = driver.findElement(By.xpath("/html/body/miq-root/app-layout/div/div/div[2]/app-sign-up/div/div/div/form/div[3]/div/input"));
		Password.sendKeys(password);
		Thread.sleep(1000);
		
		WebElement receive = driver.findElement(By.xpath("/html/body/miq-root/app-layout/div/div/div[2]/app-sign-up/div/div/div/form/div[4]/p/input"));
		receive.click();		
		Thread.sleep(1000);

		WebElement accept = driver.findElement(By.xpath("/html/body/miq-root/app-layout/div/div/div[2]/app-sign-up/div/div/div/form/div[5]/p/input"));
		accept.click();		
	    Thread.sleep(1000);
	    
	    WebElement register = driver.findElement(By.xpath("/html/body/miq-root/app-layout/div/div/div[2]/app-sign-up/div/div/div/form/div[6]/div/button[1]"));
	    register.click();		
	    Thread.sleep(3000);
	    
		

		String actualUrl = "http://3.129.255.177:4202/#/account/register";
		String expectedUrl = driver.getCurrentUrl();
		
		
		if (actualUrl.equals(expectedUrl)) {
			
			logger.info(username);
			logger.info(email);
			logger.info(password);
			
			 logger.info("Registration Faield");
			 logger.info("****************************************************************************************");
				
			
		} 
		else
		
		{
			logger.info(username);
			logger.info(email);
			logger.info(password);
			
			logger.info("Registration Successful");
			logger.info("****************************************************************************************");
		}
			    
			   
			  
				
		}

		
		
		 
@AfterTest
	public static void AfterTest() {
		driver.quit();
	}

}
