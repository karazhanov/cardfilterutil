package org.karazhanov.config;

import org.karazhanov.exceptions.WrongParamException;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author karazhanov on 23.01.19.
 */
public class Config {
    private String src = null;
    private String dest = null;
    private int threadCount = -1;

    private Config() {}

    private static void readParam(String arg, Config conf) {
        String[] param = arg.split("=");
        if(param.length != 2) {
            throw new WrongParamException("Wrong params format = " + arg);
        }
        switch (param[0].toLowerCase()) {
            case "src":
                conf.src = param[1];
                break;
            case "dest":
                conf.dest = param[1];
                break;
            case "thread":
                conf.threadCount = Integer.parseInt(param[1]);
                break;
            default:
                throw new WrongParamException("Unsupported param = " + arg + "\n Support only \"src\", \"dest\", \"thread\" params.");
        }
    }

    private void validate() {
        if(threadCount < 1) {
            throw new WrongParamException("Wrong thread count = " + threadCount);
        }
        if(src == null) {
            throw new WrongParamException("Not set parameter \"src\"");
        }
        if(dest == null) {
            throw new WrongParamException("Not set parameter \"dest\"");
        }
        String separator = Character.toString(File.separatorChar);
        if(src.replace(separator, "").equalsIgnoreCase(dest.replace(separator, ""))) {
            throw new WrongParamException("Source directory and destination directory can't be equal.");
        }
        Path path = Paths.get(dest);
        if (Files.notExists(path)) {
            throw new WrongParamException("Destination directory not exist.");
        }
    }

    public static Config parceParams(String[] args) {
        if(args.length != 3) {
            throw new WrongParamException("Set all and only \"src\", \"dest\", \"thread\" params.\n Example: src=/path/sourcedir dest=/path/destinationdir thread=2");
        }
        Config conf = new Config();
        for (String arg : args) {
            readParam(arg, conf);
        }
        conf.validate();
        return conf;
    }

    public String getSrc() {
        return src;
    }

    public String getDest() {
        return dest;
    }

    public int getThreadCount() {
        return threadCount;
    }
}
