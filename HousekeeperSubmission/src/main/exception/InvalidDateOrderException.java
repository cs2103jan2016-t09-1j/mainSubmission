package main.exception;

/**
 * 
 * @@author A0114319M
 *
 */

@SuppressWarnings("serial")
public class InvalidDateOrderException extends RuntimeException{
	
	public InvalidDateOrderException() {	
	}

	public String getMessage() {
		return "Sorry, your Start Date cannot be later than End Date.";
	}
}
