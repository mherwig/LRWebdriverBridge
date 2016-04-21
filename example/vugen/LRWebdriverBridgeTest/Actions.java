/*
 * LoadRunner Java script. (Build: _build_number_)
 * 
 * Script Description: 
 *                     
 */

import lrapi.lr;

import java.util.Map;

import de.mikeherwig.LRWebdriverBridge.LRWebdriverBridge;
import de.mikeherwig.LRWebdriverBridge.RunnerMode;
import de.mikeherwig.LRWebdriverBridge.ActionListener;

public class Actions
{
	private LRWebdriverBridge bridge;
	private ActionListener actionListener;

	public int init() throws Throwable {
		actionListener = new ActionListener() {
			@Override
			public void onMessageReceive(String message, String exceptionMessage) {}

			@Override
			public void onBeforeRun() {}

			@Override
			public void onAfterRun(long responseTime, boolean isSuccess, String actionName) {
				String status = isSuccess ? "OK" : "FAILURE";
				System.out.println("Finished action in " + responseTime + " ms with Status '" + status + "'.");
				lr.set_transaction("SimpleExampleAction", responseTime, isSuccess ? lr.PASS : lr.FAIL);
			}
		};
		
		bridge = LRWebdriverBridge.getInstance();
		bridge.addActionListener(actionListener);
		
		initParameters();
		
		return 0;
	}//end of init
	
	public void initParameters() {
		Map map = bridge.getParameters();
		map.put("VUserID", Integer.valueOf(lr.get_vuser_id())); // pass VUserID as parameter
		map.put("TT", 1); // pass thinktime
	}

	public int action() throws Throwable {
		bridge.execute("actions.SimpleExampleAction", RunnerMode.WEBDRIVER_API);
		
		return 0;
	}//end of action


	public int end() throws Throwable {
		bridge.removeActionListener(actionListener);
		actionListener = null;
		bridge = null;
		
		return 0;
	}//end of end
}
