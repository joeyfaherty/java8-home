package ocp7.filenio2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;


public class FileMover {

    public static void main(String[] args) {
        // File conversion from Old java 6 way
        Path oldPath = new File("/home/joey/xxx.txt").toPath();

        // Paths class has all static methods that return a Path
        Path path = Paths.get("/home/joey/xxx.txt");
        Path newPathLocation = Paths.get("/home/joey/tmp/xxx.txt");
        moveFile(path, newPathLocation);
        copyFile(newPathLocation, path);
        readFile(path);
    }

    private static void moveFile(Path source, Path target) {
        try {
            Files.move(source, target.resolve(source.getFileName()), REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(String.format("error moving files from %s to %s", source.toAbsolutePath(), target.toAbsolutePath()));
        }
    }

    private static void copyFile(Path source, Path target) {
        try {
            Files.copy(source, target.resolve(source.getFileName()));
        } catch (IOException e) {
            System.out.println(String.format("error copying files from %s to %s", source.toAbsolutePath(), target.toAbsolutePath()));
        }
    }

    private static void readFile(Path file) {
        try (InputStream in = Files.newInputStream(file);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (NoSuchFileException x) {
            System.err.println("No such file exists: " + x.getFile());
        } catch (IOException  x) {
            System.err.println(x);
        }
    }

}
