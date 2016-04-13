package main.logic;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.logic.command.Command;
import main.logic.command.CommandHelp;
import main.logic.command.CommandInvalid;
import main.logic.command.CommandOpen;
import main.logic.command.CommandSave;
import main.logic.command.CommandUndo;
import main.parser.CommandParser;
import main.storage.Storage;

/**
 * @@author A0124719A
 * Handles the list of tasks, creates and maintains their temporary
 * storage.
 * 
 */
public class Logic {
	private static final Logger logger = Logger.getLogger(Logic.class.getName());
	private static String filePath;
	private static String fileName;
	public static ArrayList<Task> tasksSorted;
	public static ArrayList<Task> tasksFound;
	public static ArrayList<Task> showTasks;
	public static Stack<ArrayList<Task>> taskHistory;
	public static Stack<ArrayList<Task>> redo;
	public static ArrayList<Task> tasks;
	private static Logic logic;
	private int current_status = 1;

	/**
	 * The default constructor.
	 * 
	 */
	private Logic() {
		this("", "mySchedule.ser");
	}

	/**
	 * Reads the userInput to tell which type it is and handle it through parser and command.
	 * 
	 * @param userInput
	 * @return a command project
	 */
	//To execute Logic command.
	public String commandExecute(String userInput) {
		Command command = CommandParser.parser(userInput);
		assert command != null : "Command is null!";
		current_status = CommandParser.current_status;
		//CommandParser.setRecur(userInput);
		if (!(command instanceof CommandUndo) && !(command instanceof CommandSave) && !(command instanceof CommandOpen)
				&& !(command instanceof CommandInvalid) && !(command instanceof CommandHelp)) {
			Logic.taskHistory.push(duplicate(Logic.tasks));
		} else if (command instanceof CommandUndo) {
			current_status = 1;
			Logic.redo.push(duplicate(Logic.tasks));
		}

		logger.log(Level.FINE, "processing command {0}", command);
		String logicReturn = command.execute();
		return logicReturn;
	}

	/**
	 *
	 * @Obtains the main tasks list
	 */
	public ArrayList<Task> getTasks() {
		return Logic.tasks;
	}

	/**
	 *
	 * @Obtains set the main tasks list
	 */
	public void setTasks(ArrayList<Task> createTask) {
		Logic.tasks = createTask;
	}
	
	/**
	 *
	 *
	 * @Obtains and sets main task list for sort command.
	 */

	public ArrayList<Task> getTasksSorted() {
		return tasksSorted;
	}

	public static void setTasksSorted(ArrayList<Task> tasksSorted) {
		Logic.tasksSorted = tasksSorted;
	}

	/**
	 *
	 *
	 * @Obtains and sets main task list for search and show command.
	 */
	public ArrayList<Task> getTaskSearch() {
		return tasksFound;
	}

	public static void setTaskSearch(ArrayList<Task> tasksFound) {
		Logic.tasksFound = tasksFound;
	}

	public ArrayList<Task> getTaskShow() {
		return Logic.showTasks;
	}

	public static ArrayList<Task> setShowTasks(ArrayList<Task> showTasks) {
		return Logic.showTasks = showTasks;
	}
	
	/**
	 *
	 * @return current status to determine number of columns to display in GUI.
	 */
	public int getCurrentStatus() {
		return current_status;
	}

	/**
	 * Duplicates a Task object.
	 *
	 * @param originalTask
	 * @return the duplicated task object
	 */
	private static ArrayList<Task> duplicate(ArrayList<Task> originalTask) {
		ArrayList<Task> duplicate = new ArrayList<Task>();
		for (Task task : originalTask) {
			duplicate.add(new Task(task));
		}
		return duplicate;
	}

	/**
	 * Saves to file name.
	 *
	 * @param fileName
	 */
	public static void saveFile(String filePath, String fileName) {

		File file;
		if (filePath.equals("")) {
			file = new File(fileName);
			Storage.toSerialize(tasks, file);

		} else {
			Path path = Paths.get(filePath);
			if (Files.exists(path)) {
				file = new File(filePath, fileName);
				Storage.toSerialize(tasks, file);
			}
		}
	}

	/**
	 * Opens the file name.
	 *
	 * @param fileName
	 */
	public static void open(String filePath, String fileName) {
		File file;
		if (filePath.equals("")) {
			file = new File(fileName);
		} else {
			file = new File(filePath, fileName);
		}
		tasks = Storage.toDeserialize(file);
		Logic.setFilePath(filePath);
		Logic.setFileName(fileName);
		taskHistory = new Stack<ArrayList<Task>>();
		redo = new Stack<ArrayList<Task>>();
	}

	/**
	 * applies singleton pattern. refer to project manual for more information.
	 *
	 * @return the logic instance
	 */
	public static Logic getInstance() {
		if (logic == null) {
			logic = new Logic();
		}
		return logic;
	}

	/**
	 * For testing purposes.
	 * 
	 * @return a test copy of logic
	 */
	public static Logic getTestInstance() {
		return new Logic("src/main/storage/", "mySchedule.ser");
	}

	/**
	 * For testing purposes.
	 * 
	 */

	public static String getFileName() {
		return fileName;
	}

	public static void setFileName(String fileName) {
		Logic.fileName = fileName;
	}

	public static String getFilePath() {
		return filePath;
	}

	public static void setFilePath(String filePath) {
		Logic.filePath = filePath;
	}
	
	public static void reset() {
		Logic.tasks = new ArrayList<Task>();
	}
	/**
	 * For testing purposes.
	 * 
	 * @param fileName
	 */
	private Logic(String filePath, String fileName) {
		assert fileName != null : "The file name empty!";
		assert false;
		Logic.setFilePath(filePath);
		Logic.setFileName(fileName);
		File file = new File(filePath, fileName);
		if (file.exists() && !file.isDirectory()) {
			tasks = Storage.toDeserialize(file);
		} else {
			tasks = new ArrayList<Task>();
		}
		taskHistory = new Stack<ArrayList<Task>>();
		redo = new Stack<ArrayList<Task>>();
	}
}