package de.mikeherwig.LRWebdriverBridge;

public interface ActionListener {
	
	public void onBeforeRun();
	
	public void onAfterRun(long responseTime, boolean isSuccess, String actionName);
	
	public void onMessageReceive(String message, String exceptionMessage);
}
