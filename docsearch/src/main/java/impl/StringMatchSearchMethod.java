package impl;

import search.SearchResults;

import java.io.File;
import java.io.IOException;

public class StringMatchSearchMethod implements SearchMethod {

    @Override
    public SearchResults searchDocument(File file, String text) throws IOException {
        long start = System.currentTimeMillis();
        if (!files.containsKey(file)) {
            files.put(file, readFile(file).toLowerCase());
        }
        text = text.toLowerCase();
        String fileString = files.get(file);
        int count = 0;
        for (int i = 0; i < fileString.length(); i += text.length()) {
            i = fileString.indexOf(text, i);
            if (i == -1) {
                break;
            }
            count++;
        }
        long end = System.currentTimeMillis();
        return new SearchResults(count, end - start);
    }
}
