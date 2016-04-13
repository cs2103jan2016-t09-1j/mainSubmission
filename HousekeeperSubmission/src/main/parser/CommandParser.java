package main.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.exception.InvalidDateOrderException;
import main.exception.MultipleDatesException;
import main.exception.EmptyDescriptionException;
import main.logic.Logic;
import main.logic.Task;
import main.logic.command.CommandAdd;
import main.logic.command.CommandCheck;
import main.logic.command.CommandClear;
import main.logic.command.Command;
import main.logic.command.CommandDelete;
import main.logic.command.CommandShowall;
import main.logic.command.CommandEdit;
import main.logic.command.CommandExit;
import main.logic.command.CommandHelp;
import main.logic.command.CommandInvalid;
import main.logic.command.CommandOpen;
import main.logic.command.CommandRedo;
import main.logic.command.CommandSave;
import main.logic.command.CommandSearch;
import main.logic.command.CommandShow;
import main.logic.command.CommandSort;
import main.logic.command.CommandUndo;

/**
 * Parser input of a Command.
 *
 * @@author A0131838A
 */

public class CommandParser {

	private static final String[] ADD = { "add", "want to", "i want to", "i got to" };
	private static final String[] DELETE = { "delete", "cancel", "erase", "remove", "del" };
	private static final String[] EDIT = { "edit", "change" };
	private static final String[] EXIT = { "exit", "finish", "done", "leave" };
	private static final String[] UNDO = { "undo", "un do" };
	private static final String[] REDO = { "redo", "re do" };
	private static final String[] CLEAR = { "clear", "remove all" };
	private static final String[] SHOW = { "show", "display" };
	private static final String[] CHECK = { "mark", "check" };
	private static final String[] SEARCH = { "search", "find", "look for" };
	private static final String[] DATE = { "yesterday", "today", "tomorrow", "tmr", "the day after tomorrow" };
	private static final String[] START_DATE = { "-sta", "start", "start-date", "from", "startdate" };
	private static final String[] END_DATE = { "-end", "end", "end-date", "to", "enddate" };
	private static final String[] DEADLINE_DATE = { "-dea", "deadline", "dead", "by" };
	private static final String[] CONTENT = { "-des", "description", "descrip", "cont", "content" };

	private final static int NATURAL_STATE = 1;
	private final static int SEARCH_STATE = 2;
	private final static int SHOW_STATE = 3;
	private final static int SORT_STATE = 4;
	public static int current_status = NATURAL_STATE;

	private static Logger CommandAddLogger = Logger.getLogger("CommandAddLogger");

	/**
	 * Parser the user input and returns command.
	 *
	 * @@author A0131838A
	 */
	public static Command parser(String input) {
		Command command;
		if (input.equals("")) {
			return new CommandInvalid("User input cannot be empty");
		}

		if ((command = parserAdd(input)) != null) {
			current_status = NATURAL_STATE;
			return command;
		} else if ((command = parserEdit(input)) != null) {
			current_status = NATURAL_STATE;
			return command;
		} else if ((command = parserShow(input)) != null) {
			current_status = SHOW_STATE;
			return command;
		} else if ((command = parserExit(input)) != null) {
			return command;
		} else if ((command = parserDelete(input)) != null) {
			current_status = NATURAL_STATE;
			return command;
		} else if ((command = parserSave(input)) != null) {
			current_status = NATURAL_STATE;
			return command;
		} else if ((command = parserClear(input)) != null) {
			current_status = NATURAL_STATE;
			return command;
		} else if ((command = parserUndo(input)) != null) {
			current_status = NATURAL_STATE;
			return command;
		} else if ((command = parserOpen(input)) != null) {
			current_status = NATURAL_STATE;
			return command;
		} else if ((command = parserCheck(input)) != null) {
			return command;
		} else if ((command = parserSearch(input)) != null) {
			current_status = SEARCH_STATE;
			return command;
		} else if ((command = parserRedo(input)) != null) {
			current_status = NATURAL_STATE;
			return command;
		} else if ((command = parserHelp(input)) != null) {
			return command;
		} else if ((command = parserDisplayAll(input)) != null) {
			current_status = NATURAL_STATE;
			return command;
		} else if ((command = parserSort(input)) != null) {
			current_status = SORT_STATE;
			return command;
		}
		return new CommandInvalid("Invalid Command. Please check 'Help' for proper input.");
	}

