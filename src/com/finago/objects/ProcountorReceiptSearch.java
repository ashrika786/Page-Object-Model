package com.finago.objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProcountorReceiptSearch {

	//Initializing webdriver object
	WebDriver driver;
		
	//Constructor
	public ProcountorReceiptSearch(WebDriver driver){
		       this.driver = driver;
	}
		
	//Page Objects
	By receiptTypeSearched = By.xpath("//*[@id='UIGridWithSimpleTableCompatibility']//tbody//td[7]");
	By receiptNameSearched = By.xpath("//*[@id='UIGridWithSimpleTableCompatibility']//tbody//td[2]/button");

	//Methods
	//Getting the Receipt Type of imported Receipt
	public String readReceiptType() {
		String receiptTypeSearchedText= driver.findElement(receiptTypeSearched).getText();
	        return receiptTypeSearchedText;
	}
		
	//Getting the Receipt Name of imported Receipt
	public String readReceiptName() {
	      String receiptNameSearchedText= driver.findElement(receiptNameSearched).getText();
	        return receiptNameSearchedText;
	}
	
	//Viewing searched receipt
	public void viewReceipt() {
		driver.findElement(receiptNameSearched).click();
	}

}
