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


public class Recordings_EU6_and_EU7_Together_2 extends BasePage_2 {


	public Recordings_EU6_and_EU7_Together_2() throws IOException {
	}


	@Test
	public void Canvas() throws InterruptedException, IOException {

		String sheetAdded1 = "EU7-Recordings";
		String sheetAdded2 = "EU6-Recordings";

		ArrayList<String> excelPagesList = new ArrayList<>();
		excelPagesList.add(sheetAdded1);
		excelPagesList.add(sheetAdded2);

		ArrayList<String> messages = new ArrayList<>();
		messages.add("Recording Name: ");
		messages.add("Number of Total Recordings: ");
		messages.add("--No Missing Recording");
		messages.add("--Recording being checked: ");

		commonThings(messages,"recordings", excelPagesList, sheetAdded1, sheetAdded2);

	}
}