	/**
	 * Parser redo command.
	 *
	 * @@author A0131838A
	 */
	public static Command parserRedo(String input) {
		String inputToLowerCase = input.toLowerCase();
		int index;
		for (String key : REDO) {
			index = inputToLowerCase.indexOf(key);
			if (index != -1) {
				return new CommandRedo();
			}
		}
		return null;
	}

	/**
	 * Parser undo command.
	 *
	 * @@author A0131838A
	 */
	public static Command parserUndo(String input) {
		String inputToLowerCase = input.toLowerCase();
		int index;
		for (String key : UNDO) {
			index = inputToLowerCase.indexOf(key);
			if (index != -1) {
				return new CommandUndo();
			}
		}
		return null;

	}

	/**
	 * parser showall command
	 * 
	 * @@author A0131838A
	 */
	private static Command parserDisplayAll(String input) {
		if (input.equalsIgnoreCase("showall")) {
			return new CommandShowall();
		}
		return null;
	}

	/**
	 * Parser exit command.
	 *
	 * @@author A0131838A
	 */
	public static Command parserExit(String input) {
		String inputToLowerCase = input.toLowerCase();
		int index;
		for (String key : EXIT) {
			index = inputToLowerCase.indexOf(key);
			if (index != -1) {
				return new CommandExit();
			}
		}
		return null;
	}

	/**
	 * Parser save command.
	 *
	 * @@author A0131838A
	 */
	private static Command parserSave(String input) {
		String inputToLowerCase, content;
		int index, contentIndex;
		inputToLowerCase = input.toLowerCase();
		index = inputToLowerCase.indexOf("save");
		if (index == 0) {
			contentIndex = index + "save".length();
			content = input.substring(contentIndex).trim();
			return new CommandSave(content);
		} else
			return null;
	}

	/**
	 * Parser open command.
	 *
	 * @@author A0131838A
	 */
	private static Command parserOpen(String input) {
		String inputToLowerCase, content;
		int index, contentIndex;
		inputToLowerCase = input.toLowerCase();
		index = inputToLowerCase.indexOf("open");
		if (index == 0) {
			contentIndex = index + "save".length();
			content = input.substring(contentIndex).trim();
			return new CommandOpen(content);
		} else
			return null;
	}

	/**
	 * parser help command
	 *
	 * @@author A0131838A
	 */
	private static Command parserHelp(String input) {
		if (input.equalsIgnoreCase("help")) {
			return new CommandHelp();
		}
		return null;
	}

	/**
	 * parser clear command
	 * 
	 * @@author A0131838A
	 */
	private static Command parserClear(String input) {
		String inputToLowerCase = input.toLowerCase();
		int index;
		for (String key : CLEAR) {
			index = inputToLowerCase.indexOf(key);
			if (index != -1) {
				return new CommandClear();
			}
		}
		return null;
	}

	/**
	 * Parser add command.
	 * 
	 * 
	 * @@author A0131838A
	 */

	private static Command parserAdd(String input) {
		String inputToLowerCase, content;
		int index;
		inputToLowerCase = input.toLowerCase();
		for (String key : ADD) {
			index = inputToLowerCase.indexOf(key);
			if (index == 0) {
				content = input.replace(key, "").trim();
				CommandAddLogger.log(Level.INFO, "start to parser add command");
				try {
					return parseDate(content);
				} catch (InvalidDateOrderException doiException) {
					CommandAddLogger.log(Level.WARNING, "Error: end date is early than srart date", doiException);
					return new CommandInvalid(doiException.getMessage());
				} catch (MultipleDatesException mddException) {
					CommandAddLogger.log(Level.WARNING, "Error: more than 2 date detected", mddException);
					return new CommandInvalid(mddException.getMessage());
				} catch (EmptyDescriptionException ndExceptipn) {
					CommandAddLogger.log(Level.WARNING, "Error: Empty Description", ndExceptipn);
					return new CommandInvalid(ndExceptipn.getMessage());

				}
			}
		}
		return null;
	}

