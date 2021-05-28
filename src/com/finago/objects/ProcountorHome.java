package com.finago.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProcountorHome {

	//Initializing webdriver object
	WebDriver driver;
	
	//Constructor
	public ProcountorHome(WebDriver driver){
	       this.driver = driver;
	}
	
	//Page Objects
	By homeBtn = By.xpath("//*[@id='HomeButton']");
	By welcomePopupOkBtn = By.xpath("//*[@id='GuiButton_OK']");
	By searchBox = By.xpath("//*[@id='SearchParameterTextField']");
	By searchResult = By.xpath("//*[@id=\"UIVerticalLayout\"]/div[2]//em");
	By searchLink = By.xpath("//*[@id='UIVerticalLayout']/div[2]/div");
	
	//Methods
	//Verify if login is successful
	public boolean isLoginSuccessful() {
		if(driver.findElement(homeBtn).isDisplayed())
			return true;
		else
			return false;
	}
	
	//Close welcome pop up
	public void closingWelcomePopup() {
		driver.findElement(welcomePopupOkBtn).click();
	}
	
	//Search in searchbox
	public void search(String searchText) {
		driver.findElement(searchBox).sendKeys(searchText);
	}
	
	//Verify if search result is displayed
	public boolean verifySearchResultIsDisplayed() {
		if(driver.findElement(searchResult).isDisplayed())
			return true;
		else
			return false;
	}
	
	//Click on searched result
	public void clickTextSearched() {
		driver.findElement(searchLink).click();
	}
	
}
