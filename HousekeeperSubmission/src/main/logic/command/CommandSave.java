package main.logic.command;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import main.logic.Logic;

/**
 * @@author A0114319M
 * Saves mySchedule.ser to a specified filePath.
 *
 */
public class CommandSave implements Command {
	private String filePath = "";

	public CommandSave(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String execute() {
		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			Logic.setFilePath(filePath);
			Logic.setFileName("mySchedule.ser");
			Logic.saveFile(filePath, Logic.getFileName());
			return "File successfully saved to: " + filePath;
		} else {
			return "Unable to save! No such file path.";
		}
	}
}
