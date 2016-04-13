package main.logic.command;

import main.logic.Logic;

/**
 * @@author A0124719A
 * Command to revert to the previous state. (Before current command)
 * 
 */

public class CommandUndo implements Command {

	@Override
	public String execute() {

		if (Logic.taskHistory.size() == 0) {
			assert Logic.taskHistory.size() != 0 : "Unable to undo!";
			return "Unable to undo! No previous command";
		} else {
			Logic.tasks = Logic.taskHistory.pop();
			return "Undo Successful! Returned to previous command";
		}
	}
}
