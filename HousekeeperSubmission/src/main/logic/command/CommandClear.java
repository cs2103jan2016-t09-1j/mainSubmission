package main.logic.command;

import main.logic.Logic;

/**
 * @@author A0124719A
 * To clear all the tasks in display.
 *
 */
public class CommandClear implements Command{

	@Override
	public String execute() {
		Logic.tasks.clear();
		return "All Housekeeper Tasks cleared.";
	}
}
