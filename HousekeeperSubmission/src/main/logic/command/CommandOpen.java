package main.logic.command;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import main.logic.Logic;

/**
 * @@author A0114319M
 * Opens mySchedule.ser given a filePath.
 *
 */
public class CommandOpen implements Command {
	private String filePath = "";

	public CommandOpen(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String execute() {

		if (filePath.equals("")) {
			File file = new File(Logic.getFileName());
			if (file.exists()) {
				Logic.open(filePath, Logic.getFileName());
				return "File opened at: " + filePath;
			}
		}

		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			Logic.setFilePath(filePath);
			File file = new File(filePath, Logic.getFileName());
			if (file.exists() && !file.isDirectory()) {
				Logic.open(filePath, Logic.getFileName());
				return "File opened from: " + filePath;
			}
			return "No such file exists!";
		} else {
			return "No such file path.";
		}
	}

}
