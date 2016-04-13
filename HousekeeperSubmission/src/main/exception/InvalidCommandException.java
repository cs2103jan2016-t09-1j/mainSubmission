package main.exception;
/**
 * 
 * @@author A0114319M
 *
 */

public class InvalidCommandException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidCommandException() {

	}

	public String getMessage() {
		return "Invalid Command. Would you please rephrase it?";
	}
}