	/**
	 * Parser sort command.
	 * 
	 * @@author A0131838A
	 */
	public static Command parserSort(String input) {
		String inputToLowerCase, sortKey;
		inputToLowerCase = input.toLowerCase();
		String[] str_arr = inputToLowerCase.split(" ");

		if (str_arr.length != 2) {
			return null;
		}
		if (input.equals("sort") || input.equals("arrange")) {
			return new CommandSort("No sortKey");
		}

		sortKey = str_arr[1];

		if (sortKey.equals("des")) {
			return new CommandSort("description");
		} else if (sortKey.equals("dl")) {
			return new CommandSort("deadline");
		} else {
			return null;
		}

	}

	/**
	 * Parser a date within add command.
	 *
	 * @@author A0131838A
	 */
	private static Command parseDate(String content) {
		Date startDate, endDate;
		ArrayList<String> sensitiveWord = new ArrayList<>(Arrays.asList(DATE));
		String description = "", patstring = "\"(.*)\"", strPattern = "", matPattern;
		String[] noPatList, leftConList;
		Pattern pat = Pattern.compile(patstring);
		Matcher mat = pat.matcher(content);
		boolean find = mat.find();
		// if description indicated by user,
		// remove it and extract the time
		if (find) {
			strPattern = mat.group(0).replaceAll("\"", "");
			content = content.replace(strPattern, "");

		}

		ArrayList<Date> date = DateGUIparser.getListDate(content);
		if (!date.isEmpty()) {
			if (find) {
				description = strPattern;
			} else {
				matPattern = DateGUIparser.getMatchInput(content);
				noPatList = content.split(matPattern);
				leftConList = noPatList[0].split(" ");
				ArrayList<String> wordList = new ArrayList<>(Arrays.asList(leftConList));
				if (!sensitiveWord.contains(matPattern)) {
					wordList.remove(wordList.size() - 1);
				}
				for (String str : wordList) {
					description += str + " ";
				}
				for (int i = 1; i < noPatList.length; i++) {
					description = description + noPatList[i] + " ";
				}
				description = description.trim();
			}
		}
		// 1 date(as a deadline)
		if (date.size() == 1) {
			return new CommandAdd(description, date.get(0));
		}
		// 2 dates(as start time and end time)
		if (date.size() == 2) {
			startDate = date.get(0);
			endDate = date.get(1);
			if (startDate.compareTo(endDate) < 0) {
				return new CommandAdd(description, date.get(0), date.get(1));
			} else {
				throw new InvalidDateOrderException();
			}
		}
		// 3 or more dates(will be a exception)
		if (date.size() > 2) {
			throw new MultipleDatesException(date.size());
		}
		if (find) {
			description = strPattern;
		} else {
			description = content;
		}
		if (description.trim().equals("")) {
			throw new EmptyDescriptionException();
		}
		CommandAddLogger.log(Level.INFO, "successful add");
		return new CommandAdd(description);
	}

