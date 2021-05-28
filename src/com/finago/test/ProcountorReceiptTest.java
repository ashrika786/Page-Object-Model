package com.finago.test;

import org.testng.annotations.Test;

import com.finago.objects.ProcountorHome;
import com.finago.objects.ProcountorImportData;
import com.finago.objects.ProcountorLogin;
import com.finago.objects.ProcountorReceiptSearch;
import org.testng.annotations.BeforeTest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ProcountorReceiptTest {
	
  //Setting the system property for ChromeDriver
   String driverPath= System.getProperty("user.dir")+"/driver/chromedriver.exe";
   File driverFolder = new File(driverPath);
   
   //Initializing objects of classes,webdriver and Extent Report
   static WebDriver driver;
   ProcountorHome procountorHome;
   ProcountorImportData procountorImportdata;
   ProcountorLogin procountorLogin;
   ProcountorReceiptSearch procountorReceiptSearch;
   static ExtentTest test;
   static ExtentReports report;
	
   @BeforeTest
   public void setup(){
	   
	 //Initialization of the Extent Reports object
	 report = new ExtentReports(System.getProperty("user.dir")+"/Reports/"+"ExtentReportResults.html");
	 test = report.startTest("ProcountorReceiptTest");
	System.setProperty("webdriver.chrome.driver", driverFolder.getAbsolutePath());
	
	//Enable automation and save password disable by Chrome
	ChromeOptions options = new ChromeOptions(); 
	Map<String, Object> prefs = new HashMap<String, Object>();
    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    options.setExperimentalOption("prefs", prefs);
	options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"}); 
	
	//Creating an object of ChromeDriver
	driver = new ChromeDriver(options); 
	driver.manage().window().maximize();
	
	//Deleting all the cookies
	driver.manage().deleteAllCookies();

	//Specifying pageLoadTimeout and Implicit wait
	driver.manage().timeouts().pageLoadTimeout(40, TimeUnit.SECONDS);
	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

	//launching the URL
	driver.get("https://secure.procountor.com");
  }

  /**
   * This test will login with username and password
   * Import the file in csv format
   * View the uploaded receipt
   */
  
  @Test
  public void upload_csvReceipt_and_view_receipt(){
	  
	//Logging in with UserName and password
	procountorLogin = new ProcountorLogin(driver);
	procountorLogin.loginProcountor("tutustuja123", "Tutustuja");
	
	//Validate if login is successful
	procountorHome = new ProcountorHome(driver);
	
	if(procountorHome.isLoginSuccessful())
		test.log(LogStatus.PASS, "Navigated to the specified URL");
	else
	test.log(LogStatus.FAIL, "Test Failed");
	
	//Closing the welcome popup
	procountorHome.closingWelcomePopup();
	
	//Searching import data in search box
	procountorHome.search("Import data");
	
	//Verify if search result is displayed
	if(procountorHome.verifySearchResultIsDisplayed())
		test.log(LogStatus.PASS, "Search result is displayed");
	else
		test.log(LogStatus.FAIL, "Search result is not displayed");
	
	//Clicking on search result
	procountorHome.clickTextSearched();
	
	//Selecting the import type
	procountorImportdata = new ProcountorImportData(driver);
	procountorImportdata.clickImportTypeDd();
	if(procountorImportdata.selectValueDD("Import invoices (CSV, DDE, ZIP)"))
		test.log(LogStatus.PASS, "Import type is selected as Import invoices (CSV, DDE, ZIP)");
	else
	    test.log(LogStatus.FAIL, "Error occured in selecting import type");
	
	//Importing the file
	procountorImportdata.importFile();
	
	//Check if import is successful
	if(procountorImportdata.checkSuccessfulImport())
		test.log(LogStatus.PASS, "File is imported successfully");
	else
	    test.log(LogStatus.FAIL, "Error occured while importing file");
		
	
	//Getting the Receipt Type and Receipt Name of imported Receipt
	String receiptNameImported = procountorImportdata.readReceiptName();
	String receiptTypeImported = procountorImportdata.readReceiptType();
	
	//Saving and fetching the imported invoice
	procountorImportdata.clickSaveBtn();
	procountorImportdata.fetchImportedInvoice();
	
	//Verify if imported data is same as fetched data
	procountorReceiptSearch = new ProcountorReceiptSearch(driver);
	String receiptNameSearched = procountorReceiptSearch.readReceiptName();
	String receiptTypeSearched = procountorReceiptSearch.readReceiptType();
	
	if((receiptTypeImported.equalsIgnoreCase(receiptTypeSearched))&&(receiptNameImported.equalsIgnoreCase(receiptNameSearched)))
		test.log(LogStatus.PASS, "Imported invoice is fetched successfully");
	else
	    test.log(LogStatus.FAIL, "Error occured while fetching imported invoice");
	
	//Viewing the imported invoice
	procountorReceiptSearch.viewReceipt();
	test.log(LogStatus.PASS, "Imported invoice is viewed");
  }
  
  @AfterTest
  public void closeWebdriver() {
	
	  //Closing webdriver
      driver.close();
      
      //End reports test
      report.endTest(test);
      report.flush();
  }

}
