package main.logic.command;

import main.logic.Logic;

/**
 * @@author A0114319M
 * Saves mySchedule.ser before Housekeeper exits. 
 */
public class CommandExit implements Command {

	String exitHousekeeper;
	public CommandExit() {
		exitHousekeeper = "exit";
	}

	@Override
	public String execute() {
		Logic.saveFile(Logic.getFilePath(), Logic.getFileName());
		return exitHousekeeper;
	}

}
