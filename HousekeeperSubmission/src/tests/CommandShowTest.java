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
 * A series of tests for ShowCommand class
 *
 */
public class CommandShowTest {
	private Logic logic;
	
	@Before
	public void setUp() throws Exception {
		logic = Logic.getTestInstance();
		Logic.reset();
	}
	
	private void populateArray() {
	    System.out.println("hi");
		
		Logic.tasks.add(new Task("go to school"));
		
		String s = "11/6";
	    SimpleDateFormat sd = new SimpleDateFormat("MM/dd");
	    try {
			Date date1 = sd.parse(s);
			Logic.tasks.add(new Task("go home", date1));
			Logic.tasks.get(1).setFlag(true);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    String t = "20 aug";
	    String u = "22 aug";
	    SimpleDateFormat sd1 = new SimpleDateFormat("dd mmm");
	    try{
	    	Date startDate = sd1.parse(t);
	    	Date endDate = sd1.parse(u);
	    	Logic.tasks.add(new Task("go to work", startDate, endDate));	    	
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
	    
	}
	
	@Test
	public void testShowFloating(){
		populateArray();
		logic.commandExecute("show daily");
		ArrayList<Task> actual = Logic.showTasks;
		Task expected = new Task("go to school");
		assertEquals(true, actual.get(0).equals(expected));
	}
	
	@Test
	public void testShowDeadline(){
		populateArray();
		logic.commandExecute("show deadline");
		ArrayList<Task> actual = Logic.showTasks;
		String s = "11/6";
	    SimpleDateFormat sd = new SimpleDateFormat("MM/dd");
	    try {
			Date date1 = sd.parse(s);
			Task expected = new Task("go home", date1);
			expected.setFlag(true);
			assertEquals(true, actual.get(0).equals(expected));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	@Test
	public void testShowDuration(){
		populateArray();
		logic.commandExecute("show duration");
		ArrayList<Task> actual = Logic.showTasks;
		
	    String t = "20 aug";
	    String u = "22 aug";
	    SimpleDateFormat sd1 = new SimpleDateFormat("dd mmm");
	    try{
	    	Date startDate = sd1.parse(t);
	    	Date endDate = sd1.parse(u);
	    	Task expected = new Task("go to work", startDate, endDate);	 
	    	assertEquals(true, actual.get(0).equals(expected));
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }	    
	}
	
	@Test
	public void testShowMarked(){
		populateArray();
		logic.commandExecute("show completed");
		ArrayList<Task> actual = Logic.showTasks;
		String s = "11/6";
	    SimpleDateFormat sd = new SimpleDateFormat("MM/dd");
	    try {
			Date date1 = sd.parse(s);
			Task expected = new Task("go home", date1);
			expected.setFlag(true);
			assertEquals(true, actual.get(0).equals(expected));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testShowUnmarked(){
		populateArray();
		logic.commandExecute("show uncompleted");
		ArrayList<Task> actual = Logic.showTasks;
		
		Task expected1 = new Task("go to school");
		assertEquals(true, actual.get(0).equals(expected1));
		
	    String t = "20 aug";
	    String u = "22 aug";
	    SimpleDateFormat sd1 = new SimpleDateFormat("dd mmm");
	    try{
	    	Date startDate = sd1.parse(t);
	    	Date endDate = sd1.parse(u);
	    	Task expected2 = new Task("go to work", startDate, endDate);
	    	assertEquals(true, actual.get(1).equals(expected2));
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
	}

}

