package com.hubspot.app.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class TestBase {
	public static Properties pro;
	public FileInputStream fis;
	String CONFIG_FILE = System.getProperty("user.dir") + "/src/main/java/com/hubspot/app/qa/config/config.properties";
	public static WebDriver driver;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports reports;
	public static ExtentTest test;
	
	public TestBase() {
		pro = new Properties();
		try {
			fis = new FileInputStream(CONFIG_FILE);
			pro.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void initialization() {
		String browserName = pro.getProperty("browser");
		if(browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "E:\\Selenium Jars\\chromedriver\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		driver.manage().window().maximize();
		driver.get(pro.getProperty("url"));
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@BeforeSuite
	public void setUp() {
		initialization();
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/src/main/java/com/hubspot/app/qa/report/StatusReport.html");
		htmlReporter.config().setDocumentTitle("Automation Report");
		htmlReporter.config().setReportName("Automation Execution Status Report");
		htmlReporter.config().setTheme(Theme.STANDARD);
		reports = new ExtentReports();
		reports.attachReporter(htmlReporter);
		reports.setSystemInfo("Username", "Kavish");
		reports.setSystemInfo("Host", "Localhost");
		reports.setSystemInfo("Operating System", "Windows 10");
	}
	
	@AfterSuite
	public void tearDown() {
		reports.flush();
		
	}
	
	public static void closeBrowser() {
		driver.quit();
	}
}
