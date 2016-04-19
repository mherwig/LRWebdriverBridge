package de.mikeherwig.LRWebdriverBridge;

public class ActionResult {
	
	private long responseTime = 0;
	
	private boolean isSuccess = false;

	public ActionResult() {
	}
	
	public ActionResult(long responseTime, boolean isSuccess) {
		this.responseTime = responseTime;
		this.isSuccess = isSuccess;
	}
	
	public long getResponseTime() {
		return responseTime;
	}
	
	public void setResponseTime(long responseTime) {
		this.responseTime = responseTime;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
}
