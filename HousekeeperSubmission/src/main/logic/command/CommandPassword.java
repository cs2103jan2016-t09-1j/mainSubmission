package main.logic.command;

/**
 * Experimental class.
 * @@author A0124719A
 *
 */
public class CommandPassword implements Command{
	@Override
	public String execute() {
		
		String message = "Password keyed in Successfully<br>";
		
		return message;
	}
}
