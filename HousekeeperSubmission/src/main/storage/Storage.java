package main.storage;

import java.util.ArrayList;

import main.logic.Task;

import java.io.*;

/**
 * @@author A0114319M
 * To serialize and de-serialize tasks for storage purpose.
 */

public class Storage {

	/**
	 * Serialize the ArrayList that contains all the tasks into a file named tasks.ser
	 * 
	 * @param A list of tasks that will be serialized
	 */
	public static void toSerialize(ArrayList<Task> tasks, File file) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(tasks);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/**
	 * @@author A0114319M
	 * Deserialize the file tasks.ser and cast its contents to an ArrayList of tasks.
	 * 
	 * @return A list of tasks
	 */
	
	@SuppressWarnings("unchecked")
	public static ArrayList<Task> toDeserialize(File file) {
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ArrayList<Task> tasks = (ArrayList<Task>) in.readObject();
			in.close();
			fileIn.close();
			return tasks;
		} catch (IOException i) {
			i.printStackTrace();
			return null;
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
			return null;
		}
	}

}