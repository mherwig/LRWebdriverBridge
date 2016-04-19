package de.mikeherwig.LRWebdriverBridgeActions;

import static org.junit.Assert.*;

import org.junit.Test;

import actions.SimpleExampleAction;
import de.mikeherwig.LRWebdriverBridge.ActionListener;
import de.mikeherwig.LRWebdriverBridge.LRWebdriverBridge;
import de.mikeherwig.LRWebdriverBridge.RunnerMode;

public class WebdriverRunnerTest {

	@Test
	public void executeAction() {
		LRWebdriverBridge bridge = LRWebdriverBridge.getInstance();

		assertNotNull(bridge);

		bridge.addActionListener(new ActionListener() {

			@Override
			public void onMessageReceive(String message, String exceptionMessage) {
				System.out.println(message);
			}

			@Override
			public void onBeforeRun() {}

			@Override
			public void onAfterRun(long responseTime, boolean isSuccess, String actionName) {
				assertTrue(responseTime > 0);
				String[] arr = SimpleExampleAction.class.getName().split("\\.");
				assertEquals(actionName, arr[arr.length - 1]);
				System.out.println("Finished action in " + responseTime + " ms.");
			}
		});

		boolean isSuccess = bridge.execute(SimpleExampleAction.class.getName(), RunnerMode.WEBDRIVER_API);
		assertTrue(isSuccess);
	}


}
