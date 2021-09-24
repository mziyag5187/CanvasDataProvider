package com.cybertekschool.data_provider;


import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Zoom_Att_EU6_and_EU7_Together_2 extends BasePage_2 {

	String zoomSheetEU7 = "Zoom-EU7";
	String zoomSheetEU6 = "Zoom-EU6";

	String mailSheetEU7 = "Zoom-EU7-EmailList";
	String mailSheetEU6 = "Zoom-EU6-EmailList";

	public Zoom_Att_EU6_and_EU7_Together_2() throws IOException {
	}


	@Test
	public void Canvas() throws InterruptedException, IOException {


		ArrayList<String> allGroupsZoomSheet = new ArrayList<>();
		allGroupsZoomSheet.add(zoomSheetEU7);
		allGroupsZoomSheet.add(zoomSheetEU6);


		//==========================================================


		//going through each zoom sheet (eu7 and eu6)
		for (String eachGroupZoomSheet : allGroupsZoomSheet) {

			//--- Create Sheet Object based on Batch---------------
			Sheet mailsSheet = null;
			Sheet zoomSheet = null;

			if (eachGroupZoomSheet.equals(zoomSheetEU7)) {
				mailsSheet = workbook.getSheet(mailSheetEU7);
				zoomSheet = workbook.getSheet(zoomSheetEU7);
			} else if (eachGroupZoomSheet.equals(zoomSheetEU6)) {
				mailsSheet = workbook.getSheet(mailSheetEU6);
				zoomSheet = workbook.getSheet(zoomSheetEU6);
			}

			//--- Get all student name and mails from excel mails sheet ---------------
			ArrayList<String> studentNames = new ArrayList<>();
			ArrayList<String> studentEmails = new ArrayList<>();

			int lastRowNumInSheet = mailsSheet.getLastRowNum();
			for (int i = studentNameRowStartsFrom; i <= lastRowNumInSheet; i++) {
				if (mailsSheet.getRow(i).getCell(studentNameColumnStartsFrom) != null) {
					String studentName = mailsSheet.getRow(i).getCell(studentNameColumnStartsFrom).getStringCellValue();
					studentNames.add(studentName);

					String studentMail = mailsSheet.getRow(i).getCell(studentNameColumnStartsFrom + 1).getStringCellValue();
					studentEmails.add(studentMail);
				}
			}
			System.out.println();
			System.out.println("Number of total students: " + studentNames.size());
			System.out.println();


			//--- Get all student mails and duration from excel zoom sheet ---------------
			ArrayList<String> zoomAttEmails = new ArrayList<>();
			ArrayList<Double> zoomAttDurations = new ArrayList<>();

			int lastRowNumInSheet2 = zoomSheet.getLastRowNum();
			for (int i = 1; i <= lastRowNumInSheet2; i++) {
				if (zoomSheet.getRow(i).getCell(1) != null) {
					String zoomMail = zoomSheet.getRow(i).getCell(1).getStringCellValue();
					zoomAttEmails.add(zoomMail);

					double zoomDuration = zoomSheet.getRow(i).getCell(2).getNumericCellValue();
					zoomAttDurations.add(zoomDuration);
				}
			}
			System.out.println();


			for (int i = 0; i < studentNames.size(); i++) {
				for (int j = 0; j < zoomAttEmails.size(); j++) {

					if (studentEmails.get(i).equals(zoomAttEmails.get(j))) {
//						System.out.println(studentNames.get(i) + ": " + zoomAttDurations.get(j));

						if (eachGroupZoomSheet.equals(zoomSheetEU7)) {
							if (i < eu7group1) {
								System.out.println("EU7 / Group-11: " + studentNames.get(i).toUpperCase() + " --> " + zoomAttDurations.get(j));
								if (i == eu7group1-2) {
								}
							} else if (i >= eu7group1 && i < eu7group1 + eu7group2) {
								System.out.println("EU7 / Group-12: " + studentNames.get(i).toUpperCase() + " --> " + zoomAttDurations.get(j));
								if (i == eu7group1 + eu7group2 - 1) {
								}
							} else {
								System.out.println("EU7 / Group-23: " + studentNames.get(i).toUpperCase() + " --> " + zoomAttDurations.get(j));
							}

						} else if (eachGroupZoomSheet.equals(zoomSheetEU6)) {
							System.out.println("EU6 / Group-12: " + studentNames.get(i).toUpperCase() + " --> " + zoomAttDurations.get(j));
						}
						System.out.println();
					}

				}
			}

		}


//
//
//			//===GET STUDENT NAME FROM GROUPS AND COMPARE IT TO ZOOM LIST======================
//
//			for (int studentIndexNo = 0; studentIndexNo < ZoomAttendanceMap.size(); studentIndexNo++) {
//
//				if (eachGroupZoomSheet.equals(zoomSheetEU7)) {
//
//					if (studentIndexNo < eu7group1) {           //name                                                      //duration
//						System.out.println("EU7 / Group-11: " + NameAndMailsMap.keySet().toArray()[studentIndexNo] + ": " + ZoomAttendanceMap.get(NameAndMailsMap.keySet().toArray()[studentIndexNo]));
//						if (studentIndexNo == eu7group1-1) {
//							System.out.println();
//						}
//					} else if (studentIndexNo >= eu7group1 && studentIndexNo < eu7group1 + eu7group2) {
//						System.out.println("EU7 / Group-12: " + ZoomAttendanceMap.get(studentIndexNo) + ": " + ZoomAttendanceMap.get(ZoomAttendanceMap.get(studentIndexNo)));
//						if (studentIndexNo == eu7group1 + eu7group2 -1) {
//							System.out.println();
//						}
//					} else {
//						System.out.println("EU7 / Group-23: " + ZoomAttendanceMap.get(studentIndexNo) + ": " + ZoomAttendanceMap.get(ZoomAttendanceMap.get(studentIndexNo)));
//
//					}
//				} else if (eachGroupZoomSheet.equals(zoomSheetEU6)) {
//					System.out.println("EU6 / Group-12: " + ZoomAttendanceMap.get(studentIndexNo) + ": " + ZoomAttendanceMap.get(ZoomAttendanceMap.get(studentIndexNo)));
//				}
//				System.out.println();


//				if (eachZoomSheet.equals("Zoom-EU7")) {
//					if (studentIndexNo < 14) {
//						System.out.println("EU7 / Group-11: " + ZoomAttendanceMap.get(studentIndexNo) + ": " + ZoomAttendanceMap.get(ZoomAttendanceMap.get(studentIndexNo)));
//						if (studentIndexNo == 13) {
//							System.out.println();
//						}
//					} else if (studentIndexNo >= 14 && studentIndexNo < 27) {
//						System.out.println("EU7 / Group-12: " + ZoomAttendanceMap.get(studentIndexNo) + ": " + ZoomAttendanceMap.get(ZoomAttendanceMap.get(studentIndexNo)));
//						if (studentIndexNo == 26) {
//							System.out.println();
//						}
//					} else {
//						System.out.println("EU7 / Group-23: " + ZoomAttendanceMap.get(studentIndexNo) + ": " + ZoomAttendanceMap.get(ZoomAttendanceMap.get(studentIndexNo)));
//					}
//
//				} else if (eachZoomSheet.equals("Zoom-EU6")) {
//					System.out.println("EU6 / Group-12: " + ZoomAttendanceMap.get(studentIndexNo) + ": " + ZoomAttendanceMap.get(ZoomAttendanceMap.get(studentIndexNo)));
//				}
//				System.out.println();
//			}


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




