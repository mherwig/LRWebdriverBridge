package de.mikeherwig.LRWebdriverBridge;

public class NoDriverSetException extends Exception {

	private static final long serialVersionUID = 7022723231513383811L;

	public NoDriverSetException() {}

	public NoDriverSetException(String message) {
		super(message);
	}
}
