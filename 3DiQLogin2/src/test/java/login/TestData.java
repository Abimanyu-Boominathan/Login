package login;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

	@DataProvider(name = "loginTest")
	public Object[][] LoginDataProvider() throws BiffException, IOException {
		data = getdata();
		return data;
	}

	public Object[][] getdata() throws BiffException, IOException {

		FileInputStream excel = new FileInputStream("F:\\3DiQ_SCRIPT\\Login.xls");

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

	@Test(dataProvider = "loginTest")
	public void Login(String email, String password) throws InterruptedException {

		Logger logger = Logger.getLogger("root");

		PropertyConfigurator.configure("log4j.properties");

		driver.get("http://3.129.255.177:4202/#/account/login");
		driver.manage().timeouts().implicitlyWait(16, TimeUnit.SECONDS);
		Thread.sleep(1000);

		WebElement Email = driver.findElement(By.xpath("//input[@type='email']"));
		Email.sendKeys(email);
		Thread.sleep(1000);

		WebElement Password = driver.findElement(By.xpath("//input[@type='password']"));
		Password.sendKeys(password);
		Thread.sleep(1000);

		WebElement keep = driver.findElement(By.xpath(
				"/html/body/miq-root/app-layout/div/div/div[2]/app-sign-in/div/div/div/div/form/div[3]/label/input"));
		keep.click();
		Thread.sleep(1000);

		WebElement submit = driver.findElement(By
				.xpath("/html/body/miq-root/app-layout/div/div/div[2]/app-sign-in/div/div/div/div/form/div[4]/button"));
		submit.click();
		Thread.sleep(1000);

		String actualUrl = "http://3.129.255.177:4202/#/account/login";
		String expectedUrl = driver.getCurrentUrl();

		if (actualUrl.equals(expectedUrl)) {

			logger.info(email);
			logger.info(password);

			logger.info("Registration Failed");

		} else {		

			WebElement setting = driver.findElement(By
					.xpath("/html/body/miq-root/app-app-layout/div/div/threediq-default-topnav/nav/div/ul/li[2]/a/i"));
			setting.click();
			Thread.sleep(1000);

			WebElement sub = driver.findElement(By.xpath(
					"/html/body/miq-root/app-app-layout/div/div/threediq-default-topnav/nav/div/ul/li[2]/div/a"));
			sub.click();
			Thread.sleep(1000);

			logger.info(email);
			logger.info(password);

			logger.info("Registration successfully");

		}

		logger.info("****************************************************************************************");

	}

	@AfterTest
	public static void AfterTest() {
		driver.quit();
	}

}
