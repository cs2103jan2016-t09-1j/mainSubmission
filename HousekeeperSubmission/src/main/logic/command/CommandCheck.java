package main.logic.command;

import main.logic.Logic;


/**
 * @@author A0124719A
 * Command to mark tasks as done.
 */

public class CommandCheck implements Command{
	
	private boolean check;
	private int index;
	
	public CommandCheck(boolean check, int index){
		this.check = check;
		this.index = index;
	}
	
	@Override
	public String execute(){
		if(check==true){
			if(Logic.tasks.get(index).getFlag()==true) {
				return "This task has already been marked";
			} else {
				Logic.tasks.get(index).setFlag(true);
				return "This task has been marked";
			}			
		} else {
			if(Logic.tasks.get(index).getFlag()==false){
				return "This task has not yet been marked";
			} else {
				Logic.tasks.get(index).setFlag(false);
				return "This task has been unmarked";
			}
		}
	}
}
