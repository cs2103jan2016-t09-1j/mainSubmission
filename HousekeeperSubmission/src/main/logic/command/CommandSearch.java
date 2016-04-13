package main.logic.command;

import main.logic.Logic;

import main.logic.Task;
import main.parser.DateGUIparser;

import java.util.*;

/**
 * @@author A0124719A
 * Command to search tasks based on user input. 
 * Unable to search if there are multiple keywords from different locations of the description.
 * 
 */
public class CommandSearch implements Command {

	private ArrayList<String> taskDescription;
	private ArrayList<Date> specifyDate;
	private ArrayList<Task> searchedTasks = new ArrayList<Task>();

	public CommandSearch(ArrayList<String> taskDescription) {
		this.taskDescription = taskDescription;
	}

	@Override
	public String execute() {
		searchedTasks.clear();
		String listString = "";
		Calendar searchDate = Calendar.getInstance();
		Calendar taskDate = Calendar.getInstance();

		if (taskDescription.get(0).equals("date")) {
			for (int i = 1; i < taskDescription.size(); i++) {
				listString += taskDescription.get(i) + " ";
			}

			listString = listString.trim();

			for (int i = 0; i < Logic.tasks.size(); i++) {
				specifyDate = DateGUIparser.getListDate(listString);
				searchDate.setTime(specifyDate.get(0));

				if (!(Logic.tasks.get(i).getTaskDeadline() == null)) {
					taskDate.setTime(Logic.tasks.get(i).getTaskDeadline());

					if (searchDate.get(Calendar.DAY_OF_MONTH) == taskDate.get(Calendar.DAY_OF_MONTH)) {
						if (searchDate.get(Calendar.MONTH) == taskDate.get(Calendar.MONTH)) {
							searchedTasks.add(Logic.tasks.get(i));
						}
					}
				} else if (!(Logic.tasks.get(i).getStartDate() == null)) {
					taskDate.setTime(Logic.tasks.get(i).getStartDate());
					if (searchDate.get(Calendar.DAY_OF_MONTH) == taskDate.get(Calendar.DAY_OF_MONTH)) {
						if (searchDate.get(Calendar.MONTH) == taskDate.get(Calendar.MONTH)) {
							searchedTasks.add(Logic.tasks.get(i));
						}
					}
					if (!(Logic.tasks.get(i).getStartDate().equals(Logic.tasks.get(i).getEndDate()))) {
						taskDate.setTime(Logic.tasks.get(i).getEndDate());
						if (searchDate.get(Calendar.DAY_OF_MONTH) == taskDate.get(Calendar.DAY_OF_MONTH)) {
							if (searchDate.get(Calendar.MONTH) == taskDate.get(Calendar.MONTH)) {
								searchedTasks.add(Logic.tasks.get(i));
							}
						}
					}
				}
			}

		} else {
			for (String s : taskDescription) {
				if (taskDescription.size() == 1) {
					listString = s;
				} else {
					listString += s + " ";
				}
			}

			listString = listString.trim();
			for (int i = 0; i < Logic.tasks.size(); i++) {
				if (Logic.tasks.get(i).getTaskDescription().matches("(.*)" + listString + "(.*)")) {
					searchedTasks.add(Logic.tasks.get(i));
				}
			}
		}

		if (searchedTasks.isEmpty()) {
			Logic.setTaskSearch(null);
			return "No such task";
		} else {
			Logic.setTaskSearch(searchedTasks);
			assert searchedTasks.size() != 0 : "Search List is empty";
			return "Task found";
		}
	}
}
