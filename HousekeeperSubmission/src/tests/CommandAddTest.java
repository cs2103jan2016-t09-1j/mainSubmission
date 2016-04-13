package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Calendar;
import java.util.Date;
import org.junit.Test;
import main.logic.Logic;

/**
 * @@author A0114319M
 * test cases for CRUD: add/delete/clear command
 */
public class CommandAddTest {

	/**
	 *test case for add command
	 * 
	 *test add floating task (daily notes)
	 */
	@Test
	public void testAddFloatingTask() {
		Logic logic = Logic.getInstance();
		logic.commandExecute("clear");
		String feedback = logic.commandExecute("add dance");
		// Test for correct description
		assertEquals("Task added Successfully<br>dance", feedback);
	}

	/**
	 *test case for delete command
	 */
	@Test
	public void testDelete() {
		Logic logic = Logic.getInstance();
		logic.commandExecute("clear");
		// before
		logic.commandExecute("add dance");
		assertFalse(logic.getTasks().isEmpty());
		// after
		String feedback = logic.commandExecute("delete 1");
		assertEquals("Task has been Deleted.Task: dance", feedback);
		assertTrue(logic.getTasks().isEmpty());
	}
	
	/**
	 * 
	 *test case for add
	 *for date in invalid order
	 */
	@Test
	public void testSpecialCaseAddCommand() {
		Logic logic = Logic.getInstance();
		logic.commandExecute("clear");
		logic.commandExecute("add \"See you tomorrow Jim\" by 9pm");
		assertEquals("See you tomorrow Jim", logic.getTasks().get(0).getTaskDescription());
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 21);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date deadline = cal.getTime();
		assertEquals(deadline, logic.getTasks().get(0).getTaskDeadline());
	}

	/**
	 *test case for add
	 *test add add deadline task
	 */
	@Test
	public void testAddDeadlineTask() {
		Logic logic = Logic.getInstance();
		logic.commandExecute("clear");
		logic.commandExecute("add go to class by 9am today");
		assertEquals("go to class", logic.getTasks().get(0).getTaskDescription());

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date testDate = cal.getTime();
		assertEquals(testDate, logic.getTasks().get(0).getTaskDeadline());
	}

	/**
	 * 
	 *test case for add command
	 *test for add duration task
	 */
	@Test
	public void testAddDurationTask() {
		Logic logic = Logic.getInstance();
		logic.commandExecute("clear");
		logic.commandExecute("add do housework from 10 am to 10 pm");
		assertEquals("do housework", logic.getTasks().get(0).getTaskDescription());

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 10);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		Date startDate = cal.getTime();

		cal.set(Calendar.HOUR_OF_DAY, 22);

		Date endDate = cal.getTime();
		assertEquals(startDate, logic.getTasks().get(0).getStartDate());
		assertEquals(endDate, logic.getTasks().get(0).getEndDate());
	}

	/**
	 *test case for editing command
	 */
	@Test
	public void testEdit() {
		Logic logic = Logic.getInstance();
		logic.commandExecute("clear");
		logic.commandExecute("add go for soccer practice from 10 am to 10 pm");
		assertEquals("go for soccer practice", logic.getTasks().get(0).getTaskDescription());
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 10);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date startDate = cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, 22);
		Date endDate = cal.getTime();
		assertEquals(startDate, logic.getTasks().get(0).getStartDate());
		assertEquals(endDate, logic.getTasks().get(0).getEndDate());

		logic.commandExecute("edit 1 -sta 8pm");
		cal.set(Calendar.HOUR_OF_DAY, 20);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		startDate = cal.getTime();
		assertEquals(startDate, logic.getTasks().get(0).getStartDate());
	}

	/**
	 *test case for clear command
	 */
	@Test
	public void testClearTask() {
		Logic logic = Logic.getInstance();
		logic.commandExecute("clear");
		logic.commandExecute("add help to collect laundry from 9 am to 10 am");
		logic.commandExecute("add go home");
		logic.commandExecute("i want to finish this assignment by 2359pm");
		assertEquals(3, logic.getTasks().size());
		logic.commandExecute("clear");
		assertEquals(0, logic.getTasks().size());
	}

	/**
	 *no description input
	 */
	@Test
	public void testNoDescriptionAddCommand() {
		Logic logic = Logic.getInstance();
		logic.commandExecute("clear");
		String feedback = logic.commandExecute("add  ");
		assertEquals("Sorry, Description cannot be empty!", feedback);
	}

	/**
	 * 
	 *for date in invalid order
	 */
	@Test
	public void testInvalidDateOrderAddCommand() {
		Logic logic = Logic.getInstance();
		logic.commandExecute("clear");
		String feedback = logic.commandExecute("add go for math class from 5pm to 2pm today");
		assertEquals("Sorry, your Start Date cannot be later than End Date.", feedback);
	}

}