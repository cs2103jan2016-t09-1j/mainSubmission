package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import main.logic.Logic;
import main.logic.Task;

/**
 * @@author A0114319M
 * Tests for the CommandCheck class
 */
public class CommandCheckTest {
	private Logic theLogic;
	
	@Before
	public void setUp() throws Exception {
		theLogic = Logic.getTestInstance();
		Logic.reset();
	}
	
	private void addTask() {
		
		Logic.tasks.add(new Task("go lecture"));
		Logic.tasks.add(new Task("do tutorial"));
		Logic.tasks.add(new Task("assignment"));
		Logic.tasks.get(1).setFlag(true);
	}
	
	@Test
	public void testMark(){
		addTask();
		theLogic.commandExecute("check 1");
		ArrayList<Task> actual = Logic.tasks;
		boolean expected = true;
		assertEquals(true, actual.get(0).getFlag()==(expected));
	}
	
	@Test
	public void testUnmark(){
		addTask();
		theLogic.commandExecute("uncheck 1");
		ArrayList<Task> actual = Logic.tasks;
		boolean expected = false;
		assertEquals(true, actual.get(2).getFlag()==(expected));
	}
}