	/**
	 * Parser for a delete command.
	 * 
	 * @@author A0131838A
	 * 
	 */
	private static Command parserDelete(String input) {
		String tIndexStr, inputToLowerCase;
		int index, beginIndex, tIndex;
		Task t;
		inputToLowerCase = input.toLowerCase();
		for (String key : DELETE) {
			index = inputToLowerCase.indexOf(key);
			if (index == 0) {
				try {
					beginIndex = index + key.length();
					tIndexStr = inputToLowerCase.substring(beginIndex).trim();
					tIndex = Integer.parseInt(tIndexStr) - 1;
					if (current_status == SORT_STATE && (tIndex >= Logic.tasksSorted.size() || tIndex < 0)) {
						t = Logic.tasksSorted.get(tIndex);
						tIndex = Logic.tasks.indexOf(t);
						return new CommandInvalid("Please type a whthin range's index!");

					} else if (current_status == SHOW_STATE && (tIndex >= Logic.showTasks.size() || tIndex < 0)) {
						t = Logic.showTasks.get(tIndex);
						tIndex = Logic.tasks.indexOf(t);
						return new CommandInvalid("Please type a whthin range's index!");

					} else if (current_status == SEARCH_STATE && (tIndex >= Logic.tasksFound.size() || tIndex < 0)) {
						t = Logic.tasksFound.get(tIndex);
						tIndex = Logic.tasks.indexOf(t);
						return new CommandInvalid("Please type a whthin range's index!");

					} else {
						if (tIndex >= Logic.tasks.size() || tIndex < 0) {
							return new CommandInvalid("Please type a whthin range's index!");
						}
					}
					return new CommandDelete(tIndex);
				} catch (NumberFormatException numberFormatException) {
					return new CommandInvalid("Please type your index correctly!");
				}
			}
		}
		return null;
	}

	/**
	 * Parser edit command.
	 * 
	 * @@author A0131838A
	 * 
	 */

	private static Command parserEdit(String input) {

		String[] inputs = input.split(" ");
		String details = "";

		if (inputs.length < 3) {
			return null;
		}

		// check whether there is EDIT command
		boolean isEdit = false;
		for (String key : EDIT) {
			if (inputs[0].toLowerCase().equals(key)) {
				isEdit = true;
				break;
			}
		}

		if (!isEdit) {
			return null;
		}

		// make sure there is a valid index
		int tIndex;
		try {
			tIndex = Integer.parseInt(inputs[1]) - 1;
			if (current_status == SHOW_STATE && (tIndex < 0 || tIndex >= Logic.showTasks.size())) {
				Task t = Logic.showTasks.get(tIndex);
				tIndex = Logic.tasks.indexOf(t);
				return new CommandInvalid("Please type a whthin range's index!");

			} else if (current_status == SORT_STATE && (tIndex < 0 || tIndex >= Logic.tasksSorted.size())) {
				Task t = Logic.tasksSorted.get(tIndex);
				tIndex = Logic.tasks.indexOf(t);
				return new CommandInvalid("Please type a whthin range's index!");

			} else if (current_status == SEARCH_STATE && (tIndex < 0 || tIndex >= Logic.tasksFound.size())) {
				Task t = Logic.tasksFound.get(tIndex);
				tIndex = Logic.tasks.indexOf(t);
				return new CommandInvalid("Please type a whthin range's index!");

			} else {
				if (tIndex < 0 || tIndex >= Logic.tasks.size()) {
					return new CommandInvalid("");
				}
			}
		} catch (NumberFormatException numberFormatException) {
			return new CommandInvalid("Please type your index correctly!");
		}

		for (int i = 2; i < inputs.length; i++) {
			details += inputs[i] + " ";
		}
		return parseEditDetails(details, tIndex);
	}

	/**
	 * Parser the details of edit command.
	 *
	 * 
	 * @@author A0131838A
	 */

