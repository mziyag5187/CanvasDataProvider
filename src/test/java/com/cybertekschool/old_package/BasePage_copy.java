package com.cybertekschool.old_package;

import com.cybertekschool.utilities.ConfigurationReader;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;

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

	String path = "C:\\Users\\yakup\\IdeaProjects\\CanvasDataProvider\\src\\test\\resources\\dersler.xlsx";
	Workbook workbook = WorkbookFactory.create(new File(path));


}
