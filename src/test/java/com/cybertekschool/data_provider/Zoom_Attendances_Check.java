package com.cybertekschool.data_provider;

import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;


public class Zoom_Attendances_Check extends BasePage {

	String zoomSheetEU7 = "Zoom-EU7";
	String zoomSheetEU6 = "Zoom-EU6";

	String mailSheetEU7 = "Zoom-EU7-EmailList";
	String mailSheetEU6 = "Zoom-EU6-EmailList";

	public Zoom_Attendances_Check() throws IOException {
	}


	@Test
	public void checkZoomAttendance() throws InterruptedException, IOException {


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


			//naming the folders
			for (int i = 0; i < studentNames.size(); i++) {
				for (int j = 0; j < zoomAttEmails.size(); j++) {
					if (studentEmails.get(i).equals(zoomAttEmails.get(j))) {

						if (totalEU7groups == 3 && totalEU6groups == 1) {
							if (eachGroupZoomSheet.equals(zoomSheetEU7)) {
								if (i < groupNum1) {
									System.out.println("EU7 / " + groupName1 + ": " + studentNames.get(i).toUpperCase() + " --> " + zoomAttDurations.get(j));
								} else if (i >= groupNum1 && i < groupNum1 + groupNum2) {
									System.out.println("EU7 / " + groupName2 + ": " + studentNames.get(i).toUpperCase() + " --> " + zoomAttDurations.get(j));
								} else {
									System.out.println("EU7 / " + groupName3 + ": " + studentNames.get(i).toUpperCase() + " --> " + zoomAttDurations.get(j));
								}

							} else if (eachGroupZoomSheet.equals(zoomSheetEU6)) {
								System.out.println("EU6 / " + groupName4 + ": " + studentNames.get(i).toUpperCase() + " --> " + zoomAttDurations.get(j));
							}
							System.out.println();
						}

						else if (totalEU7groups == 2 && totalEU6groups == 2) {
							if (eachGroupZoomSheet.equals(zoomSheetEU7)) {
								if (i < groupNum1) {
									System.out.println("EU7 / " + groupName1 + ": " + studentNames.get(i).toUpperCase() + " --> " + zoomAttDurations.get(j));
								} else if (i >= groupNum1 && i < groupNum1 + groupNum2) {
									System.out.println("EU7 / " + groupName2 + ": " + studentNames.get(i).toUpperCase() + " --> " + zoomAttDurations.get(j));
								}
							}else if (eachGroupZoomSheet.equals(zoomSheetEU6)) {
								if (i < groupNum3) {
									System.out.println("EU6 / " + groupName3 + ": " + studentNames.get(i).toUpperCase() + " --> " + zoomAttDurations.get(j));
								} else if (i >= groupNum3 && i < groupNum3 + groupNum4) {
									System.out.println("EU6 / " + groupName4 + ": " + studentNames.get(i).toUpperCase() + " --> " + zoomAttDurations.get(j));
								}
							}
							System.out.println();
						}
					}


					}

				}
			}

		}

	}







