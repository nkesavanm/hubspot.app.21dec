package com.hubspot.app.qa.testcases;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.hubspot.app.qa.base.TestBase;
import com.hubspot.app.qa.pages.LoginPage;
import com.hubspot.app.qa.util.TestUtil;

public class LoginPageTest extends TestBase {
	LoginPage loginPage;
	@BeforeMethod
	public void browserSetup() {
		TestBase.initialization();
	}
	
	@DataProvider
	public Object[][] loginData() {
		return TestUtil.getData("Sheet1");
	}
	
	// Test case for hubspot app
	@Test(priority=1)
	public void validLogin() {
		test = reports.createTest("validLogin");
		loginPage = new LoginPage();
		loginPage.doLogin(pro.getProperty("username"), pro.getProperty("password"));
	}
	
	@Test(priority=2,dataProvider = "loginData")
	public void invalidLoginCheck(String username, String password) throws Exception {
		test = reports.createTest("invalidLoginCheck");
		loginPage = new LoginPage();
		loginPage.doInvalidLogin(username,password);
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) {
		if(ITestResult.FAILURE==result.getStatus()) {
			TestUtil.getImage(driver, result.getName());
		}
		TestBase.closeBrowser();
	}
}
