package de.mikeherwig.LRWebdriverBridge;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class LRWebdriverBridge {

	private ActionRunner runner;
	
	private ActionListenerHub actionListenerHub;
	
	private String path;
	
	private Map<String, Object> parameters;
	
	private LRWebdriverBridge() {}

	private static class InstanceHolder {
		private static final LRWebdriverBridge INSTANCE = new LRWebdriverBridge();
	}

	public static LRWebdriverBridge getInstance() {
		return InstanceHolder.INSTANCE;
	}
	
	private ActionListenerHub getActionListenerHub() {
		if (actionListenerHub == null) {
			actionListenerHub = new ActionListenerHub();
		}
		
		return actionListenerHub;
	}

	public void addActionListener(ActionListener listener) {
		getActionListenerHub().getActionListeners().add(listener);
	}
	
	public void removeActionListener(ActionListener listener) {
		getActionListenerHub().getActionListeners().remove(listener);
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public Map<String, Object> getParameters() {
		if (parameters == null) {
			parameters = new HashMap<String, Object>();
		}
		
		return parameters;
	}
	
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}

	public boolean execute(String actionName, RunnerMode mode) {		
		if (mode == RunnerMode.WEBDRIVER_API) {
			runner = new WebdriverRunner();
		} else if (mode == RunnerMode.NATIVE) {
			runner = new NativeRunner();
		}
		
		runner.setParameters(getParameters());
		runner.setActionListenerHub(getActionListenerHub());

		try {
			runner.loadAction(actionName, path);
			runner.runAction();
		} catch (FileNotFoundException e) {
			getActionListenerHub().onMessageReceive("ERROR: Action \"" + actionName + "\" could not be found!", e.getMessage());
			
			return false;
		} catch (CouldNotLoadActionException e) {
			getActionListenerHub().onMessageReceive("ERROR: Failed at loading the action \"" + actionName + "\"!", e.getMessage());
			
			return false;
		}
		
		return true;
	}
}
