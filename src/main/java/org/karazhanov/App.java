package org.karazhanov;

import org.karazhanov.config.Config;
import org.karazhanov.service.FilesService;

/**
 * @author karazhanov on 23.01.19.
 */
public class App {

    public static void main(String[] args) {
        Config config = Config.parceParams(args);
        FilesService filesService = new FilesService(config);
        filesService.execute();
    }
}
