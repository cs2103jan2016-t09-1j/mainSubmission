package main.logic.command;

import java.util.Date;

import main.logic.Logic;
import main.logic.Task;


/**
 * @@author A0124719A
 * Edits a task based on the user input. Can edit description, start date and end date.
 */
public class CommandEdit implements Command {

	private int index;
	private String editDescription;
	private Date editDeadline;
	private Date editStartDate;
	private Date editEndDate;
	private boolean clearDeadline;
	private boolean clearDuration;

	public CommandEdit(int index, String editDescription, Date editDeadline, Date editStartDate, Date editEndDate, boolean clearDeadline, boolean clearDuration) {
		this.index = index;
		this.editDescription = editDescription;
		this.editDeadline = editDeadline;
		this.editStartDate = editStartDate;
		this.editEndDate = editEndDate;
		this.clearDeadline = clearDeadline;
		this.clearDuration = clearDuration;
	}

	@Override
	public String execute() {
		Task task = Logic.tasks.get(index);
		String showMessage = "";
		if (editStartDate != null) {
			showMessage += "Task start date changed from: '" + task.getStartDate() + "\' to \'" + editStartDate + "'";
			task.setStartDate(editStartDate);
			clearDeadline = true;
		}
		if (editEndDate != null) {
			showMessage += "Task end date changed from: '" + task.getEndDate() + "\' to \'" + editEndDate + "'";
			task.setEndDate(editEndDate);
			clearDeadline = true;
		}
		if (editDeadline != null) {
			showMessage += "Task deadline changed from: '" + task.getTaskDeadline() + "\' to \'" + editDeadline + "'";
			task.setTaskDeadline(editDeadline);
			clearDuration = true; //tasks have either a deadline or duration
		}
		if (editDescription != null) {
			showMessage += "Task description changed from: '" + task.getTaskDescription() + "\' to \'" + editDescription + "'\n";
			task.setTaskDescription(editDescription);
		}
		if (clearDeadline) {
			task.setTaskDeadline(null);
		}
		if (clearDuration) {
			task.setStartDate(null);
			task.setEndDate(null);
		}
		return showMessage;
	}

}
