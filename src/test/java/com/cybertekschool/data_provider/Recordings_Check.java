package com.cybertekschool.data_provider;


import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;


public class Recordings_Check extends BasePage {


	public Recordings_Check() throws IOException {
	}


	@Test
	public void checkRecordings() throws InterruptedException, IOException {

		String sheetAdded1 = "EU7-Recordings";
		String sheetAdded2 = "EU6-Recordings";
		String sheetAdded3 = null;
		String sheetAdded4 = null;

		ArrayList<String> excelPagesList = new ArrayList<>();
		excelPagesList.add(sheetAdded1);
		excelPagesList.add(sheetAdded2);
		excelPagesList.add(sheetAdded3);
		excelPagesList.add(sheetAdded4);

		ArrayList<String> messages = new ArrayList<>();
		messages.add("Recording Name: ");
		messages.add("Number of Total Recordings: ");
		messages.add("--No Missing Recording");
		messages.add("--Recording being checked: ");
		messages.add("--number of total missing recordings from the student: ");
		messages.add("--Recording being checked: null / no insight in the URL to check: ");

		commonThings(messages,"recordings", excelPagesList, sheetAdded1, sheetAdded2, sheetAdded3, sheetAdded4);

	}
}





