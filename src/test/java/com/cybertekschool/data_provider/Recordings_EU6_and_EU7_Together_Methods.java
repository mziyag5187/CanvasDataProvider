package com.cybertekschool.data_provider;


import com.cybertekschool.utilities.BrowserUtils;
import com.cybertekschool.utilities.Driver;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Recordings_EU6_and_EU7_Together_Methods extends BasePage {

	public Recordings_EU6_and_EU7_Together_Methods() throws IOException {
	}

	WebDriver driver = Driver.get();

	@Test
	public void Canvas() throws InterruptedException, IOException {


		BasePage.login(driver);

		String excelPage1 = "EU7-Recordings";
		String excelPage2 = "EU6-Recordings";

		ArrayList<String> excelSheet = new ArrayList<>();
		excelSheet.add(excelPage2);
		excelSheet.add(excelPage2);

		//==================================================================================

		for (String eachExcelPage : excelSheet) {
			Sheet currentSheet = workbook.getSheet(eachExcelPage);
			int studentsLastRow = currentSheet.getLastRowNum();

			ArrayList<String> AllLessonsArray = new ArrayList<>();
			ArrayList<String> AllStudentsArray = new ArrayList<>();

			//===LessonsArray: get the URL's of each recording==================================
			short lastColumnNum = currentSheet.getRow(URLs_Row_StartsFrom).getLastCellNum();
			System.out.println("Last column no: " + lastColumnNum + "\n");


			for (int i = BasePage.URLs_or_data_Column_StartsFrom; i < lastColumnNum; i++) {
				if (currentSheet.getRow(URLs_Row_StartsFrom).getCell(i) != null) {
					String recordingName = currentSheet.getRow(URLs_Row_StartsFrom).getCell(i).getStringCellValue();
					AllLessonsArray.add(recordingName);
					System.out.println("Recording Name: " + recordingName);
				}
			}
			System.out.println();

			//===StudentsArray: to get each student name=======================================
			for (int i = studentNameRowStartsFrom; i <= studentsLastRow; i++) {
				if (currentSheet.getRow(i).getCell(studentNameColumnStartsFrom) != null) {
					String student = currentSheet.getRow(i).getCell(studentNameColumnStartsFrom).getStringCellValue();
					AllStudentsArray.add(student);
					System.out.println("students: " + student);
				}
			}
			System.out.println();

			//=====LOOPING STARTS======================================================================================
			//=====GOING THROUGH EACH STUDENTS======================================================================================

			int eu7CountGroup1 = 0;
			int eu7CountGroup2 = 0;
			int eu7CountGroup3 = 0;
			int eu6CountGroup1 = 0;


			for (int studIndex = 0; studIndex < AllStudentsArray.size(); studIndex++) {

				try {

					System.out.println("\nStudent in progress: " + AllStudentsArray.get(studIndex));
					int recordingCount = 0;

					//=====CREATING LIST FOR 1 and 0s FOR EACH STUDENT=====================================
					ArrayList<Double> watchListArray = new ArrayList<>();
					for (int i = studentNameColumnStartsFrom +1; i < lastColumnNum; i++) {
						double watchedOrNot = currentSheet.getRow(studIndex + studentNameRowStartsFrom).getCell(i).getNumericCellValue();
						watchListArray.add(watchedOrNot);
					}

					//=====CREATING FOLDER FOR SCREENSHOTS===================================================

					String studentFolderString = "";

					if (eachExcelPage.equals(excelPage1)) {
						if (studIndex < eu7group1) {
							studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\Group-11 Recordings\\" + ++eu7CountGroup1 + " - " + AllStudentsArray.get(studIndex);
						} else if (studIndex >= eu7group1 && studIndex < eu7group1 + eu7group2) {
							studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\Group-12 Recordings\\" + ++eu7CountGroup2 + " - " + AllStudentsArray.get(studIndex);
						} else {
							studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU7\\Group-23 Recordings\\" + ++eu7CountGroup3 + " - " + AllStudentsArray.get(studIndex);
						}
					} else if (eachExcelPage.equals(excelPage2)) {
						studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\EU6\\Group-12 Recordings\\" + ++eu6CountGroup1 + " - " + AllStudentsArray.get(studIndex);
					}

					File studentFolderFile = new File(studentFolderString);

					if (studentFolderFile.exists()) {
						FileUtils.deleteDirectory(studentFolderFile);
					}


					//====== GOING TROUGH EACH DATA (1 and 0) ============================================================================

					for (int j = 0; j < watchListArray.size(); j++) {

						if (watchListArray.get(j) == 0 && !AllLessonsArray.get(j).equals("null")) {

							driver.get(AllLessonsArray.get(j));

							System.out.println("--Recording being checked: " + AllLessonsArray.get(j));

							BasePage.switchToFrame(driver);

							BrowserUtils.clickWithJSWait(By.xpath("//*[@id=\"tab-insights\"]"), 7);

							BasePage.clickStudentName(driver, AllStudentsArray, studIndex);

							BasePage.goToScreenReaderContent(driver);

							BrowserUtils.scrollToElement(driver.findElement(By.id("breadcrumbs")));

							BasePage.trimRecordingName(driver);

							BasePage.takeScreenshot(driver,studentFolderString, AllStudentsArray, studIndex, recordingCount, BasePage.trimRecordingName(driver));

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
	}

}




