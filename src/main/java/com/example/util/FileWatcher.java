package com.example.util;

import com.example.game.Game;

import java.nio.file.*;
import java.util.Objects;

import static com.example.diamondcircle.StartWindowController.diamondCircleController;

public class FileWatcher extends Thread {

    private static final Path DIRECTORY = Paths.get("Results");

    private static final String FILE_TYPE = ".txt";

    private static final String FILE_PREFIX = "IGRA_";

    @Override
    public void run() {
        Game.numberOfGames = Objects.requireNonNull(DIRECTORY.toFile().listFiles()).length;

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            DIRECTORY.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

            while(true) {
                WatchKey key;
                try {
                    key = watchService.take();
                } catch (InterruptedException e) {
                    return;
                }

                for(WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent<Path> ev = (WatchEvent<Path>) event;
                    Path fileName = ev.context();

                    if(fileName.toString().trim().endsWith(FILE_TYPE) && fileName.toString().trim().startsWith(FILE_PREFIX)) {
                        Game.numberOfGames = Objects.requireNonNull(DIRECTORY.toFile().listFiles()).length;
                        diamondCircleController.updateNumberOfGames();
                    }
                }

                boolean valid = key.reset();
                if(!valid) {
                    break;
                }
            }

        } catch (Exception e) {
            UtilHelper.logExceptions(FileWatcher.class, e);
        }
    }

}
