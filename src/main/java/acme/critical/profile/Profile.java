package acme.critical.profile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import acme.critical.Critical;
import acme.critical.profile.ProfileFile;
import acme.critical.profile.files.*;
import acme.critical.utils.FileUtils;

public class Profile {
	public static Profile INSTANCE;

	public String pName;
	public Path pDir;

	private ArrayList<ProfileFile> pFiles = new ArrayList<>();

	public Profile(String name, Path path) {
		INSTANCE = this;
		pName = name;
		pDir = path;
		FileUtils.mkdir(pDir);

		pFiles.add(new FriendsFile());
		pFiles.add(new ThemeFile());
	}

	public void save() {
		for (ProfileFile pf : pFiles) pf.save();
	}

	public <T extends ProfileFile> void save(Class<T> clasS) {
		ProfileFile file = pFiles.stream().filter(pf -> pf.getClass() == clasS).findFirst().orElse(null);
		file.save();
	}

	public void load() {
		for (ProfileFile pf : pFiles) pf.load();
	}

	public <T extends ProfileFile> void load(Class<T> clasS) {
		ProfileFile file = pFiles.stream().filter(pf -> pf.getClass() == clasS).findFirst().orElse(null);
		file.load();
	}

}
