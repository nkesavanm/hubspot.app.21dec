package com.hubspot.app.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.hubspot.app.qa.base.TestBase;

public class TestUtil extends TestBase {
	static WebDriverWait wait = new WebDriverWait(driver, 20);
	public static String TESTDATA_EXCEL_PATH = System.getProperty("user.dir")+"/src/main/java/com/hubspot/app/qa/testdata/TestData.xlsx";
	public static Workbook book;
	public static Sheet sheet;
	
	public static void waitForElementVisible(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static void waitForElementClick(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public static void sendKeysToElement(WebElement element, String data) {
		waitForElementVisible(element);
		element.sendKeys(data);
	}
	
	public static void clearElement(WebElement element) {
		waitForElementVisible(element);
		element.clear();
	}
	
	public static void elementClick(WebElement element) {
		waitForElementClick(element);
		element.click();
	}
	
	public static void getImage(WebDriver driver, String imageName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		Date date = new Date();
		String timeStamp = sdf.format(date);
		TakesScreenshot ts = (TakesScreenshot)driver; // down casting
		File srcFile = ts.getScreenshotAs(OutputType.FILE); // to convert the image as file and store that into File class refrrence
		File destFile = new File(System.getProperty("user.dir")+"/screenshots/"+imageName+"_"+timeStamp+".png");
		try {
			FileHandler.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object[][] getData(String sheetName) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(TESTDATA_EXCEL_PATH);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		};
		
		try {
			book = WorkbookFactory.create(fis);
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for(int i=0; i<sheet.getLastRowNum(); i++) {
			for(int j=0; j<sheet.getRow(0).getLastCellNum(); j++) {
				data[i][j] = sheet.getRow(i+1).getCell(j).getStringCellValue(); 
			}
		}
		return data;
	}

	
}
