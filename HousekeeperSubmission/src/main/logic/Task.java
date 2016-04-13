package main.logic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import main.exception.EmptyDescriptionException;

import java.util.Comparator;


/**
 * @@author A0124719A
 * Task represents an item that the user needs to complete.
 * 
 */
public class Task implements Serializable, Comparable<Task> {

	private static final long serialVersionUID = 1L;
	private String taskDescription = null;
	private Date taskDeadline = null;
	private Date startDate = null;
	private Date endDate = null;
	private boolean flag = false;

	/**
	 * 	
	 * 
	 * @param taskDescription
	 * Floating task constructor
	 * @return task containing the taskDescription
	 * @throws NotaskDescriptionException exception
	 */
	public Task(String taskDescription) throws EmptyDescriptionException {
		if (taskDescription.trim().equals("")) {
			throw new EmptyDescriptionException();
		}
		this.taskDescription = taskDescription;
	}

	/**
	 * Deadline task constructor
	 * 
	 * @param taskDescription   
	 * @param taskDeadline
	 * @return task
	 */
	public Task(String taskDescription, Date taskDeadline) {
		this.taskDescription = taskDescription;
		this.taskDeadline = taskDeadline;
	}

	/**
	 * Duration task constructor
	 * 
	 * @param taskDescription
	 * @param startDate
	 * @param endDate
	 * @return task
	 */
	public Task(String taskDescription, Date startDate, Date endDate) {
		this.taskDescription = taskDescription;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * To duplicate the constructor.
	 * 
	 * @param task
	 * @return cloned task
	 */
	public Task(Task task) {
		taskDescription = task.getTaskDescription();
		startDate = task.getStartDate();
		endDate = task.getEndDate();
		taskDeadline = task.getTaskDeadline();
		flag = task.getFlag();
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public Date getTaskDeadline() {
		return taskDeadline;
	}

	public void setTaskDeadline(Date taskDeadline) {
		this.taskDeadline = taskDeadline;
	}

	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public String toString() {
		return "Task: " + getTaskDescription();
	}

	/**
	 * This returns the date in the required format
	 * 
	 * @param date
	 * @return formated date in certain way
	 */
	public String dateFormat(Date date) {
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy K:mma");
		return df.format(date);
	}

	/**
	 * This returns taskDeadline in the required format
	 * 
	 * @return formated date
	 */
	public String getTaskDeadlineInString() {
		if (taskDeadline != null) {
			return dateFormat(taskDeadline);
		}
		return "";
	}

	/**
	 * This returns the start date in the required format
	 * 
	 * @return formated date
	 */
	public String getStartDateInString() {
		if (startDate != null) {
			return dateFormat(startDate);
		}
		return "";
	}

	/**
	 * This returns the end date in the required format
	 * 
	 * @return formated date
	 */
	public String getEndDateInString() {
		if (endDate != null) {
			return dateFormat(endDate);
		}
		return "";
	}

	/**
	 * To compare two tasks and returns a boolean value
	 * 
	 * @param task
	 */
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			Task task = (Task) obj;
			boolean taskDescriptionCompare = taskDescription.equals(task.getTaskDescription());
			boolean taskDeadlineCompare;
			boolean startDateCompare;
			boolean endDateCompare;
			boolean flagCompare;
			if (taskDeadline == null) {
				taskDeadlineCompare = taskDeadline == task.getTaskDeadline();
			} else {
				taskDeadlineCompare = taskDeadline.equals(task.getTaskDeadline());
			}

			if (startDate == null) {
				startDateCompare = startDate == task.getStartDate();
			} else {
				startDateCompare = startDate.equals(task.getStartDate());
			}

			if (endDate == null) {
				endDateCompare = endDate == task.getEndDate();
			} else {
				endDateCompare = endDate.equals(task.getEndDate());
			}

			flagCompare = flag == task.getFlag();

			return (taskDescriptionCompare && taskDeadlineCompare && startDateCompare && endDateCompare && flagCompare);
		} else {
			return false;
		}
	}

	/**
	 * Compares two tasks alphabetically base on taskDescription
	 * 
	 * @param task
	 * @return int
	 */
	@Override
	public int compareTo(Task task) {
		return this.taskDescription.compareTo(task.getTaskDescription());
	}

	/**
	 * Compares two tasks based on taskDeadline
	 * 
	 * @param
	 * @return Comparator
	 */
	public static Comparator<Task> getTaskDeadlineComparable() {
		return new Comparator<Task>() {
			@Override
			public int compare(Task task1, Task task2) {
				return task1.getTaskDeadline().compareTo(task2.getTaskDeadline());
			}
		};
	}
	
	/**
	 * Serializes the task object to prepare for saving
	 *
	 * @param stream
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream stream) throws IOException {
		stream.writeObject(taskDescription);
		stream.writeObject(taskDeadline);
		stream.writeObject(startDate);
		stream.writeObject(endDate);
		stream.writeObject(flag ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 * Deserializes the task object. to prepare to open
	 *
	 * @param stream
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
		taskDescription = (String) stream.readObject();
		taskDeadline = (Date) stream.readObject();
		startDate = (Date) stream.readObject();
		endDate = (Date) stream.readObject();
		flag = ((Boolean) stream.readObject()) ? true : false;
	}
}