	private static Command parseEditDetails(String details, int tIndex) {

		String[] words = details.split(" ");
		ArrayList<String> wordsList = new ArrayList<>(Arrays.asList(words));

		int deadBeginIndex = -1, startIndex = -1, endIndex = -1, descripIndex = -1;
		String tempStr = "", newDescrip = null;
		Date newDead = null, newStart = null, newEnd = null;
		boolean clearDead = false, clearDura = false;

		// parse for the deadline
		for (String deadline_key : DEADLINE_DATE) {
			if (wordsList.contains(deadline_key)) {
				deadBeginIndex = wordsList.indexOf(deadline_key) + 1;

				for (int i = deadBeginIndex; i < wordsList.size(); i++) {
					if (i < wordsList.size()) {
						tempStr += wordsList.get(i) + " ";
					}
				}
				tempStr.trim();
				if (!tempStr.equals("")) {
					newDead = DateGUIparser.getListDate(tempStr).get(0);
				}
				tempStr = "";
				if (newDead == null) {
					clearDead = true;
				}
				break;
			}
		}

		if (!clearDead) {
			// parse for the start date
			for (String start_key : START_DATE) {

				if (wordsList.contains(start_key)) {

					startIndex = wordsList.indexOf(start_key) + 1;

					for (int i = startIndex; i < wordsList.size(); i++) {
						tempStr += wordsList.get(i) + " ";
					}
					tempStr.trim();
					if (!tempStr.equals("")) {
						newStart = DateGUIparser.getListDate(tempStr).get(0);
					}
					if (newStart == null) {
						clearDura = true;
					}
					tempStr = "";
					break;
				}
			}
			// parse for the end date
			for (String end_key : END_DATE) {
				if (wordsList.contains(end_key)) {
					endIndex = wordsList.indexOf(end_key) + 1;

					for (int i = endIndex; i < wordsList.size(); i++) {
						tempStr += wordsList.get(i) + " ";
					}
					tempStr.trim();
					if (!tempStr.equals("")) {
						newEnd = DateGUIparser.getListDate(tempStr).get(0);
					}
					if (newEnd == null) {
						clearDura = true;
					}
					tempStr = "";
					break;
				}
			}
		}
		// parse for the content
		for (String description_key : CONTENT) {
			if (wordsList.contains(description_key)) {
				descripIndex = wordsList.indexOf(description_key) + 1;

				for (int i = descripIndex; i < wordsList.size(); i++) {
					if (Arrays.asList(START_DATE).contains(wordsList.get(i))
							|| Arrays.asList(END_DATE).contains(wordsList.get(i))
							|| Arrays.asList(DEADLINE_DATE).contains(wordsList.get(i))) {
						break;

					}
					tempStr += wordsList.get(i) + " ";
				}
				tempStr.trim();
				newDescrip = tempStr;
			}
		}

		if (newDescrip == null && newDead == null && newStart == null && newEnd == null && !clearDead && !clearDura)

		{
			return new CommandInvalid("Please press 'help' and phrase your edit command differently!");
		}

		// make sure start date is early than end date
		if (startIndex != -1 && endIndex != -1 && (newStart.compareTo(newEnd) > 0)) {

			return new CommandInvalid("Make sure your end date is later than your start date!");
		} else if (startIndex != -1 && (newStart != null)) {
			{
				if (Logic.tasks.get(tIndex).getEndDate() != null) {
					if (newStart.compareTo(Logic.tasks.get(tIndex).getEndDate()) > 0) {
						return new CommandInvalid("Make sure your end date is later than your end date!");
					}
				} else {
					return new CommandInvalid("You can't just change the start date!");
				}
			}

		} else if (endIndex != -1 && newEnd != null) {

			if (Logic.tasks.get(tIndex).getStartDate() != null) {
				if (Logic.tasks.get(tIndex).getStartDate().compareTo(newEnd) > 0) {
					return new CommandInvalid("Make sure your end date is later than your end date!");
				}
			} else {
				return new CommandInvalid("You can't just change the end date!");
			}
		}

		return new CommandEdit(tIndex, newDescrip, newDead, newStart, newEnd, clearDead, clearDura);
	}

