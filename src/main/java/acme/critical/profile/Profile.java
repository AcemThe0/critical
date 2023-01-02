package acme.critical.profile;

import java.nio.file.Files;
import java.nio.file.Path;

import acme.critical.Critical;
import acme.critical.profile.files.*;
import acme.critical.utils.FileUtils;

public class Profile {
	private String pName;
	private Path pDir;

	public Profile(String name, Path path) {
		pName = name;
		pDir = path;
		FileUtils.mkdir(pDir);
	}

	public void save() {
	}

	public void load() {
	}
}
