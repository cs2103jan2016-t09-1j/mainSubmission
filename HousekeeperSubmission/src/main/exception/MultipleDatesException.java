package main.exception;

/**
 * 
 * @@author A0114319M
 *
 */

@SuppressWarnings("serial")
public class MultipleDatesException extends RuntimeException{
	
	private int dates;
    
	public MultipleDatesException(int dates) {
		this.dates = dates;
	}
    
	public String getMessage() {
		return dates + " Invalid. There cannot be more than 2 dates.";
	}
}