	/**
	 * Parser Check Command.
	 *
	 * @@author A0131838A
	 */
	private static Command parserCheck(String input) {
		String tIndexStr, inputToLowerCase;
		int index, beginIndex, tIndex;
		Task t;
		inputToLowerCase = input.toLowerCase();
		String[] split = inputToLowerCase.split("\\s+");

		for (String key : CHECK) {
			index = inputToLowerCase.indexOf(key);
			if (index != -1) {
				try {
					beginIndex = index + key.length();
					tIndexStr = inputToLowerCase.substring(beginIndex).trim();
					tIndex = Integer.parseInt(tIndexStr) - 1;

					if (current_status == SHOW_STATE && (tIndex >= Logic.showTasks.size() || tIndex < 0)) {
						t = Logic.showTasks.get(tIndex);
						tIndex = Logic.tasks.indexOf(t);
						return new CommandInvalid("Please type a whthin range's index!");
					} else if (current_status == SORT_STATE && (tIndex >= Logic.tasksSorted.size() || tIndex < 0)) {
						t = Logic.tasksSorted.get(tIndex);
						tIndex = Logic.tasks.indexOf(t);
						return new CommandInvalid("Please type a whthin range's index!");

					} else if (current_status == SEARCH_STATE && (tIndex >= Logic.tasksFound.size() || tIndex < 0)) {
						t = Logic.tasksFound.get(tIndex);
						tIndex = Logic.tasks.indexOf(t);
						return new CommandInvalid("Please type a whthin range's index!");

					}

					else {
						if (tIndex >= Logic.tasks.size() || tIndex < 0) {
							return new CommandInvalid("Please type an index that's within range");
						}
					}

					if (split[0].equals("unmark") || split[0].equals("uncheck")) {
						return new CommandCheck(false, tIndex);
					} else {
						return new CommandCheck(true, tIndex);
					}

				} catch (NumberFormatException nfException) {
					return new CommandInvalid("Please type your index correctly!");
				}
			}
		}
		return null;
	}

	/**
	 * Parser Search Command.
	 * 
	 * @@author A0131838A
	 */

	private static Command parserSearch(String input) {
		String inputToLowerCase = input.toLowerCase();
		String[] st = inputToLowerCase.split("\\s+");
		ArrayList<String> list = new ArrayList<String>();
		for (String key : SEARCH) {
			if (key.equals(st[0])) {
				for (int i = 1; i < st.length; i++) {
					list.add(st[i]);
				}
				return new CommandSearch(list);
			}
		}
		return null;
	}

	/**
	 * Parser Show Command.
	 * 
	 * @@author A0131838A
	 */

	private static Command parserShow(String input) {
		String inputToLowerCase = input.toLowerCase();
		int index;
		ArrayList<String> list = new ArrayList<String>();
		String description = "";
		String[] st = inputToLowerCase.split("\\s+");
		for (String key : SHOW) {
			index = inputToLowerCase.indexOf(key);
			if (index != -1) {
				for (int i = 1; i < st.length; i++) {
					list.add(st[i]);
				}
				break;
			}
		}

		if (list.size() == 0) {
			return null;
		}

		for (int i = 0; i < list.size(); i++) {
			description += list.get(i);
		}
		// show deadline tasks
		if (description.equals("deadlinetask") || description.equals("deadlinetasks") || description.equals("deadline")
				|| description.equals("dead")) {
			description = "deadline";
		} // show floating tasks
		else if (description.equals("dailynotes") || description.equals("daily notes") || description.equals("daily")
				|| description.equals("dai")) {
			description = "daily";
		} // show uncompleted tasks
		else if (description.equals("uncompleted") || description.equals("incompleted")
				|| description.equals("no completed") || description.equals("uncom")) {
			description = "uncompleted";
		} // show duration tasks
		else if (description.equals("durationtask") || description.equals("durationtasks")
				|| description.equals("duration") || description.equals("dura")) {
			description = "duration";
		} // show completed tasks
		else if (description.equals("completed") || description.equals("com") || description.equals("finished")
				|| description.equals("done")) {
			description = "completed";
		} // show today tasks
		else if (description.equals("today")) {
			description = "today";
		} // show tomorrow tasks
		else if (description.equals("tomorrow") || description.equals("tmr")) {
			description = "tomorrow";
		} else {
			return new CommandInvalid("No such show command");
		}
		if (!(description.equals(null))) {
			return new CommandShow(description);
		}

		return null;
	}

}
