package com.cybertekschool.data_provider;


		import com.cybertekschool.utilities.BrowserUtils;
		import com.cybertekschool.utilities.ConfigurationReader;
		import com.cybertekschool.utilities.Driver;
		import org.junit.Test;

		import org.apache.poi.ss.usermodel.Cell;
		import org.apache.poi.ss.usermodel.Sheet;
		import org.apache.poi.ss.usermodel.Workbook;
		import org.apache.poi.ss.usermodel.WorkbookFactory;
		import org.openqa.selenium.*;


		import java.io.File;
		import java.io.IOException;
		import java.util.ArrayList;

public class CanvasDataProvider {

	WebElement insightTab;


	@Test
	public void Canvas() throws InterruptedException, IOException {

		//====================================================================================================

		String path = "C:\\Users\\yakup\\IdeaProjects\\CanvasDataProvider\\src\\test\\resources\\dersler.xlsx";

		Workbook workbook = WorkbookFactory.create(new File(path));

		Sheet lessonList = workbook.getSheet(ConfigurationReader.get("dersler"));
		int lessonsLastRow = lessonList.getLastRowNum();

		Sheet studentList = workbook.getSheet(ConfigurationReader.get("groups"));
		int studentsLastRow = studentList.getLastRowNum();

		ArrayList<Cell> LessonsArray = new ArrayList<>();
		ArrayList<Cell> StudentsArray = new ArrayList<>();

		//========================================================================

		String mesaj = lessonList.getRow(0).getCell(0).getStringCellValue();
		System.out.println(mesaj);

		for (int i = 1; i <= lessonsLastRow; i++) {
			LessonsArray.add(lessonList.getRow(i).getCell(0));
			System.out.println(LessonsArray.get(i - 1).getStringCellValue());
		}

//		for (Cell cell : LessonsArray) {
//			System.out.println(cell.getStringCellValue());
//		}

		for (int i = 1; i <= studentsLastRow; i++) {
			StudentsArray.add(studentList.getRow(i).getCell(0));
		}

		//========================================================================

		String mail = ConfigurationReader.get("username");
		String password = ConfigurationReader.get("password");

		//========================================================================

		WebDriver driver = Driver.get();
		driver.manage().window().maximize();
		driver.get(ConfigurationReader.get("url"));
		driver.findElement(By.xpath("//*[@id=\"okta-signin-username\"]")).sendKeys(mail);
		driver.findElement(By.xpath("//*[@id=\"okta-signin-password\"]")).sendKeys(password);
		driver.findElement(By.xpath("//*[@id=\"okta-signin-submit\"]")).click();
//		Thread.sleep(2000);

//		BrowserUtils.waitForClickablility(By.xpath("//*[@id=\"form8\"]/div[2]/input"),20000);
//		driver.findElement(By.xpath("//*[@id=\"form8\"]/div[2]/input")).click();

		BrowserUtils.clickWithWait(By.xpath("//*[@id=\"form8\"]/div[2]/input"),5);

		Thread.sleep(15000);

		//************************************************
		//** OPEN YOUR MOBILE PHONE AND APPROVE OKTA LOGIN
		//************************************************

		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(1));
		driver.close();
		driver.switchTo().window(tabs2.get(0));
		driver.get("https://learn.cybertekschool.com/courses/540");
		for (Cell lesson : LessonsArray) {
			driver.get(lesson.getStringCellValue());
			driver.switchTo().defaultContent();

			BrowserUtils.waitForPageToLoad(5000);
			driver.switchTo().frame(1);


//			for (int i = 1; i <= 5; i++) {
//				try {
//					driver.switchTo().frame(1);
//					return;
//				} catch (Exception e) {
//					BrowserUtils.waitFor(1);
//				}
//			}



//current	Thread.sleep(10000);
//			WebElement insightTab = driver.findElement(By.xpath("//*[@id=\"tab-insights\"]"));
			BrowserUtils.waitForClickablility(By.xpath("//*[@id=\"tab-insights\"]"), 5);
			BrowserUtils.clickWithWait(By.xpath("//*[@id=\"tab-insights\"]"), 5);
//			BrowserUtils.clickWithTimeOut(By.xpath("//*[@id=\"tab-insights\"]"), 5);
//			insightTab.click();
			Thread.sleep(3000);

			for (Cell student : StudentsArray) {
				driver.findElement(By.xpath("//*[.=\"" + student.getStringCellValue() + "\"]")).click();
				Thread.sleep(1000);
				WebElement insightTab = driver.findElement(By.xpath("//*[@id=\"tab-insights\"]"));
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", insightTab);


				Thread.sleep(3000);
			}
		}
	}

}

