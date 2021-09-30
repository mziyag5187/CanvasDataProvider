package com.cybertekschool.data_provider;


import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class Quizz_Assignment_Check extends BasePage {

	public Quizz_Assignment_Check() throws IOException {
	}


	@Test
	public void quizAssignmentCheck() throws InterruptedException, IOException {

		String sheetAdded1 = "EU7-Quizzes";
		String sheetAdded2 = "EU7-Assignments";
		String sheetAdded3 = "EU6-Quizzes";
		String sheetAdded4 = "EU6-Assignments";


		ArrayList<String> excelPagesList = new ArrayList<>();
		excelPagesList.add(sheetAdded1);
		excelPagesList.add(sheetAdded2);
		excelPagesList.add(sheetAdded3);
		excelPagesList.add(sheetAdded4);

		ArrayList<String> messages = new ArrayList<>();
		messages.add("Quiz/Assign Name: ");
		messages.add("Number of Quiz/Assign: ");
		messages.add("--No Missing Quiz/Assign");
		messages.add("--Quiz/assign. being checked: ");
		messages.add("--number of total missing quiz/assignment from the student: ");
		messages.add("--Quiz/assign. being checked: null / no URL to check: ");


		commonThings(messages,"quizAssign", excelPagesList, sheetAdded1, sheetAdded2, sheetAdded3, sheetAdded4);

	}
}





