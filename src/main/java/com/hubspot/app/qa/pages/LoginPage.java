package com.hubspot.app.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.hubspot.app.qa.base.TestBase;
import com.hubspot.app.qa.util.TestUtil;

public class LoginPage extends TestBase {
	
	@FindBy(xpath="//input[@id='username']") 
	WebElement userNameTextBox;
	@FindBy(xpath="//input[@id='password']") 
	WebElement passwordTextBox;
	@FindBy(xpath="//button[@id='loginBtn']") 
	WebElement loginButton;
	
	public void doLogin(String user, String password) {
		TestUtil.sendKeysToElement(userNameTextBox, user);
		TestUtil.sendKeysToElement(passwordTextBox, password);
		TestUtil.elementClick(loginButton);
	}
	
	public void doInvalidLogin(String user, String password) throws Exception {
		TestUtil.sendKeysToElement(userNameTextBox, user);
		TestUtil.sendKeysToElement(passwordTextBox, password);
		Thread.sleep(2000);
		TestUtil.clearElement(userNameTextBox);
		TestUtil.clearElement(passwordTextBox);
	}
	
	public LoginPage() {
		PageFactory.initElements(driver, this);
	}
}
