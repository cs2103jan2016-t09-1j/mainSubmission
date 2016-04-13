package tests;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import main.logic.Logic;
import main.logic.Task;

/**
 * @@author A0114319M
 * A series of tests for SearchCommand class.
 *
 */
public class CommandSearchTest {
	private Logic logic;
	
	@Before
	public void setUp() throws Exception {
		logic = Logic.getTestInstance();
		Logic.reset();
	}
	
	private void populateArray() {
		
		Logic.tasks.add(new Task("go to school"));
		
		String s = "11/6";
	    SimpleDateFormat sd = new SimpleDateFormat("MM/dd");
	    try {
			Date date1 = sd.parse(s);
			Logic.tasks.add(new Task("go home", date1));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		Logic.tasks.add(new Task("go to work"));
	}
	
	@Test
	public void testSearchByDescription(){
		populateArray();
		logic.commandExecute("search go to school");		
		ArrayList<Task> actual = Logic.tasksFound;
		Task expected = new Task("go to school");
		assertEquals(true, actual.get(0).equals(expected));
	}
	
	@Test
	public void testSearchByDate(){
		populateArray();
		logic.commandExecute("search date 6 Nov");
		ArrayList<Task> actual = Logic.tasksFound;
		String s = "11/6";
	    SimpleDateFormat sd = new SimpleDateFormat("MM/dd");
	    try {
	    	System.out.println("hi");
			Date date1 = sd.parse(s);
			Task expected = new Task("go home", date1);
			assertEquals(true, actual.get(0).equals(expected));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}	
}
