package main.logic.command;

/**
 * @@author A0124719A
 * Command when the user has entered an invalid input.
 * 
 */
public class CommandInvalid implements Command {

	private String invalidString;

	public CommandInvalid(String invalidString) {
		this.invalidString = invalidString;
	}

	@Override
	public String execute() {
		
		String showExceptionMessage = this.invalidString;
		return showExceptionMessage;
		
	}

}
