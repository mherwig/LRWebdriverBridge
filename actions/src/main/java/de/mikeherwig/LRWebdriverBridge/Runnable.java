package de.mikeherwig.LRWebdriverBridge;

import de.mikeherwig.LRWebdriverBridge.ActionListener;
import de.mikeherwig.LRWebdriverBridge.LRWebdriverBridge;
import de.mikeherwig.LRWebdriverBridge.RunnerMode;

public class Runnable {
	
	public static void main(String[] args) {
		LRWebdriverBridge bridge = LRWebdriverBridge.getInstance();
		
		String className;
		if (args.length > 0) {
			className = args[0];
			if (args.length > 1) {
				bridge.setPath(args[1]);
			}
		} else {
			return;
		}
		
		bridge.addActionListener(new ActionListener() {
			@Override
			public void onMessageReceive(String message, String exceptionMessage) {
				System.out.println(message);
			}

			@Override
			public void onBeforeRun() {
			}

			@Override
			public void onAfterRun(long responseTime, boolean isSuccess, String actionName) {
				System.out.println("Finished action in " + responseTime + " ms.");
			}
		});
		
		boolean isSuccess = bridge.execute(className, RunnerMode.WEBDRIVER_API);
		System.out.println("Action was " + (isSuccess ? "" : "NOT") + " successful.");
		
		System.exit(isSuccess ? 0 : 1);
	}
}
