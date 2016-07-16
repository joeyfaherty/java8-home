package ocp7.filenio2;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class AccessDirectoryTreeRecursive {

    private static Path rootDirectory = Paths.get("/home/joey/tmp");

    static FileVisitor<Path> fileVisitor = new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
            System.out.println(String.format("Before visit the '%s' directory", dir));
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attribs) {
            System.out.println(String.format("Visiting file '%s' which has size %d bytes", file, attribs.size()));
            return FileVisitResult.CONTINUE;
        }
    };

    public static void main(String[] args) throws IOException {
        Files.walkFileTree(rootDirectory, fileVisitor);
    }


}
