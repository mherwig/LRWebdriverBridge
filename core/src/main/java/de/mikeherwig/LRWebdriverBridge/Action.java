package de.mikeherwig.LRWebdriverBridge;

import java.util.Map;

import org.openqa.selenium.WebDriver;

public interface Action {
		
	ActionResult run(WebDriver driver, Map<String, Object> parameters);
}
