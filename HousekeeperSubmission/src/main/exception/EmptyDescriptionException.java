package main.exception;
/**
 * 
 * @@author A0114319M
 *
 */
@SuppressWarnings("serial")
public class EmptyDescriptionException extends RuntimeException{
	
	public EmptyDescriptionException() {
	}

	public String getMessage() {
		return "Sorry, Description cannot be empty!";
	}

}
