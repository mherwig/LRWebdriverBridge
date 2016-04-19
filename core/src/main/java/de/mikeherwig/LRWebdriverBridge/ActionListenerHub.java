package de.mikeherwig.LRWebdriverBridge;

import java.util.ArrayList;
import java.util.List;

public class ActionListenerHub implements ActionListener {
	
	private List<ActionListener> actionListeners;
	
	public void setActionListeners(List<ActionListener> actionListeners) {
		this.actionListeners = actionListeners;
	}

	public List<ActionListener> getActionListeners() {
		if (actionListeners == null) {
			actionListeners = new ArrayList<ActionListener>();
		}

		return actionListeners;
	}
	
	@Override
	public void onBeforeRun() {		
		for (ActionListener listener : getActionListeners()) {
			listener.onBeforeRun();
		}
	}

	@Override
	public void onAfterRun(long responseTime, boolean isSuccess, String actionName) {
		for (ActionListener listener : getActionListeners()) {
			listener.onAfterRun(responseTime, isSuccess, actionName);
		}
	}

	@Override
	public void onMessageReceive(String message, String exceptionMessage) {
		for (ActionListener listener : getActionListeners()) {
			listener.onMessageReceive(message, exceptionMessage);
		}
	}
}
