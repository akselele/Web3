package view;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestAddBike {
	
	private WebDriver driver;
	String url = "http://localhost:8080/web_war_exploded/Servlet?command=add";

	@Before
	public void setUp() throws Exception {
//		 Voor Windows (vergeet "\" niet te escapen met een tweede "\")
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\Axel\\Desktop\\TI3\\Web3\\chromedriver.exe");
//		 Voor mac:
//		System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
		driver = new ChromeDriver();
		driver.get(url);
	}

	@Test
	public void negatievePrijs(){
		WebElement itemID = driver.findElement(By.id("itemId"));
		itemID.clear();
		itemID.sendKeys("testidItem");

		WebElement brand = driver.findElement(By.id("brand"));
		brand.clear();
		brand.sendKeys("testbrand");

		WebElement category = driver.findElement(By.id("category"));
		category.clear();
		category.sendKeys("ROAD");

		WebElement description = driver.findElement(By.id("description"));
		description.clear();
		description.sendKeys("testdescription");

		WebElement price = driver.findElement(By.id("price"));
		price.clear();
		price.sendKeys("-500");

		WebElement button = driver.findElement(By.id("submit"));
		button.click();

		assertEquals("Bikes - voeg toe", driver.getTitle());
		ArrayList<WebElement> elements = (ArrayList<WebElement>) driver.findElement(By.tagName("li"));
		assertTrue(bezit(elements, "No valid price"));
	}

	@Test
	public void addWerkt(){
		WebElement itemID = driver.findElement(By.id("itemId"));
		itemID.clear();
		itemID.sendKeys("testidItem");

		WebElement brand = driver.findElement(By.id("brand"));
		brand.clear();
		brand.sendKeys("testbrand");

		WebElement category = driver.findElement(By.id("category"));
		category.clear();
		category.sendKeys("ROAD");

		WebElement description = driver.findElement(By.id("description"));
		description.clear();
		description.sendKeys("testdescription");

		WebElement price = driver.findElement(By.id("price"));
		price.clear();
		price.sendKeys("100");

		WebElement button = driver.findElement(By.id("submit"));
		button.click();

		assertEquals("Overview Bikes", driver.getTitle());
		ArrayList<WebElement> x = (ArrayList<WebElement>) driver.findElement(By.tagName("td"));
		assertTrue(bezit(x, "testbrand"));
	}

	private boolean bezit(ArrayList<WebElement> elements, String text)
	{
		for (int i = 0; i < elements.size(); i++)
		{
			if (elements.get(i).getText().equals(text))return true;
		}
		return false;
	}
	
	@After
	public void clean() {
		driver.quit();
	}


}
