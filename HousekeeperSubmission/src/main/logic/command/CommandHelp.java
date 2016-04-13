package main.logic.command;

/**
 * @@author A0114319M
 * Displays help command. GUI will create a message box to show on a separate window.
 *
 */
public class CommandHelp implements Command {

	@Override
	public String execute() {
		return "Help";
	}
}