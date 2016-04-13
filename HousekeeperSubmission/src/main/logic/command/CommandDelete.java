package main.logic.command;
import main.logic.Logic;

/**
 * @@author A0124719A
 * Deletes a task based on the user input index.
 */
public class CommandDelete implements Command {
	private int index;

	public CommandDelete(int index) {
		this.index = index;
	}

	@Override
	public String execute() {
		String showMessage = "Task has been Deleted." + Logic.tasks.get(index).toString();
		Logic.tasks.remove(index);
		return showMessage;
	}

}
