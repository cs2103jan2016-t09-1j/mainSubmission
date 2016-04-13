package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import main.logic.Logic;
import main.logic.Task;
import main.logic.command.CommandAdd;
import main.logic.command.Command;
import main.logic.command.CommandInvalid;
import main.logic.command.CommandSave;
import main.parser.CommandParser;

/**
 * Logic and Parser Component. A series of tests for Housekeeper.
 * 
 * @@author A0114319M
 */
public class TestNormal {
	private Logic l;
	@Test
	public void testAdd() {
		l.commandExecute("add go to gym");
		ArrayList<Task> actual = Logic.tasks;
		ArrayList<Task> hoped = new ArrayList<Task>();
		hoped.add(new Task("go to gym"));
		assertEquals(true, actual.equals(hoped));
		assertEquals(true, actual.size() == hoped.size());
	}

	@Before
	public void setUp() throws Exception {
		l = Logic.getTestInstance();
		Logic.reset(); 
	}


	@Test
	public void testParser() {
		Command command;
		command = CommandParser.parser("save");
		assertTrue(command instanceof CommandSave);
		command = CommandParser.parser("please add it");
		assertTrue(command instanceof CommandInvalid);
		command = CommandParser.parser("add go to school");
		assertTrue(command instanceof CommandAdd);
		command = CommandParser.parser("go to gym");
		assertTrue(command instanceof CommandInvalid);
	}
	
}
