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

public class Data_StudentBased {


	@Test
	public void Canvas() throws InterruptedException, IOException {

		//====================================================================================================

		String path = "C:\\Users\\yakup\\IdeaProjects\\CanvasDataProvider\\src\\test\\resources\\dersler.xlsx";

		Workbook workbook = WorkbookFactory.create(new File(path));

		Sheet lessonList = workbook.getSheet(ConfigurationReader.get("dersler"));
		int lessonsLastRow = lessonList.getLastRowNum();

		Sheet studentList = workbook.getSheet(ConfigurationReader.get("groups"));
		int studentsLastRow = studentList.getLastRowNum();

		ArrayList<String> LessonsArray = new ArrayList<>();
		ArrayList<String> StudentsArray = new ArrayList<>();

		//========================================================================


		for (int i = 0; i <= lessonsLastRow; i++) {
			LessonsArray.add(lessonList.getRow(i).getCell(0).getStringCellValue());
			System.out.println(LessonsArray.get(i));
		}

		for (int i = 0; i <= studentsLastRow; i++) {
			StudentsArray.add(studentList.getRow(i).getCell(0).getStringCellValue());
			System.out.println(StudentsArray.get(i));
		}

		//==================================================================================

		String username = ConfigurationReader.get("username");
		String password = ConfigurationReader.get("password");

		//==================================================================================

		WebDriver driver = Driver.get();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
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

		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		driver.close();
		driver.switchTo().window(tabs2.get(0));
		driver.get("https://learn.cybertekschool.com/courses/540");

		//==================================================================================

		int studentCount = 0;

		for (int k = 0; k < 10; k++) {


			for (String student : StudentsArray) {
				++studentCount;
				System.out.println(student);
				int lessonCount = 0;

				String studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\" + studentCount + " - " + student;
				File studentFolderFile = new File(studentFolderString);

				if (studentFolderFile.exists()) {
					FileUtils.deleteDirectory(studentFolderFile);
				}

				//-----------------------------------------------

				for (int j = 0; j < LessonsArray.size(); j++) {

					driver.get(LessonsArray.get(j));

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

					for (int i = 0; i < 3; i++) {
						BrowserUtils.clickWithTimeOut(By.xpath("//span[@name = '" + student + "']"), 3);
						Thread.sleep(200);
					}

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
					File pngFolder = new File(studentFolderString + "\\" + student + " " + ++lessonCount + " - " + recordingName + ".png");
					FileUtils.copyFile(screenshot, pngFolder);

				}

			}

		}

	}
}


