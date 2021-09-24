package com.cybertekschool.old_package;


import com.cybertekschool.utilities.BrowserUtils;
import com.cybertekschool.utilities.ConfigurationReader;
import com.cybertekschool.utilities.Driver;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Recordings_EU6_Group {


	@Test
	public void Canvas() throws InterruptedException, IOException {

		//====================================================================================================

		String path = "C:\\Users\\yakup\\IdeaProjects\\CanvasDataProvider\\src\\test\\resources\\dersler.xlsx";
		Workbook workbook = WorkbookFactory.create(new File(path));

		//========================================================================

		Sheet sheet = workbook.getSheet("EU6-Recordings");
		int studentsLastRow = sheet.getLastRowNum();

		//==================================================================================

		String username = ConfigurationReader.get("username");
		String password = ConfigurationReader.get("password");

		//==================================================================================

		WebDriver driver = Driver.get();
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(ConfigurationReader.get("url"));

		//==================================================================================

		driver.findElement(By.xpath("//*[@id=\"okta-signin-username\"]")).sendKeys(username);
		driver.findElement(By.xpath("//*[@id=\"okta-signin-password\"]")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"okta-signin-submit\"]")).click();
		BrowserUtils.clickWithJSWait(By.xpath("//*[@id=\"form8\"]/div[2]/input"), 5);

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

		//==================================================================================
		//==================================================================================



		//==================================================================================
		//==================================================================================

		ArrayList<String> AllLessonsArray = new ArrayList<>();
		ArrayList<String> AllStudentsArray = new ArrayList<>();

		//===LessonsArray: get the URL's of each recording==================================
		short lastLessonNum = sheet.getRow(1).getLastCellNum();
		System.out.println("number of total recordings: " + lastLessonNum + "\n");

		for (int i = 1; i < lastLessonNum; i++) {
			if (sheet.getRow(1).getCell(i) != null) {
			String recordingName = sheet.getRow(1).getCell(i).getStringCellValue();
			AllLessonsArray.add(recordingName);
			System.out.println("Recording Name: " + recordingName);
			}
		}
		System.out.println();


		//===StudentsArray: to get each student name=======================================
		for (int i = 2; i <= studentsLastRow; i++) {
			if (sheet.getRow(i).getCell(0) != null) {
				String student = sheet.getRow(i).getCell(0).getStringCellValue();
				AllStudentsArray.add(student);
				System.out.println("students: " + student);
			}
		}
		System.out.println();

		//=====LOOPING STARTS======================================================================================
		//=====GOING THROUGH EACH STUDENTS======================================================================================

		int studentCount = 0;

		for (int studentIndexNo = 0; studentIndexNo < AllStudentsArray.size(); studentIndexNo++) {

			System.out.println( "\nStudent in progress: " + AllStudentsArray.get(studentIndexNo));
			int recordingCount = 0;

			//=====CREATING LIST FOR 1 and 0s FOR EACH STUDENT=====================================
			ArrayList<Double> watchListArray = new ArrayList<>();
			for (int i = 1; i < lastLessonNum; i++) {
				double watchedOrNot = sheet.getRow(studentIndexNo + 2).getCell(i).getNumericCellValue();
				watchListArray.add(watchedOrNot);
			}

			//=====CREATING FOLDER FOR SCREENSHOTS===================================================

			String studentFolderString = "";

			studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\Group-12\\" + ++studentCount + " - " + AllStudentsArray.get(studentIndexNo);

			File studentFolderFile = new File(studentFolderString);

			if (studentFolderFile.exists()) {
				FileUtils.deleteDirectory(studentFolderFile);
			}


			//====== GOING TROUGH EACH DATA (1 and 0) ============================================================================

			for (int j = 0; j < watchListArray.size(); j++) {

				if (watchListArray.get(j) == 0 && !AllLessonsArray.get(j).equals("null")) {

					driver.get(AllLessonsArray.get(j));
					System.out.println("--Recording being checked: " + AllLessonsArray.get(j));

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

					BrowserUtils.clickWithJSWait(By.xpath("//*[@id=\"tab-insights\"]"), 5);

					//------------------------------------------------------------------------------

					boolean key = false;
					stdName:
					for (int i = 0; i <2; i++) {
						try {
							WebElement studentName = driver.findElement(By.xpath("//span[@name = '" + AllStudentsArray.get(studentIndexNo) + "']"));
							key = true;
							break stdName;
						} catch (Exception e) {
							Thread.sleep(500);
							key = false;
						}
					}

					if (key) {
						for (int i = 0; i < 3; i++) {
							BrowserUtils.clickWithTimeOut(By.xpath("//span[@name = '" + AllStudentsArray.get(studentIndexNo) + "']"), 2);
							Thread.sleep(200);
						}
					}

					//------------------------------------------------------------------------------


					BrowserUtils.scrollToElement(driver.findElement(By.className("ScreenReaderContent")));

					for (int t = 1; t < 5; t++) {
						try {
							driver.switchTo().defaultContent();
							break;
						} catch (Exception e) {
							Thread.sleep(1000);
							e.printStackTrace();
						}

					}

					BrowserUtils.scrollToElement(driver.findElement(By.id("breadcrumbs")));


					String recordingName = driver.findElement(By.cssSelector("h1.page-title")).getText();
					recordingName = recordingName.replace(":", "");
					recordingName = recordingName.replace("|", "");
					recordingName = recordingName.replace("~", "");
					recordingName = recordingName.replace("!", "");
					recordingName = recordingName.replace("%", "");
					recordingName = recordingName.replace("RECORDING", "");
					Thread.sleep(2500);


					//-------------------------------------------------------


					TakesScreenshot ts = (TakesScreenshot) driver;
					File screenshot = ts.getScreenshotAs(OutputType.FILE);
					File pngFolder = new File(studentFolderString + "\\" + AllStudentsArray.get(studentIndexNo) + " " + ++recordingCount + " - " + recordingName + ".png");
					FileUtils.copyFile(screenshot, pngFolder);

				}
			}
		}

	}

}

//			studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\Group-12\\" + ++studentCount + " - " + AllStudentsArray.get(studentIndexNo);
//
//			File studentFolderFile = new File(studentFolderString);
//
//			if (studentFolderFile.exists()) {
//					FileUtils.deleteDirectory(studentFolderFile);
//					}


