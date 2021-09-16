package com.cybertekschool.data_provider;


import com.cybertekschool.utilities.BrowserUtils;
import com.cybertekschool.utilities.ConfigurationReader;
import com.cybertekschool.utilities.Driver;
import org.junit.Test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CanvasDataProvider {

	WebElement insightTab;


	@Test
	public void Canvas() throws InterruptedException, IOException {

		//====================================================================================================

		String path = "C:\\Users\\yakup\\IdeaProjects\\CanvasDataProvider\\src\\test\\resources\\dersler.xlsx";

		Workbook workbook = WorkbookFactory.create(new File(path));

		Sheet lessonList = workbook.getSheet(ConfigurationReader.get("dersler"));
		int lessonsLastRow = lessonList.getLastRowNum();

		Sheet studentList = workbook.getSheet(ConfigurationReader.get("groups"));
		int studentsLastRow = studentList.getLastRowNum();

		ArrayList<Cell> LessonsArray = new ArrayList<>();
		ArrayList<Cell> StudentsArray = new ArrayList<>();

		//========================================================================

		String mesaj = lessonList.getRow(0).getCell(0).getStringCellValue();
		System.out.println(mesaj);

		for (int i = 1; i <= lessonsLastRow; i++) {
			LessonsArray.add(lessonList.getRow(i).getCell(0));
			System.out.println(LessonsArray.get(i - 1).getStringCellValue());
		}

		for (int i = 1; i <= studentsLastRow; i++) {
			StudentsArray.add(studentList.getRow(i).getCell(0));
		}

		//========================================================================

		String mail = ConfigurationReader.get("username");
		String password = ConfigurationReader.get("password");

		//========================================================================

		WebDriver driver = Driver.get();
		driver.manage().window().maximize();
		driver.get(ConfigurationReader.get("url"));

		//========================================================================

		driver.findElement(By.xpath("//*[@id=\"okta-signin-username\"]")).sendKeys(mail);
		driver.findElement(By.xpath("//*[@id=\"okta-signin-password\"]")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"okta-signin-submit\"]")).click();

		BrowserUtils.clickWithWait(By.xpath("//*[@id=\"form8\"]/div[2]/input"), 5);

		Thread.sleep(15000);

		//************************************************
		//** OPEN YOUR MOBILE PHONE AND APPROVE OKTA LOGIN
		//************************************************

		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		driver.close();
		driver.switchTo().window(tabs2.get(0));
		driver.get("https://learn.cybertekschool.com/courses/540");

		//========================================================================

		for (Cell lesson : LessonsArray) {
			driver.get(lesson.getStringCellValue());
			driver.switchTo().defaultContent();
			driver.switchTo().frame(1);

			BrowserUtils.clickWithWait(By.xpath("//*[@id=\"tab-insights\"]"), 10);

			for (Cell student : StudentsArray) {

				for (int i = 1; i < 5; i++) {
					try {
						driver.switchTo().frame(1);
						break;
					} catch (Exception e) {
						e.printStackTrace();
					}


//					BrowserUtils.waitFor(1);

					BrowserUtils.clickWithTimeOut(By.xpath("//*[.=\"" + student.getStringCellValue() + "\"]"), 10);

					System.out.println(driver.findElement(By.xpath("//*[.=\"" + student.getStringCellValue() + "\"]")).getText());

					BrowserUtils.waitFor(1);

					BrowserUtils.scrollToElement(driver.findElement(By.className("ScreenReaderContent")));

					for (int j = 1; j < 5; j++) {
						try {
							driver.switchTo().defaultContent();
							break;
						} catch (Exception e) {
							e.printStackTrace();
						}

					}

					BrowserUtils.scrollToElement(driver.findElement(By.id("breadcrumbs")));
					BrowserUtils.waitFor(2);


				}


			}

		}
	}
}

