package main.logic.command;

import main.logic.Logic;
import main.logic.Task;
import java.util.Date;

/**
 * @@author A0124719A
 * Command to add tasks.
 */
public class CommandAdd implements Command{

	public enum TypeOfTask {
		TASK_Floating,
		TASK_Deadline,
		TASK_Duration
	}

	private String taskDescription;
	private Date taskDeadline;
	private Date startDate;
	private Date endDate;
	private TypeOfTask typeOfTask;

	/**
	 * Floating task constructor.
	 * @param  taskDescription
	 * @returntask taskDescription of task
	 */
	public CommandAdd(String taskDescription) {
		this.typeOfTask = TypeOfTask.TASK_Floating;
		this.taskDescription = taskDescription;
	}

	/**
	 * Deadline task constructor.
	 * @param  taskDescription 
	 * @param  taskTASK_Deadline    
	 * @return taskDescription, taskTASK_Deadline of task
	 */
	public CommandAdd(String taskDescription, Date taskDeadline) {
		this.typeOfTask = TypeOfTask.TASK_Deadline;
		this.taskDescription = taskDescription;
		this.taskDeadline = taskDeadline;
	}

	/**
	 * Duration task constructor
	 * @param  taskDescription
	 * @param  startDate 
	 * @param  endDate    
	 * @return start time, end time, taskDescription of task
	 */
	public CommandAdd(String taskDescription, Date startDate, Date endDate) {
		this.typeOfTask = TypeOfTask.TASK_Duration;
		this.taskDescription = taskDescription;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public String execute() {
		String showMessage = "Task added Successfully<br>";
		switch(this.typeOfTask) {
			case TASK_Floating:
				Logic.tasks.add(new Task(taskDescription));
				showMessage += taskDescription;
				break;
			case TASK_Deadline:
				Logic.tasks.add(new Task(taskDescription, taskDeadline));
				showMessage += taskDescription + "<br>[deadline] " + taskDeadline;
				break;
			case TASK_Duration:
				Logic.tasks.add(new Task(taskDescription, startDate, endDate));
				showMessage += taskDescription + "<br>[start at] " + startDate + "<br>[end at] " + endDate;
				break;
			default:
				break;
		}
		return showMessage;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

}
