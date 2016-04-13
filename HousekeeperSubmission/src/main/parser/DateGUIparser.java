package main.parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import java.util.List;
import main.logic.Task;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

/**
 * parses data for display to GUI
 * 
 * @@author A0131838A
 */

public class DateGUIparser {

	private static ArrayList<Date> listDate;
	private static String matchInput = null;
	private static Parser dateParser = new Parser();
	/**
	 * get a date form a timing task if it is a deadline task, date is deadline
	 * if it is a duration task, date is start time
	 * 
	 * @@author A0131838A
	 */
	public static Date getDate(Task task) {
		if (task.getStartDate() != null)
			return task.getStartDate();
		if (task.getTaskDeadline() != null)
			return task.getTaskDeadline();
		else

			return null;

	}

	/**
	 * get floating tasks 
	 * @@author A0131838A
	 */
	public static ArrayList<Task> getFloatingTasks(ArrayList<Task> tasks) {
		ArrayList<Task> temp = new ArrayList<>();
		for (Task task : tasks) {
			if (task.getTaskDeadline() == null)
				if(task.getStartDate() == null) {
				temp.add(task);
			}
		}
		return temp;
	}

	/**
	 * get timing task
	 * @@author A0131838A
	 */
	public static ArrayList<Task> getTimeTasks(ArrayList<Task> tasks) {
		ArrayList<Task> temp = new ArrayList<>();
		for (Task t : tasks) {
			if (t.getTaskDeadline() != null || t.getStartDate() != null) {
				temp.add(t);
			}
		}
		Collections.sort(temp, new Comparator<Task>() {
			@Override
			public int compare(Task o1, Task o2) {
				return DateGUIparser.getDate(o1).compareTo(DateGUIparser.getDate(o2));
			}
		});
		return temp;
	}

	/**
	 * Get a form date from input
	 * format: "EEE    MMM  dd  yyyy"
	 * @@author A0131838A
	 */
	public static String Date(Date date) {
		DateFormat df = new SimpleDateFormat("EEE    MMM  dd  yyyy");
		return df.format(date);
	}

	/**
	 * Get a form time from input
	 * format:"K:mma"
	 *  @@author A0131838A
	 */
	public static String Time(Date date) {
		DateFormat dateF = new SimpleDateFormat("K:mma");
		return dateF.format(date);
	}

	/**
	 * 
	 * change from check command to delete command
	 * 
	 * @@author A0131838A
	 */
	public static String checkToDelete(String input) {
		String[] str = input.split(" ");
		return "delete " + str[1];
	}



	/**
	 * extract a list of date from string
	 * 
	 * @@author A0131838A
	 *
	 */
	public static ArrayList<Date> getListDate(String content) {
		listDate = new ArrayList<>();
		List<DateGroup> groups = dateParser.parse(content);
		for (DateGroup g : groups) {
			listDate.addAll(g.getDates());
		}
		return listDate;
	}

	/**
	 * get the string 
	 * 
	 * @@author A0131838A
	 * 
	 */
	public static String getMatchInput(String content) {
		listDate = new ArrayList<>();
		List<DateGroup> groups = dateParser.parse(content);

		for (DateGroup g : groups) {
			matchInput = g.getText();
		}
		return matchInput;
	}
}
