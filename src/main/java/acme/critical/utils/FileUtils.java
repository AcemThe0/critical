package acme.critical.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import acme.critical.Critical;

public class FileUtils {
    public static void mkdir(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException ex) {
            throw new RuntimeException(
                "Directory creation failed for: " + path.toString(), ex
            );
        }
    }

    public static String read(Path path) {
        try {
            String ret = Files.readString(path);
            return ret;
        } catch (IOException ex) {
            throw new RuntimeException(
                "File reading failed for: " + path.toString(), ex
            );
        }
    }

    public static void write(Path path, String string) {
        try {
            Files.writeString(path, string);
        } catch (IOException ex) {
            throw new RuntimeException(
                "File writing failed for: " + path.toString(), ex
            );
        }
    }

    public static String loadAsset(String name) {
        var path =
            Critical.INSTANCE.getRoot().resolve("assets/critical/" + name);
        try {
            return Files.readString(path);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load asset: " + name);
        }
    }
}
