package impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public interface SearchMethod {

    //static cache of files -> file contents
    HashMap<File, String> files = new HashMap<>();

    int searchDocument(File file, String text) throws IOException;

    //Read the provided file and return a constructed string
    default String readFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath())).toLowerCase();
    }
}
