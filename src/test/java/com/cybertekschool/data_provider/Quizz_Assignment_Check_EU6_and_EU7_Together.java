package com.cybertekschool.data_provider;


import com.cybertekschool.utilities.BrowserUtils;
import com.cybertekschool.utilities.ConfigurationReader;
import com.cybertekschool.utilities.Driver;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Quizz_Assignment_Check_EU6_and_EU7_Together extends BasePage {


	String sheetAdded1 = "EU7-QuizAssign (2)";
	String sheetAdded2 = "EU6-QuizAssign";


	public Quizz_Assignment_Check_EU6_and_EU7_Together() throws IOException {
	}


	@Test
	public void Canvas() throws InterruptedException, IOException {


		ArrayList<String> excelPagesList = new ArrayList<>();
		excelPagesList.add(sheetAdded1);
//		sheetsList.add(sheetAdded2);

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
		//==================================================================================

		wait.until(ExpectedConditions.numberOfWindowsToBe(2));

		ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		driver.close();
		driver.switchTo().window(tabs2.get(0));
		driver.get("https://learn.cybertekschool.com/courses/540");

		//==================================================================================


		//====CREATING GROUPS==============================================================================

		for (int k = 0; k < excelPagesList.size(); k++) {

			Sheet currentExcelPage = workbook.getSheet(excelPagesList.get(k));
			int lastRowNumInSheet = currentExcelPage.getLastRowNum();

			ArrayList<String> allURLsList = new ArrayList<>();
			ArrayList<String> All_Students_List = new ArrayList<>();

			//get number of total task (recording or quiz/assign)
			int lastColNumInSheet = currentExcelPage.getRow(URLs_Row_StartsFrom).getLastCellNum();
			System.out.println("Last column number in current sheet: " + lastColNumInSheet + "\n");


			//get all URLs in a list
			for (int i = URLs_or_data_Column_StartsFrom; i < lastColNumInSheet; i++) {
				if (currentExcelPage.getRow(URLs_Row_StartsFrom).getCell(i) != null) {
					String taskNameFromURL = currentExcelPage.getRow(URLs_Row_StartsFrom).getCell(i).getStringCellValue();
					allURLsList.add(taskNameFromURL);
					//!!!!!!!CHANGE TO STRING VARIABLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					System.out.println("Quiz/Assign Name: " + taskNameFromURL);
				}
			}

			//!!!!!!!CHANGE TO STRING VARIABLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			System.out.println("Number of Total Quiz/Assign: " + allURLsList.size() + "\n");

			//get all students in a list
			for (int i = studentNameRowStartsFrom; i <= lastRowNumInSheet; i++) {
				if (currentExcelPage.getRow(i).getCell(studentNameColumnStartsFrom) != null) {
					String studentName = currentExcelPage.getRow(i).getCell(studentNameColumnStartsFrom).getStringCellValue();
					All_Students_List.add(studentName);
					System.out.println("students: " + studentName);
				}
			}
			System.out.println();








			int eu7CountGroup1 = 0;
			int eu7CountGroup2 = 0;
			int eu7CountGroup3 = 0;
			int eu6CountGroup1 = 0;


			//going through each student
			for (int studentIndexNo = 0; studentIndexNo < All_Students_List.size(); studentIndexNo++) {

				try {

					System.out.println("\nStudent in progress: " + All_Students_List.get(studentIndexNo));
					int taskCount = 0;

					//getting data from each student
					ArrayList<Double> dataList = new ArrayList<>();
					for (int i = URLs_or_data_Column_StartsFrom; i < lastColNumInSheet; i++) {
						double studentData = currentExcelPage.getRow(studentIndexNo + studentNameRowStartsFrom).getCell(i).getNumericCellValue();
						dataList.add(studentData);
					}
					System.out.println("--number of total data from student: " + dataList.size());

					//check if the number of total data and number of total urls is the same
					if (dataList.size() != allURLsList.size()) {
						System.out.println("number of total data and number of total urls does not match");
						System.exit(0);
					}


					//if there is no missing recording/quiz, print out a message and don't create a folder
					if (!dataList.contains(0.0)) {
						System.out.println("--No Missing Quiz/Assign");
					} else {

						//Creating Folder for screenshots
						String studentFolderString = "";

						if (excelPagesList.get(k).equals(sheetAdded1)) {
							if (studentIndexNo < eu7group1) {
								studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\Group-11 Recordings\\" + ++eu7CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
							} else if (studentIndexNo >= eu7group1 && studentIndexNo < eu7group1 + eu7group2) {
								studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\Group-12 Recordings\\" + ++eu7CountGroup2 + " - " + All_Students_List.get(studentIndexNo);
							} else {
								studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\Group-23 Recordings\\" + ++eu7CountGroup3 + " - " + All_Students_List.get(studentIndexNo);
							}
						} else if (excelPagesList.get(k).equals(sheetAdded2)) {
							studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\Group-12 Recordings\\" + ++eu6CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
						}
						File studentFolderFile = new File(studentFolderString);

						//if folder is already exist, delete it
						if (studentFolderFile.exists()) {
							FileUtils.deleteDirectory(studentFolderFile);
						}


						//going through each student data (0 and 1s)
						for (int j = 0; j < dataList.size(); j++) {

							if (dataList.get(j) == 0 && !allURLsList.get(j).equals("null")) {

								driver.get(allURLsList.get(j));

								//!!!!!!!CHANGE TO STRING VARIABLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
								System.out.println("--Quiz/Assign being checked: " + allURLsList.get(j));












								//======================================================================
								//!!!Distinction point!!!
								//click on people---------------------------------------------------------
								BrowserUtils.clickWithJSWait(By.xpath("//a[@class='people']"), 3);


								//find and click on student name------------------------------------------

								JavascriptExecutor js = (JavascriptExecutor) driver;

								isDisplayed:
								for (int i = 0; i < 12; i++) {
									try {
										js.executeScript("window.scrollBy(0,15000)", "");

										if (driver.findElement(By.xpath("//a[contains(text(),'" + All_Students_List.get(studentIndexNo) + "')]")).isDisplayed()) {
											BrowserUtils.clickWithJSWait(By.xpath("//a[contains(text(),'" + All_Students_List.get(studentIndexNo) + "')]"), 2);
											break isDisplayed;
										}

									} catch (Exception e) {
										Thread.sleep(1000);
									}
								}

								BrowserUtils.waitForClickablility(By.xpath("//a[contains(@aria-label,'View grades')]"), 5);
								BrowserUtils.clickWithTimeOut(By.xpath("//a[contains(@aria-label,'View grades')]"), 5);
								Thread.sleep(500);









								//------------------------------------------------------------------------------

								TakesScreenshot ts = (TakesScreenshot) driver;
								File screenshot = ts.getScreenshotAs(OutputType.FILE);
								File pngFolder = new File(studentFolderString + "\\" + All_Students_List.get(studentIndexNo) + " " + ++taskCount + ".png");
								FileUtils.copyFile(screenshot, pngFolder);

							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}
}





