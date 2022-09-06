package com.example.util;

import com.example.game.Game;

import java.nio.file.*;
import java.util.Objects;

import static com.example.diamondcircle.StartWindowController.diamondCircleController;

public class FileWatcher extends Thread {

    private static final Path directory = Paths.get("History");

    @Override
    public void run() {
        Game.numberOfGames = Objects.requireNonNull(directory.toFile().listFiles()).length;

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE);

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

                    if(fileName.toString().trim().endsWith(".txt") && fileName.toString().trim().startsWith("IGRA_")) {
                        Game.numberOfGames = Objects.requireNonNull(directory.toFile().listFiles()).length;
                        diamondCircleController.updateNumberOfGames();
                    }
                }

                boolean valid = key.reset();
                if(!valid) {
                    break;
                }
            }

        } catch (Exception e) {
           e.printStackTrace();
        }
    }

}
