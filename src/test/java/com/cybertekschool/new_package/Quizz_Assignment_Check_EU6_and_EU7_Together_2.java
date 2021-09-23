package com.cybertekschool.new_package;


import com.cybertekschool.data_provider.BasePage;
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

public class Quizz_Assignment_Check_EU6_and_EU7_Together_2 extends BasePage_2 {

	public Quizz_Assignment_Check_EU6_and_EU7_Together_2() throws IOException {
	}


	@Test
	public void Canvas() throws InterruptedException, IOException {

		String sheetAdded1 = "EU7-Quizzes";
		String sheetAdded2 = "EU6-Quizzes";
		String sheetAdded3 = "EU7-Assignments";
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

		commonThings(messages,"quizAssign", excelPagesList, sheetAdded1, sheetAdded2);

	}
}





