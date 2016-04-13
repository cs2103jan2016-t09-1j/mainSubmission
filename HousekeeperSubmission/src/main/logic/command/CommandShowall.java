package main.logic.command;

/**
 * @@author A0124719A
 * Show all command
 *
 */
public class CommandShowall implements Command{

	@Override
	public String execute() {
		return "All tasks displayed!";
	}
}