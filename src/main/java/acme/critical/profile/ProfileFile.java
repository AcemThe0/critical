package acme.critical.profile;

import java.io.File;
import java.nio.file.Path;

import acme.critical.Critical;
import acme.critical.profile.Profile;
import acme.critical.utils.FileUtils;

public class ProfileFile {
	public Path path;

	public ProfileFile(String name) {
		path = Profile.INSTANCE.pDir.resolve(name);

		long len = new File(path.toString()).length();
		if (len <= 0) restoreDefault();
		load();
	}

	public String onSave() {
		return null;
	}

	public void onLoad(String string) {
	}

	public void save() {
		String string = onSave();
		FileUtils.write(path, string);
	}

	public void restoreDefault() {
		if (onDefault() == null) save();
		else {
			String string = onDefault();
			FileUtils.write(path, string);
		}
	}

	public String onDefault() {
		return null;
	}

	public void load() {
		String string = FileUtils.read(path);
		onLoad(string);
	}
}
