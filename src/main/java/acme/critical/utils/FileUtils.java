package acme.critical.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
	public static void mkdir(Path path) {
		try {
			Files.createDirectories(path);
		} catch(IOException ex) {
			throw new RuntimeException("Directory creation failed for: " + path.toString(), ex);
		}
	}
}
