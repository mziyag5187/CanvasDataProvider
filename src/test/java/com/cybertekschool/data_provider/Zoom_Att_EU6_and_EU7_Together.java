package com.cybertekschool.data_provider;


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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;




public class Zoom_Att_EU6_and_EU7_Together extends BasePage{



	String sheetAdded1 = "Zoom-EU7";
	String sheetAdded2 = "Zoom-EU6";

	String recordingSheet1 = "EU7-Recordings";
	String recordingSheet2 = "EU6-Recordings";



	public Zoom_Att_EU6_and_EU7_Together() throws IOException {
	}



	@Test
	public void Canvas() throws InterruptedException, IOException {


		ArrayList<String> sheetsList = new ArrayList<>();
		sheetsList.add(sheetAdded1);
		sheetsList.add(sheetAdded2);


		//==========================================================


		for (String excelPage : sheetsList) {

			//=== GET ALL STUDENT NAMES FROM EU7 and EU6 GROUPS ===

			//--- Create Sheet Object based on Batch---------------
			Sheet sheet1 = null;
			Sheet sheet2 = null;

			if (excelPage.equals(sheetAdded1)) {
				sheet1 = workbook.getSheet(recordingSheet1);
				sheet2 = workbook.getSheet(sheetAdded1);
			} else if (excelPage.equals(sheetAdded2)) {
				sheet1 = workbook.getSheet(recordingSheet2);
				sheet2 = workbook.getSheet(sheetAdded2);
			}


			//--- Get all student names from groups ---------------

			ArrayList<String> AllStudentsArray = new ArrayList<>();
			int studentsLastRow = sheet1.getLastRowNum();

			for (int i = 2; i <= studentsLastRow; i++) {
				if (sheet1.getRow(i).getCell(0) != null) {
					String student = sheet1.getRow(i).getCell(0).getStringCellValue();
					AllStudentsArray.add(student);
				}
			}
			System.out.println();


			//===GET ALL STUDENT NAMES FROM ZOOM LISTS==========================================

			//---Get all names and attendances duration in key value format from zoom list
			Map<String, Double> AllZoomAttendancesMap = new HashMap<>();
			int zoomLastRow = sheet2.getLastRowNum();

			String student = "";
			Double duration = 0.0;

			for (int i = 0; i <= zoomLastRow; i++) {
				if (sheet2.getRow(i).getCell(0) != null) {
					student = sheet2.getRow(i).getCell(0).getStringCellValue();
				}

				if (sheet2.getRow(i).getCell(2) != null) {
					duration = sheet2.getRow(i).getCell(2).getNumericCellValue();
				}
				AllZoomAttendancesMap.put(student, duration);
			}
			System.out.println();


			//===GET STUDENT NAME FROM GROUPS AND COMPARE IT TO ZOOM LIST======================


			for (int i = 0; i < AllStudentsArray.size(); i++) {
				if (excelPage.equals("Zoom-EU7")) {
					if (i < 14) {
						System.out.println("EU7 / Group-11: " + AllStudentsArray.get(i) + ": " + AllZoomAttendancesMap.get(AllStudentsArray.get(i)));
						if (i == 13) {
							System.out.println();
						}
					} else if (i >= 14 && i < 27) {
						System.out.println("EU7 / Group-12: " + AllStudentsArray.get(i) + ": " + AllZoomAttendancesMap.get(AllStudentsArray.get(i)));
						if (i == 26) {
							System.out.println();
						}
					} else {
						System.out.println("EU7 / Group-23: " + AllStudentsArray.get(i) + ": " + AllZoomAttendancesMap.get(AllStudentsArray.get(i)));
					}

				} else if (excelPage.equals("Zoom-EU6")) {
					System.out.println("EU6 / Group-12: " + AllStudentsArray.get(i) + ": " + AllZoomAttendancesMap.get(AllStudentsArray.get(i)));
				}
				System.out.println();

			}





		}



	}
}



