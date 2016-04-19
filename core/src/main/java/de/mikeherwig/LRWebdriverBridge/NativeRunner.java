package de.mikeherwig.LRWebdriverBridge;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Map;

import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class NativeRunner implements ActionRunner {
	
	private PhantomJSDriver driver = new PhantomJSDriver();

	private ActionListener actionListener;

	private String actionName;
	
	private Map<String, Object> parameters;
	
	private String script;

	@Override
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	@Override
	public void setActionListenerHub(ActionListener listener) {
		this.actionListener = listener;
	}

	@Override
	public void loadAction(String fileName, String path) throws FileNotFoundException, CouldNotLoadActionException {
		File file = new File(path, fileName);
		if (!file.exists()) {
			throw new CouldNotLoadActionException("File \"" + fileName + "\" doesn't exist.");
		}
		
		try {
			byte[] encoded = Files.readAllBytes(file.toPath());
			script = new String(encoded, StandardCharsets.UTF_8);
		} catch (Exception e) {
			throw new CouldNotLoadActionException(e.getMessage());
		}
	}

	@Override
	public void runAction() {
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				long responseTime = 0;
				boolean isSuccess = false;

				actionListener.onBeforeRun();
				actionListener.onMessageReceive("Starting action \"" + actionName + "\" ...", null);
				
				String resultString = (String) driver.executeScript(script, parameters.values());
				String[] resultStringParts = resultString.split("\\|");
				
				responseTime = Long.parseLong(resultStringParts[0]);
				
				if (resultStringParts.length > 1) {
					isSuccess = Boolean.parseBoolean(resultStringParts[1]);
				}
				
				actionListener.onAfterRun(responseTime, isSuccess, actionName);
				actionListener.onMessageReceive("Action \"" + actionName + "\" finished.", null);
			}
		};
		
		runnable.run();
	}
}
