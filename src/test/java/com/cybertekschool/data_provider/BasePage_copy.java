package com.cybertekschool.data_provider;

import com.cybertekschool.utilities.BrowserUtils;
import com.cybertekschool.utilities.ConfigurationReader;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BasePage_copy {

	public BasePage_copy() throws IOException {
	}

	//excel sheet recording row no
	static int recordingURLRow = 0;

	//excel sheet recording column no
	static int recordingURLColumn = 3;

	//excel sheet student name row no
	static int studentNameRow = 3;

	//excel sheet student name column no
	static int studentNameColumn = 2;

	//number of students in group1
	int eu7group1 = 14;
	int eu7group2 = 13;
	int eu7group3 = 14;
	int eu6group1 = 12;

	static String username = ConfigurationReader.get("username");
	static String password = ConfigurationReader.get("password");

	String path = "C:\\Users\\yakup\\IdeaProjects\\CanvasDataProvider\\src\\test\\resources\\dersler_copy.xlsx";
	Workbook workbook = WorkbookFactory.create(new File(path));


}
