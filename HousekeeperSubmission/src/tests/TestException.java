package tests;

import org.junit.Test;

import main.logic.Logic;
import static org.junit.Assert.*;

/**
 * Logic Component Test.  
 * Checks if the invalid input throws the exception.
 * @@author A0114319M
 */
public class TestException {
	
	@Test
	public void exceptionTest() {
		Logic l = Logic.getInstance();
		String input = l.commandExecute("Hello world");
		assertEquals("Invalid Command. Please check 'Help' for proper input.", input);		
	}
}
