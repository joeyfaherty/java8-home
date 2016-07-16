package ocp7.filenio2;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class DirectoryWatcher {

    public static void main(String[] args) throws IOException {

        // register a path to watch
        Path dirToWatch = Paths.get("/home/joey/watchDir");

        // create a watch service instance
        WatchService watchService = FileSystems.getDefault().newWatchService();

        WatchKey watchKey = dirToWatch.register(watchService, StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);

        while(true) {
            try {
                final WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    System.out.println(event.kind());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // we need to reset the key to continue to receive events
            watchKey.reset();
        }
    }
}