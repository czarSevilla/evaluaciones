package czar.evaluaciones.integration;

import java.io.File;
import java.io.FileOutputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSeleniumTest {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected String base;
	
	protected WebDriver driver;
	
	protected String getPath() {
		return String.format("target/surefire-reports/results/%s/", this.base);
	}
	
	protected void captureScreenshot(String fileName) {
        try {
            new File(getPath()).mkdirs();
            String filename = String.format("%s/%sITest.png", getPath(), fileName);
            FileOutputStream out = new FileOutputStream(filename);
            out.write(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            out.close();
        } catch (Exception e) {
            logger.error("al capturar screenshot", e);
        }
    }
}
