package com.finago.objects;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProcountorImportData {

	//Initializing webdriver object
	WebDriver driver;
	WebDriverWait wait;
	
	//Constructor
	public ProcountorImportData(WebDriver driver){
		     this.driver = driver;
	}
	
	//Page Objects
	By importTypeDd = By.xpath("//*[@id='GuiChoice_CH_IMPORTTYPE']");
	By importTypeDdValue = By.xpath("//*[@id=\"VAADIN_COMBOBOX_OPTIONLIST\"]//tbody//span");
	By selectBtn = By.xpath("//*[@id='AButton_SELECT_FILE_']/div");
	By chooseFileBtn = By.xpath("//input[@class='gwt-FileUpload']");
	By continueBeforeImportBtn = By.xpath("//*[@id='GuiButton_CONTINUE']");
	By continueAfterImportBtn = By.xpath("//*[@id='AButton_CONTINUE']");
	By importDataText = By.xpath("//*[@id='GuiLabel_CH_RESUME']");
	By receiptTypeImported = By.xpath("//*[@id='M']/span/span");
	By receiptNameImported = By.xpath("//*[@id='ExtendedVaadinTable']/div[2]//tbody//td[10]/div");
	By saveBtn = By.xpath("//*[@id='AButton_SAVE_']/div[1]");
	By importPopupYesBtn = By.xpath("//*[@id='GuiButton_YES']");
	
	//Methods
	//Clicking on import type drop down
	public void clickImportTypeDd() {
		driver.findElement(importTypeDd).click();
	}
	
	//Selecting import type value from drop down
	public boolean selectValueDD(String value) {
		boolean val=false;
		//Getting the list of dropdown values
		List<WebElement> ddValues = driver.findElements(importTypeDdValue);
		
		// Iterating through the list selecting the desired option
	    for( int i = 0; i< ddValues.size();i++){
	    
	    if( ddValues.get(i).getText().equals(value)){
	            ddValues.get(i).click();
	            val=true;
	            break;
	         }
	 } 
	    return val;
	    
	}
	
	//Importing file
	public void importFile() {
		 driver.findElement(selectBtn).click();
		 //Getting the file path to be uploaded
		 File f = new File(System.getProperty("user.dir")+"/upload_files");
		 String[] files= f.list();
		 String fileName = System.getProperty("user.dir")+"/upload_files/"+files[0];
		 File filePath = new File(fileName);
		    
		 // Enter the file path onto the file-selection input field
	     driver.findElement(chooseFileBtn).sendKeys(filePath.getAbsolutePath());
	     
	    //Clicking on continue button
	     driver.findElement(continueBeforeImportBtn).click();
	     wait = new WebDriverWait(driver, 30);
	     wait.until(ExpectedConditions.visibilityOfElementLocated(continueAfterImportBtn)).click();
	     
	 }
	
	//Verifying if the import is successful
	public boolean checkSuccessfulImport() {
		String importDataReadText = driver.findElement(importDataText).getText();
        //Reading the Amount of incorrectly read rows
        String splitDot = importDataReadText.split("\\.")[2];
        String splitColon = (splitDot.split("\\:")[1]).trim();
        if((Integer.parseInt(splitColon))== 0)
        	return true;
        else
        	return false;
	}
	
	//Getting the Receipt Type of imported Receipt
	public String readReceiptType() {
		String receiptTypeImportedText= driver.findElement(receiptTypeImported).getText();
        return receiptTypeImportedText;
	}
	
	//Getting the Receipt Name of imported Receipt
	public String readReceiptName() {
        String receiptNameImportedText= driver.findElement(receiptNameImported).getText();
        return receiptNameImportedText;
	}
	
	//Saving the uploaded data
	public void clickSaveBtn() {
		driver.findElement(saveBtn).click();
	}
	
	//Fetching imported invoices
	public void fetchImportedInvoice() {
		driver.findElement(importPopupYesBtn).click();
	}
}
