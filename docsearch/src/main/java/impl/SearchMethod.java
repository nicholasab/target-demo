package impl;

import search.SearchResults;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

public interface SearchMethod {

    HashMap<File, String> files = new HashMap<>();

    SearchResults searchDocument(File file, String text) throws IOException;

    default String readFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }
}
