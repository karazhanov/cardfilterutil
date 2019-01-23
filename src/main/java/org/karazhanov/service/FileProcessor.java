package org.karazhanov.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author karazhanov on 23.01.19.
 */
public class FileProcessor {

    private final Path sourceFile;
    private final String dest;
    private final CardValidator cardValidator = new CardValidator();

    public FileProcessor(Path sourceFile, String dest) {
        this.sourceFile = sourceFile;
        this.dest = dest;
    }

    public boolean execute() {
        if(Files.exists(sourceFile)){
            try{
                List<String> validCsvLines = Files.lines(sourceFile)
                        .filter(cardValidator::containValidCardNumber)
                        .collect(Collectors.toList());
                if(!validCsvLines.isEmpty()) {
                    save(validCsvLines);
                }
            } catch (FileNotFoundException e) {
                System.err.println("Something went wrong save data to " + dest);
            } catch (Exception e){
                System.err.println("Something went wrong while processing " + sourceFile);
            }
        }
        return false;
    }

    private void save(List<String> validCsvLines) throws FileNotFoundException {
        File destFile = new File(dest, sourceFile.getFileName().toString());
        System.out.println("SAVE DATA TO " + destFile);
        PrintWriter pw = new PrintWriter(new FileOutputStream(destFile));
        validCsvLines.forEach(pw::println);
        pw.close();
    }
}
