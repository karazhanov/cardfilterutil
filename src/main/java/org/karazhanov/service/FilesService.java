package org.karazhanov.service;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.schedulers.Schedulers;
import org.karazhanov.config.Config;
import org.karazhanov.exceptions.WrongParamException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @author karazhanov on 23.01.19.
 */
public class FilesService {
    private final Config config;
    private final ExecutorService threadPool;

    public FilesService(Config config) {
        this.config = config;
        this.threadPool = Executors.newFixedThreadPool(config.getThreadCount());
    }

    public void execute() {
        Flowable.create(this::readFiles, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .parallel()
                .runOn(Schedulers.from(threadPool))
                .map(this::process)
                .sequential()
                .subscribe(
                        convert -> {
                        },
                        Throwable::printStackTrace,
                        () -> {
                            System.out.println("END");
                            System.exit(0);
                        });
    }

    private void readFiles(FlowableEmitter<Path> emitter) {
        Path start = Paths.get(config.getSrc());
        try (Stream<Path> paths = Files.walk(start)) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(emitter::onNext);
            emitter.onComplete();
        } catch (IOException e) {
            emitter.onError(new WrongParamException("Something wrong with \"src\" directory", e));
        }
    }

    private boolean process(Path path) {
        FileProcessor fileProcessor = new FileProcessor(path, config.getDest());
        return fileProcessor.execute();
    }

}
