package com.finago.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProcountorLogin {

	//Initializing webdriver object
	WebDriver driver;
	
	//Constructor
	public ProcountorLogin(WebDriver driver){
	      this.driver = driver;
	}
	
	//Page Objects
	By procounterUsername= By.xpath("//*[@id='LocalizableTextField']");
	By procounterPassword= By.xpath("//*[@id='LocalizableUIPasswordField']");
	By loginBtn= By.xpath("//*[@id='LoginPageButton_BUTTON_NEXT']");
	
	//Methods
	//Login with username and password
	public void loginProcountor(String username, String password) {
		driver.findElement(procounterUsername).sendKeys(username);
		driver.findElement(procounterPassword).sendKeys(password);
		driver.findElement(loginBtn).click();
	}
}
