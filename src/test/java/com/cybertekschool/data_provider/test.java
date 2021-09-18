package com.cybertekschool.data_provider;

import com.cybertekschool.utilities.Driver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class test {
	public static void main(String[] args) throws IOException, InterruptedException {

		List<String> students = new ArrayList<>();
		students.add("Ali");
		students.add("Veli");
		students.add("Osman");
		students.add("Tuncay");



		//=========================================================

		WebDriver driver = Driver.get();
		int count = 0;


		for (int i = 0; i < students.size(); i++) {

			driver.get("https://www.timeanddate.com/worldclock/turkey/istanbul");


			TakesScreenshot ts = (TakesScreenshot) driver;
			File screenshot_png = ts.getScreenshotAs(OutputType.FILE);

			String studentFolderString = System.getProperty("user.dir") + "\\target\\SCREENSHOTS\\" + ++count + "- " + students.get(i);
			File studentFolderFile = new File(studentFolderString);

			File studentPNG = new File(studentFolderString + "\\"  + students.get(i) +  count + " " + ".png");

			if (studentFolderFile.exists()) {
				FileUtils.deleteDirectory(studentFolderFile);
			}

			FileUtils.copyFile(screenshot_png, studentPNG);


		}

		//==========================================================

//		target = System.getProperty("user.dir") + "\\target\\SCREENSHOTS";
//
//		folder = new File(target + students.get(0));
//		folder.mkdirs();


//		C:\Users\yakup\IdeaProjects\CanvasDataProvider\target\SCREENSHOTSali.png

//		if (!folder.exists()){
//			folder.mkdir();
//		}


		driver.quit();

	}
}
