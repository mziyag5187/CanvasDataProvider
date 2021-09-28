package com.cybertekschool.data_provider_3;


import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class Quizz_Assignment_Check_EU6_and_EU7_Together_3 extends BasePage_3 {

	public Quizz_Assignment_Check_EU6_and_EU7_Together_3() throws IOException {
	}


	@Test
	public void Canvas() throws InterruptedException, IOException {

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

		commonThings(messages,"quizAssign", excelPagesList, sheetAdded1, sheetAdded2, sheetAdded3, sheetAdded4);

	}
}





