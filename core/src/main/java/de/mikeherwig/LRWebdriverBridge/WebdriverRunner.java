package de.mikeherwig.LRWebdriverBridge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class WebdriverRunner implements ActionRunner {

	private URLClassLoader classLoader;

	private WebDriver driver = new PhantomJSDriver();

	private ActionListener actionListener;

	private String actionName;

	private Action action;
	
	private Map<String, Object> parameters;
	
	@Override
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	@Override
	public void setActionListenerHub(ActionListener listener) {
		this.actionListener = listener;
	}

	@Override
	public void loadAction(String className, String path) throws FileNotFoundException, CouldNotLoadActionException {
		String[] arr = className.split("\\.");
		this.actionName = arr[arr.length - 1];

		try {
			classLoader = new URLClassLoader(new URL[] { new File(path == null ? "./" : path).toURI().toURL() });
			Class<?> c = classLoader.loadClass(className);
			action = (Action) c.newInstance();
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
				
				ActionResult result = action.run(driver, parameters);
				responseTime = result.getResponseTime();
				isSuccess = result.isSuccess();
				
				actionListener.onAfterRun(responseTime, isSuccess, actionName);
				actionListener.onMessageReceive("Action \"" + actionName + "\" finished.", null);
				
				if (classLoader != null) {
					try {
						classLoader.close();
					} catch (IOException e) {}
				}
			}
		};
		
		runnable.run();
	}
}
