package actions;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import de.mikeherwig.LRWebdriverBridge.Action;
import de.mikeherwig.LRWebdriverBridge.ActionResult;

public class SimpleExampleAction implements Action  {
		
	@Override
	public ActionResult run(WebDriver driver, Map<String, Object> parameters) {
		boolean isSuccess = false;
		
		final String word = "bird"; // the bird is the word!
		
		if (parameters.containsKey("VUserID")) {
			int vuserId = ((Integer) parameters.get("VUserID")).intValue();
	        System.out.println("SimpleExampleAction Started by VuserID " + vuserId);
		}
		
		long startTime = System.currentTimeMillis(); // measure beginn
		 
        driver.get("http://www.google.com");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys(word);
        element.submit();
                
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().startsWith(word);
            }
        });
        
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;  // measure stop
        
        System.out.println("Page title says: " + driver.getTitle());
        
        if (driver.getTitle().toLowerCase().startsWith(word)) {
        	isSuccess = true;
        }
        
        driver.quit();
        
		return new ActionResult(elapsedTime, isSuccess);
	}
}
