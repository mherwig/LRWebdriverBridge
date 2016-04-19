package de.mikeherwig.LRWebdriverBridge;

import java.io.FileNotFoundException;
import java.util.Map;

public interface ActionRunner {
	
	void setParameters(Map<String, Object> parameters);
	
	void setActionListenerHub(ActionListener listener);
			
	void loadAction(String className, String path) throws FileNotFoundException, CouldNotLoadActionException ;
	
	void runAction();
}
