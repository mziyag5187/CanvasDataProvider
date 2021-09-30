package com.cybertekschool;
import com.cybertekschool.utilities.Driver;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Set;

public class TestClass {

	@Test
	public void test1() throws InterruptedException {

		WebDriver driver = Driver.get();

		driver.get("http://practice.cybertekschool.com/windows");

		String currentHandle = driver.getWindowHandle();

		Set<String> windowHandles = driver.getWindowHandles();

		for (String handle : windowHandles) {
			driver.switchTo().window(handle);

			if (driver.getTitle().equals("expectedTitle")) {
				break;
			}
		}


		//=====================================================


	}

}
