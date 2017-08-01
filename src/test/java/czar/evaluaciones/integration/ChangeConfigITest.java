package czar.evaluaciones.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChangeConfigITest extends AbstractSeleniumTest {
	
	public ChangeConfigITest() {
		super();
		super.base = "changeConfigITest";
	}
	
	@Before
	public void init() {
		//System.setProperty("webdriver.chrome.driver", "C:/chromedriver_win32/chromedriver.exe");
		super.driver = new ChromeDriver();
	}
	
	@Test
	public void test() {
		
		String url = "http://localhost:8080/evaluaciones";
				
		driver.get(url);
		
		driver.findElement(By.id("userName")).sendKeys("admin");
		driver.findElement(By.id("userPassword")).sendKeys("admin");
		
		captureScreenshot("01_login"); 
		
		driver.findElement(By.cssSelector("input[type=submit]")).click();
		
		driver.get(url + "/admin/configuracion");
		
		captureScreenshot("02_configuration"); 
		
		driver.findElements(By.cssSelector("button[title=editar]")).get(3).click();
		
		captureScreenshot("03_change"); 
		
		driver.findElement(By.cssSelector("input[name=value]")).clear(); 
		driver.findElement(By.cssSelector("input[name=value]")).sendKeys("10");
		driver.findElement(By.cssSelector("button[type=submit]")).click();
		
		captureScreenshot("04_result"); 
		
		WebElement messageResult = driver.findElement(By.id("div-info"));
		
		assertNotNull(messageResult);
		
	}
	
	@After
	public void clean() {
		if (driver != null) {
			driver.close();
		}
	}
}
