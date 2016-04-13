package main.logic.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.logic.Logic;
import main.logic.Task;
import main.parser.DateGUIparser;

/**
 * @@author A0116137M
 * Command to show tasks based on user input of daily, deadline or duration tasks.
 * 
 */

public class CommandShow implements Command {

	String description;
	ArrayList<Task> showTasks = new ArrayList<Task>();

	public CommandShow(String description) {
		this.description = description;
	}

	@Override
	public String execute() {
		Logger logger = Logger.getLogger("CommandShow");

		showTasks.clear();

		if (description.equals("daily")) {
			for (int i = 0; i < Logic.tasks.size(); i++) {
				if (Logic.tasks.get(i).getTaskDeadline() == null && Logic.tasks.get(i).getStartDate() == null
						&& Logic.tasks.get(i).getEndDate() == null) {
					logger.log(Level.INFO, "adding floating task");
					showTasks.add(Logic.tasks.get(i));
				}
			}
		} else if (description.equals("deadline")) {
			for (int i = 0; i < Logic.tasks.size(); i++) {
				if (Logic.tasks.get(i).getTaskDeadline() != null) {
					logger.log(Level.INFO, "adding deadline task");
					showTasks.add(Logic.tasks.get(i));
				}
			}
		} else if (description.equals("duration")) {
			for (int i = 0; i < Logic.tasks.size(); i++) {
				if (Logic.tasks.get(i).getStartDate() != null && Logic.tasks.get(i).getEndDate() != null) {
					logger.log(Level.INFO, "adding duration task");
					showTasks.add(Logic.tasks.get(i));
				}
			}
		} else if (description.equals("completed")) {
			for (int i = 0; i < Logic.tasks.size(); i++) {
				if (Logic.tasks.get(i).getFlag() == true) {
					logger.log(Level.INFO, "adding completed task");
					showTasks.add(Logic.tasks.get(i));
				}
			}
		} else if (description.equals("uncompleted")) {
			for (int i = 0; i < Logic.tasks.size(); i++) {
				if (Logic.tasks.get(i).getFlag() == false) {
					logger.log(Level.INFO, "adding uncompleted task");
					showTasks.add(Logic.tasks.get(i));
				}
			}
		} else if (description.equals("today")) {
			Date today = new Date();
			for (int i = 0; i < Logic.tasks.size(); i++) {

				if (DateGUIparser.getDate(Logic.tasks.get(i)) != null
						&& DateGUIparser.Date(DateGUIparser.getDate(Logic.tasks.get(i)))
								.equals(DateGUIparser.Date(today))) {
					showTasks.add(Logic.tasks.get(i));
				}
			}
		} else if (description.equals("tomorrow")) {
			Date today = new Date();
			Date tomorrow = new Date(today.getTime() + (1000 * 60 * 60 * 24));
			for (int i = 0; i < Logic.tasks.size(); i++) {
				if (DateGUIparser.getDate(Logic.tasks.get(i)) != null
						&& DateGUIparser.Date(DateGUIparser.getDate(Logic.tasks.get(i)))
								.equals(DateGUIparser.Date(tomorrow))) {
					showTasks.add(Logic.tasks.get(i));
				}
			}
		}

		logger.log(Level.INFO, "end of show list");

		if (showTasks.isEmpty()) {
			Logic.setShowTasks(null);
			return "No tasks to show";
		} else {
			Logic.setShowTasks(showTasks);
			assert showTasks.size() != 0 : "Show List is empty";
			return "Tasks shown";
		}
	}

}
