package main.logic.command;

import main.logic.Logic;

/**
 * @@author A0124719A
 * Command to revert to the previous state before undo command.
 */
public class CommandRedo implements Command {

	@Override
	public String execute() {		
		
		if(Logic.redo.size()==0){
			return "Unable to redo!";
		} else {
			Logic.tasks = Logic.redo.pop();
			return "Redo successful!";
		}
	}
}