package com.cybertekschool.data_provider;

import com.cybertekschool.utilities.BrowserUtils;
import com.cybertekschool.utilities.ConfigurationReader;
import com.cybertekschool.utilities.Driver;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BasePage {

	public BasePage() throws IOException {
	}

	//excel sheet recording row no
	static int URLs_Row_StartsFrom = 0;

	//excel sheet recording column no
	static int URLs_or_data_Column_StartsFrom = 3;

	//excel sheet student name row no
	static int studentNameRowStartsFrom = 3;

	//excel sheet student name column no
	static int studentNameColumnStartsFrom = 2;



	//number of students in group1
	int eu7group1 = 14;
	int eu7group2 = 13;
	int eu7group3 = 14;
	int eu6group1 = 12;

	static String username = ConfigurationReader.get("username");
	static String password = ConfigurationReader.get("password");

	String path = "C:\\Users\\yakup\\IdeaProjects\\CanvasDataProvider\\src\\test\\resources\\dersler_copy.xlsx";
	Workbook workbook = WorkbookFactory.create(new File(path));

	//=========================================================


	public static void login(WebDriver driver){
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(ConfigurationReader.get("url"));

		//==================================================================================

		driver.findElement(By.xpath("//*[@id=\"okta-signin-username\"]")).sendKeys(username);
		driver.findElement(By.xpath("//*[@id=\"okta-signin-password\"]")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"okta-signin-submit\"]")).click();
		BrowserUtils.clickWithJSWait(By.xpath("//*[@id=\"form8\"]/div[2]/input"), 5);

		goCybertek(driver);

	}

	public static void goCybertek(WebDriver driver){
		WebDriverWait wait = new WebDriverWait(driver, 25);
		wait.until(ExpectedConditions.urlContains("UserHome"));

		//==================================================================================
		//** OPEN YOUR MOBILE PHONE AND APPROVE OKTA LOGIN
		//==================================================================================

		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		driver.close();
		driver.switchTo().window(tabs2.get(0));
		driver.get("https://learn.cybertekschool.com/courses/540");
	}

	public static void takeScreenshot(WebDriver driver, String folderPath, ArrayList allStudents, int index, int recordingCount, String recordingName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File screenshot = ts.getScreenshotAs(OutputType.FILE);
		File pngFolder = new File(folderPath + "\\" + allStudents.get(index) + " " + ++recordingCount + " - " + recordingName + ".png");
		FileUtils.copyFile(screenshot, pngFolder);
	}

//	public static String studentFolderString(String eachExcelPage, String excelPage1, String excelPage2, int studIndex, int eu7group1, int eu7group2, int eu7group3){
//		String studentFolderString = "";
//
//		if (eachExcelPage.equals(excelPage1)) {
//			if (studIndex < eu7group1) {
//				studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\Group-11 Recordings\\" + ++eu7CountGroup1 + " - " + AllStudentsArray.get(studIndex);
//			} else if (studIndex >= eu7group1 && studIndex < eu7group1 + eu7group2) {
//				studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\Group-12 Recordings\\" + ++eu7CountGroup2 + " - " + AllStudentsArray.get(studIndex);
//			} else {
//				studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\Group-23 Recordings\\" + ++eu7CountGroup3 + " - " + AllStudentsArray.get(studIndex);
//			}
//		} else if (eachExcelPage.equals(excelPage2)) {
//			studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\Group-12 Recordings\\" + ++eu6CountGroup1 + " - " + AllStudentsArray.get(studIndex);
//		}
//	}

	public static String trimRecordingName(WebDriver driver) throws InterruptedException {
		String recordingName = driver.findElement(By.cssSelector("h1.page-title")).getText();
		recordingName = recordingName.replace(":", "");
		recordingName = recordingName.replace("|", "");
		recordingName = recordingName.replace("~", "");
		recordingName = recordingName.replace("!", "");
		recordingName = recordingName.replace("%", "");
		recordingName = recordingName.replace("RECORDING", "");
		Thread.sleep(3000);
		return recordingName;
	}

	public static void clickStudentName(WebDriver driver, ArrayList AllStudentsArray, int studIndex) throws InterruptedException {
		boolean key = false;
		stdName:
		for (int i = 0; i < 2; i++) {
			try {
				WebElement studentName = driver.findElement(By.xpath("//span[@name = '" + AllStudentsArray.get(studIndex) + "']"));
				key = true;
				break stdName;
			} catch (Exception e) {
				Thread.sleep(500);
				key = false;
			}
		}

		if (key) {
			for (int i = 0; i < 3; i++) {
				BrowserUtils.clickWithTimeOut(By.xpath("//span[@name = '" + AllStudentsArray.get(studIndex) + "']"), 2);
				Thread.sleep(200);
			}
		}
	}

	public static void goToScreenReaderContent(WebDriver driver) throws InterruptedException {
		ScreenReaderContent:
		for (int i = 0; i < 3; i++) {
			try {
				BrowserUtils.scrollToElement(driver.findElement(By.className("ScreenReaderContent")));
				break ScreenReaderContent;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}

		for (int t = 1; t < 5; t++) {
			try {
				driver.switchTo().defaultContent();
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
				e.printStackTrace();
			}

		}
	}

	public static void switchToFrame(WebDriver driver) throws InterruptedException {
		for (int i = 0; i < 2; i++) {
			try {
				driver.switchTo().defaultContent();
				break;
			} catch (Exception e) {
				Thread.sleep(800);
				e.printStackTrace();
			}
		}


		for (int i = 0; i < 2; i++) {
			try {
				driver.switchTo().frame(1);
				break;
			} catch (Exception e) {
				Thread.sleep(800);
				e.printStackTrace();
			}
		}
	}





}
