package com.cybertekschool.data_provider_3;

import com.cybertekschool.utilities.BrowserUtils;
import com.cybertekschool.utilities.ConfigurationReader;
import com.cybertekschool.utilities.Driver;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BasePage_3 {

	WebDriver driver;

	public BasePage_3() throws IOException {
	}

	//*****ONLY ADJUST THESE VARIABLES*****
	//*************************************

	//credentials
	String username = "yakupck@cybertekschool.com";
	String password = "Trabzon-61";


	//******** GROUP INFO *************************
	//number of groups you have
	int totalEU7groups = 3;
	int totalEU6groups = 1;


	//EU7 groups names
	String groupName1 = "Group-11";
	String groupName2 = "Group-12";
	String groupName3 = "Group-23";


	//EU6 groups names
	String groupName4 = "Group-12";


	//number of students in your groups
	int groupNum1 = 14;      //EU7 group1
	int groupNum2 = 12;      //EU7 group2
	int groupNum3 = 13;      //EU7 group3
	int groupNum4 = 12;      //EU6 group1

	//-----------------------------------

//******** GROUP INFO ***************************
//	//number of groups you have
//	int totalEU7groups = 2;
//	int totalEU6groups = 2;
//
//	//EU7 groups names
//	String groupName1 = "Group-11";
//	String groupName2 = "Group-12";
//
//	//EU6 groups names
//	String groupName3 = "Group-12";
//	String groupName4 = "Group-23";
//
//	//number of students in your groups
//	int groupNum1 = 14;      //EU7 group1
//	int groupNum2 = 12;      //EU7 group2
//
//	int groupNum3 = 12;      //EU6 group1
//	int groupNum4 = 14;      //EU6 group2


	//*************************************
	//*************************************


	//excel sheet recording row no
	int URLs_Row_StartsFrom = 0;

	//excel sheet recording column no
	int URLs_or_data_Column_StartsFrom = 3;

	//excel sheet student name row no
	int studentNameRowStartsFrom = 3;

	//excel sheet student name column no
	int studentNameColumnStartsFrom = 2;

	String path = "C:\\Users\\yakup\\IdeaProjects\\CanvasDataProvider\\src\\test\\resources\\dersler.xlsx";
	Workbook workbook = WorkbookFactory.create(new File(path));


	//=====================================================================================================


	public void commonThings(ArrayList<String> messages, String whichOne, ArrayList<String> excelPagesList, String sheetAdded1, String sheetAdded2, String sheetAdded3, String sheetAdded4) throws InterruptedException {

		login();

		Sheet currentExcelPage = null;
		for (int k = 0; k < excelPagesList.size(); k++) {
			if (excelPagesList.get(k) != null) {

				currentExcelPage = workbook.getSheet(excelPagesList.get(k));
				int lastRowNumInSheet = currentExcelPage.getLastRowNum();

				ArrayList<String> allURLsList = new ArrayList<>();
				ArrayList<String> All_Students_List = new ArrayList<>();

				//get last column number
				int lastColNumInSheet = currentExcelPage.getRow(URLs_Row_StartsFrom).getLastCellNum();
				System.out.println("\n****************************************************************");
				System.out.println("Last column number in current sheet: " + lastColNumInSheet + "\n");


				//********************\\
				//get all URLs in a list
				for (int i = URLs_or_data_Column_StartsFrom; i < lastColNumInSheet; i++) {
					if (currentExcelPage.getRow(URLs_Row_StartsFrom).getCell(i) != null) {
						String taskNameFromURL = currentExcelPage.getRow(URLs_Row_StartsFrom).getCell(i).getStringCellValue();
						if (!taskNameFromURL.isEmpty()) {
							allURLsList.add(taskNameFromURL);
							//!!!!!!!CHANGE TO STRING VARIABLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
							System.out.println(messages.get(0) + taskNameFromURL);
						}
					}
				}

				//!!!!!!!CHANGE TO STRING VARIABLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				System.out.println(messages.get(1) + allURLsList.size() + "\n");

				//********************\\
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


						//****************************\\
						//getting data from each student
						ArrayList<Double> dataList = new ArrayList<>();
						int missingTask = 0;
						for (int i = URLs_or_data_Column_StartsFrom; i < URLs_or_data_Column_StartsFrom + allURLsList.size(); i++) {
							double studentData = currentExcelPage.getRow(studentIndexNo + studentNameRowStartsFrom).getCell(i).getNumericCellValue();
							dataList.add(studentData);
							if (studentData == 0.0) {
								missingTask++;
							}
						}
						System.out.println("--number of total data from student: " + dataList.size());
						System.out.println(messages.get(4) + missingTask);


						//check if the number of total data and number of total urls is the same
						if (dataList.size() != allURLsList.size()) {
							System.out.println("number of total data and number of total urls does not match");
							System.exit(0);
						}


						//if there is no missing recording/quiz, print out a message and don't create a folder
						if (!dataList.contains(0.0)) {
							System.out.println(messages.get(2));
						} else {
							//Creating Folder for screenshots
							String studentFolderString = "";


							//CREATE FOLDER FOR SCREENSHOTS BASED ON GROUPS-NAMING RULES
							if (totalEU7groups == 3 && totalEU6groups == 1) {

								if (whichOne.equals("recordings")) {
									if (excelPagesList.get(k).equals(sheetAdded1)) {
										if (studentIndexNo < groupNum1) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName1 + " Recordings\\" + ++eu7CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
										} else if (studentIndexNo >= groupNum1 && studentIndexNo < groupNum1 + groupNum2) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName2 + " Recordings\\" + ++eu7CountGroup2 + " - " + All_Students_List.get(studentIndexNo);
										} else {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName3 + " Recordings\\" + ++eu7CountGroup3 + " - " + All_Students_List.get(studentIndexNo);
										}
									} else if (excelPagesList.get(k).equals(sheetAdded2)) {
										studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\" + groupName4 + " Recordings\\" + ++eu6CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
									}
								} else if (whichOne.equals("quizAssign")) {
									if (excelPagesList.get(k).equals(sheetAdded1)) {
										if (studentIndexNo < groupNum1) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName1 + " Quizzes\\" + ++eu7CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
										} else if (studentIndexNo >= groupNum1 && studentIndexNo < groupNum1 + groupNum2) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName2 + " Quizzes\\" + ++eu7CountGroup2 + " - " + All_Students_List.get(studentIndexNo);
										} else {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName3 + " Quizzes\\" + ++eu7CountGroup3 + " - " + All_Students_List.get(studentIndexNo);
										}
									} else if (excelPagesList.get(k).equals(sheetAdded3)) {
										studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\" + groupName4 + " Quizzes\\" + ++eu6CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
									} else if (excelPagesList.get(k).equals(sheetAdded2)) {
										if (studentIndexNo < groupNum1) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName1 + " Assignments\\" + ++eu7CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
										} else if (studentIndexNo >= groupNum1 && studentIndexNo < groupNum1 + groupNum2) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName2 + " Assignments\\" + ++eu7CountGroup2 + " - " + All_Students_List.get(studentIndexNo);
										} else {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName3 + " Assignments\\" + ++eu7CountGroup3 + " - " + All_Students_List.get(studentIndexNo);
										}
									} else if (excelPagesList.get(k).equals(sheetAdded4)) {
										studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\" + groupName4 + " Assignments\\" + ++eu6CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
									}
								}
							} else if (totalEU7groups == 2 && totalEU6groups == 2) {

								if (whichOne.equals("recordings")) {
									if (excelPagesList.get(k).equals(sheetAdded1)) {
										if (studentIndexNo < groupNum1) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName1 + " Recordings\\" + ++eu7CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
										} else if (studentIndexNo >= groupNum1 && studentIndexNo < groupNum1 + groupNum2) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName2 + " Recordings\\" + ++eu7CountGroup2 + " - " + All_Students_List.get(studentIndexNo);
										}
									} else if (excelPagesList.get(k).equals(sheetAdded2)) {
										if (studentIndexNo < groupNum3) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\" + groupName3 + " Recordings\\" + ++eu7CountGroup3 + " - " + All_Students_List.get(studentIndexNo);
										} else if (studentIndexNo >= groupNum3 && studentIndexNo < groupNum3 + groupNum4) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\" + groupName4 + " Recordings\\" + ++eu6CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
										}
									}
								} else if (whichOne.equals("quizAssign")) {
									if (excelPagesList.get(k).equals(sheetAdded1)) {
										if (studentIndexNo < groupNum1) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName1 + " Quizzes\\" + ++eu7CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
										} else if (studentIndexNo >= groupNum1 && studentIndexNo < groupNum1 + groupNum2) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName2 + " Quizzes\\" + ++eu7CountGroup2 + " - " + All_Students_List.get(studentIndexNo);
										}
									} else if (excelPagesList.get(k).equals(sheetAdded3)) {
										if (studentIndexNo < groupNum3) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\" + groupName3 + " Quizzes\\" + ++eu7CountGroup3 + " - " + All_Students_List.get(studentIndexNo);
										} else if (studentIndexNo >= groupNum3 && studentIndexNo < groupNum3 + groupNum4) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\" + groupName4 + " Quizzes\\" + ++eu6CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
										}
									} else if (excelPagesList.get(k).equals(sheetAdded2)) {

										if (studentIndexNo < groupNum1) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName1 + " Assignments\\" + ++eu7CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
										} else if (studentIndexNo >= groupNum1 && studentIndexNo < groupNum1 + groupNum2) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\" + groupName2 + " Assignments\\" + ++eu7CountGroup2 + " - " + All_Students_List.get(studentIndexNo);
										}
									} else if (excelPagesList.get(k).equals(sheetAdded4)) {
										if (studentIndexNo < groupNum3) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\" + groupName3 + " Assignments\\" + ++eu7CountGroup3 + " - " + All_Students_List.get(studentIndexNo);
										} else if (studentIndexNo >= groupNum3 && studentIndexNo < groupNum3 + groupNum4) {
											studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\" + groupName4 + " Assignments\\" + ++eu6CountGroup1 + " - " + All_Students_List.get(studentIndexNo);
										}
									}
								}

							}

							//To create a file/folder by Java
							File studentFolderFile = new File(studentFolderString);

							//if folder is already exist, delete it
							if (studentFolderFile.exists()) {
								FileUtils.deleteDirectory(studentFolderFile);
							}

							//****************************************\\
							//going through each student data (0 and 1s)
							for (int j = 0; j < dataList.size(); j++) {

								taskCount++;
								if (dataList.get(j) == 0 && !allURLsList.get(j).equals("null")) {

									driver.get(allURLsList.get(j));


									//!!!!!!!CHANGE TO STRING VARIABLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
									System.out.println(messages.get(3) + allURLsList.get(j));


									if (whichOne.equals("recordings")) {
										//for RECORDINGS
										recordingsPage(driver, All_Students_List, studentIndexNo, studentFolderString, taskCount);
									} else if (whichOne.equals("quizAssign")) {
										//for quiz and assignments
										quizAssignPage(k, excelPagesList, driver, All_Students_List, studentIndexNo, studentFolderString, taskCount, sheetAdded1, sheetAdded2, sheetAdded3, sheetAdded4);
									}


								} else if (dataList.get(j) == 0 && allURLsList.get(j).equals("null")) {
									//!!!!!!!CHANGE TO STRING VARIABLE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
									System.out.println(messages.get(5));

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




	//==================================================================================================================================================================================================================================================================================================================
	public void recordingsPage(WebDriver driver, ArrayList<String> All_Students_List, int studentIndexNo, String studentFolderString, int taskCount) throws InterruptedException, IOException {


		for (int i = 0; i < 20; i++) {
			try {
				driver.switchTo().defaultContent();
				break;
			} catch (Exception e) {
				Thread.sleep(800);
				e.printStackTrace();
			}
		}


		for (int i = 0; i < 20; i++) {
			try {
				driver.switchTo().frame(1);
				break;
			} catch (Exception e) {
				Thread.sleep(800);
				e.printStackTrace();
			}
		}


		BrowserUtils.clickWithJSWait(By.xpath("//*[@id=\"tab-insights\"]"), 7);


		boolean key = false;
		stdName:
		for (int i = 0; i < 2; i++) {
			try {
				WebElement studentName = driver.findElement(By.xpath("//span[@name = '" + All_Students_List.get(studentIndexNo) + "']"));
				key = true;
				break stdName;
			} catch (Exception e) {
				Thread.sleep(500);
				key = false;
			}
		}

		if (key) {
			for (int i = 0; i < 3; i++) {
//				BrowserUtils.clickWithTimeOut(By.xpath("//span[@name = '" + All_Students_List.get(studentIndexNo) + "']"), 2);
				BrowserUtils.clickWithJSWait(By.xpath("//span[@name = '" + All_Students_List.get(studentIndexNo) + "']"), 2);
				Thread.sleep(200);
			}
		}


		//goToScreenReaderContent
		ScreenReaderContent:
		for (int i = 0; i < 3; i++) {
			try {
				BrowserUtils.scrollToElement(driver.findElement(By.className("ScreenReaderContent")));

				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].style.border='2px solid red'", driver.findElement(By.cssSelector("h2.MediaInsights__chartTitle")));
				jsExecutor.executeScript("arguments[0].style.background='yellow'", driver.findElement(By.cssSelector("h2.MediaInsights__chartTitle")));
				jsExecutor.executeScript("arguments[0].style.border='2px solid red'", driver.findElement(By.cssSelector("div.MediaInsights__chartLayout")));

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

		breadcrumbs:
		for (int i = 0; i < 2; i++) {
			try {
				BrowserUtils.scrollToElement(driver.findElement(By.id("breadcrumbs")));
				break breadcrumbs;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		recordingTitle:
		for (int i = 0; i < 3; i++) {
			try {

				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].style.border='2px solid red'", driver.findElement(By.cssSelector("h1.page-title")));

				break recordingTitle;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}

		//***************\\
		//trimRecordingName
		String recordingName = driver.findElement(By.cssSelector("h1.page-title")).getText();
		recordingName = recordingName.replace(":", "");
		recordingName = recordingName.replace("|", "");
		recordingName = recordingName.replace("~", "");
		recordingName = recordingName.replace("!", "");
		recordingName = recordingName.replace("%", "");
		recordingName = recordingName.replace("?", "");
		recordingName = recordingName.replace("*", "");
		recordingName = recordingName.replace(".", "");
		recordingName = recordingName.replace("{", "");
		recordingName = recordingName.replace("}", "");
		recordingName = recordingName.replace("(", "");
		recordingName = recordingName.replace(")", "");
		recordingName = recordingName.replace("RECORDING", "");
		Thread.sleep(3000);


		//*****************************************\\
		//To take screenshot
		TakesScreenshot ts = (TakesScreenshot) driver;
		File screenshot = ts.getScreenshotAs(OutputType.FILE);
		File pngFolder = new File(studentFolderString + "\\" + All_Students_List.get(studentIndexNo) + " " + taskCount + " - " + recordingName + ".png");
		FileUtils.copyFile(screenshot, pngFolder);

	}




	//==================================================================================================================================================================================================================================================================================================================
	public void quizAssignPage(int k, ArrayList<String> excelPagesList, WebDriver driver, ArrayList<String> All_Students_List, int studentIndexNo, String studentFolderString, int taskCount, String sheetAdded1, String sheetAdded2, String sheetAdded3, String sheetAdded4) throws InterruptedException, IOException {

		WebElement quizAssignWE = null;
		String quizAssigntext = null;

		if (excelPagesList.get(k).equals(sheetAdded1) || excelPagesList.get(k).equals(sheetAdded3)) {
			quizName:
			for (int i = 0; i < 3; i++) {
				try {
					quizAssignWE = driver.findElement(By.xpath("(//span[@class='ellipsible'])[4]"));
					quizAssigntext = quizAssignWE.getText();
					break quizName;
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}

		} else if (excelPagesList.get(k).equals(sheetAdded2) || excelPagesList.get(k).equals(sheetAdded4)) {

			assigName:
			for (int i = 0; i < 3; i++) {
				try {
					quizAssignWE = driver.findElement(By.xpath("//h1[@class='title']"));
					quizAssigntext = quizAssignWE.getText();
					break assigName;
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}
		}


		BrowserUtils.clickWithJSWait(By.xpath("//a[@class='people']"), 3);

		WebElement searchButton = null;
		placeHolder:
		for (int i = 0; i < 3; i++) {
			try {
				searchButton = driver.findElement(By.xpath("//*[@placeholder='Search people']"));
				break placeHolder;
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}

		searchButton.sendKeys(All_Students_List.get(studentIndexNo));
		Thread.sleep(500);

		try {
			BrowserUtils.waitForClickablility(By.xpath("//a[contains(text(),'" + All_Students_List.get(studentIndexNo) + "')]"), 7);
		} catch (Exception e) {

		}

		BrowserUtils.clickWithJSWait(By.xpath("//a[contains(text(),'" + All_Students_List.get(studentIndexNo) + "')]"), 2);

		BrowserUtils.waitForClickablility(By.xpath("//a[contains(@aria-label,'View grades')]"), 5);
		BrowserUtils.clickWithTimeOut(By.xpath("//a[contains(@aria-label,'View grades')]"), 5);
		Thread.sleep(500);


		List<WebElement> assignmentWE = driver.findElements((By.xpath("//tbody/tr[contains(@class, 'student_assignment')]")));

		highlight:
		for (int i = 0; i < 2; i++) {

			for (WebElement webElement : assignmentWE) {
				if (webElement.getText().contains(quizAssigntext)) {

					BrowserUtils.scrollToElement(webElement);

					JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
					jsExecutor.executeScript("arguments[0].style.border='2px solid red'", webElement);
					jsExecutor.executeScript("arguments[0].style.background='yellow'", webElement);
					break highlight;
				}
			}
		}


		Thread.sleep(500);
		TakesScreenshot ts = (TakesScreenshot) driver;
		File screenshot = ts.getScreenshotAs(OutputType.FILE);
		File pngFolder = new File(studentFolderString + "\\" + All_Students_List.get(studentIndexNo) + " " + taskCount + ".png");
		FileUtils.copyFile(screenshot, pngFolder);
	}






	//=========================================================================================================
	public void login() throws InterruptedException {
		driver = Driver.get();
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get(ConfigurationReader.get("url"));


		Thread.sleep(1500);
		if (!driver.getCurrentUrl().equals("https://cybertekschool.okta.com/app/UserHome")) {

			driver.findElement(By.xpath("//*[@id=\"okta-signin-username\"]")).sendKeys(username);
			driver.findElement(By.xpath("//*[@id=\"okta-signin-password\"]")).sendKeys(password);
			driver.findElement(By.xpath("//*[@id=\"okta-signin-submit\"]")).click();
			BrowserUtils.clickWithJSWait(By.xpath("//*[@id=\"form8\"]/div[2]/input"), 5);
		}
		goCybertek();

	}

	//=========================================================================================================
	public void goCybertek() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 25);
		wait.until(ExpectedConditions.urlContains("UserHome"));

		//================================================
		//** OPEN YOUR MOBILE PHONE AND APPROVE OKTA LOGIN
		//================================================

		try {
			wait.until(ExpectedConditions.numberOfWindowsToBe(2));
			ArrayList<String> tabs2 = new ArrayList<>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
			driver.close();
			driver.switchTo().window(tabs2.get(0));
		} catch (Exception e) {
			Thread.sleep(100);
		}

		driver.get("https://learn.cybertekschool.com/courses/540");
	}


}