/*


		for(String group:groups){
				Sheet sheet=workbook.getSheet(group);
				int studentsLastRow=sheet.getLastRowNum();

				ArrayList<String> AllLessonsArray=new ArrayList<>();
		ArrayList<String> AllStudentsArray=new ArrayList<>();

		//===LessonsArray: get the URL's of each recording==================================
		short lastLessonNum=sheet.getRow(1).getLastCellNum();
		System.out.println(lastLessonNum);
		System.out.println("number of total recordings: "+lastLessonNum+"\n");


		for(int i=1;i<lastLessonNum; i++){
		if(sheet.getRow(1).getCell(i)!=null){
		String recordingName=sheet.getRow(1).getCell(i).getStringCellValue();
		AllLessonsArray.add(recordingName);
		System.out.println("Recording Name: "+recordingName);
		}
		}
		System.out.println();

		//===StudentsArray: to get each student name=======================================
		for(int i=2;i<=studentsLastRow;i++){
		if(sheet.getRow(i).getCell(0)!=null){
		String student=sheet.getRow(i).getCell(0).getStringCellValue();
		AllStudentsArray.add(student);
		System.out.println("students: "+student);
		}
		}
		System.out.println();

		//=====LOOPING STARTS======================================================================================
		//=====GOING THROUGH EACH STUDENTS======================================================================================

		int studentCount1=0;
		int studentCount2=0;
		int studentCount3=0;
		int studentCount4=0;


		for(int studentIndexNo=0;studentIndexNo<AllStudentsArray.size();studentIndexNo++){

		try{

		System.out.println("\nStudent in progress: "+AllStudentsArray.get(studentIndexNo));
		int recordingCount=0;

		//=====CREATING LIST FOR 1 and 0s FOR EACH STUDENT=====================================
		ArrayList<Double> watchListArray=new ArrayList<>();
		for(int i=1;i<lastLessonNum; i++){
		double watchedOrNot=sheet.getRow(studentIndexNo+2).getCell(i).getNumericCellValue();
		watchListArray.add(watchedOrNot);
		}

		//=====CREATING FOLDER FOR SCREENSHOTS===================================================

		String studentFolderString="";

		if(group.equals("EU7")){
		if(studentIndexNo<=14){
		studentFolderString=System.getProperty("user.dir")+"\\target\\SCREENSHOTS\\EU7\\Group-11\\"+ ++studentCount1+" - "+AllStudentsArray.get(studentIndexNo);
		}else if(studentIndexNo>14&&studentIndexNo<=27){
		studentFolderString=System.getProperty("user.dir")+"\\target\\SCREENSHOTS\\EU7\\Group-12\\"+ ++studentCount2+" - "+AllStudentsArray.get(studentIndexNo);
		}else{
		studentFolderString=System.getProperty("user.dir")+"\\target\\SCREENSHOTS\\EU7\\Group-23\\"+ ++studentCount3+" - "+AllStudentsArray.get(studentIndexNo);
		}
		}else if(group.equals("EU6")){
		studentFolderString=System.getProperty("user.dir")+"\\target\\SCREENSHOTS\\EU6\\Group-12\\"+ ++studentCount4+" - "+AllStudentsArray.get(studentIndexNo);
		}

		File studentFolderFile=new File(studentFolderString);

		if(studentFolderFile.exists()){
		FileUtils.deleteDirectory(studentFolderFile);
		}


		//====== GOING TROUGH EACH DATA (1 and 0) ============================================================================

		for(int j=0;j<watchListArray.size();j++){

		if(watchListArray.get(j)==0&&!AllLessonsArray.get(j).equals("null")){

		driver.get(AllLessonsArray.get(j));
		System.out.println("--Recording being checked: "+AllLessonsArray.get(j));

		for(int i=0;i< 2;i++){
		try{
		driver.switchTo().defaultContent();
		break;
		}catch(Exception e){
		Thread.sleep(800);
		e.printStackTrace();
		}
		}


		for(int i=0;i< 2;i++){
		try{
		driver.switchTo().frame(1);
		break;
		}catch(Exception e){
		Thread.sleep(800);
		e.printStackTrace();
		}
		}

		BrowserUtils.clickWithWait(By.xpath("//*[@id=\"tab-insights\"]"),7);

		//------------------------------------------------------------------------------

		boolean key=false;
		stdName:
		for(int i=0;i< 2;i++){
		try{
		WebElement studentName=driver.findElement(By.xpath("//span[@name = '"+AllStudentsArray.get(studentIndexNo)+"']"));
		key=true;
		break stdName;
		}catch(Exception e){
		Thread.sleep(500);
		key=false;
		}
		}

		if(key){
		for(int i=0;i< 3;i++){
		BrowserUtils.clickWithTimeOut(By.xpath("//span[@name = '"+AllStudentsArray.get(studentIndexNo)+"']"),2);
		Thread.sleep(200);
		}
		}

		//------------------------------------------------------------------------------

		ScreenReaderContent:
		for(int i=0;i< 3;i++){
		try{
		BrowserUtils.scrollToElement(driver.findElement(By.className("ScreenReaderContent")));
		break ScreenReaderContent;
		}catch(Exception e){
		Thread.sleep(1000);
		}
		}

		for(int t=1;t< 5;t++){
		try{
		driver.switchTo().defaultContent();
		break;
		}catch(Exception e){
		Thread.sleep(1000);
		e.printStackTrace();
		}

		}

		BrowserUtils.scrollToElement(driver.findElement(By.id("breadcrumbs")));


		String recordingName=driver.findElement(By.cssSelector("h1.page-title")).getText();
		recordingName=recordingName.replace(":","");
		recordingName=recordingName.replace("|","");
		recordingName=recordingName.replace("~","");
		recordingName=recordingName.replace("!","");
		recordingName=recordingName.replace("%","");
		recordingName=recordingName.replace("RECORDING","");
		Thread.sleep(2500);


		//-------------------------------------------------------


		TakesScreenshot ts=(TakesScreenshot)driver;
		File screenshot=ts.getScreenshotAs(OutputType.FILE);
		File pngFolder=new File(studentFolderString+"\\"+AllStudentsArray.get(studentIndexNo)+" "+ ++recordingCount+" - "+recordingName+".png");
		FileUtils.copyFile(screenshot,pngFolder);

		}
		}
		}catch(Exception e){
		e.printStackTrace();
		}

		}

		}

		}

		}


 */




