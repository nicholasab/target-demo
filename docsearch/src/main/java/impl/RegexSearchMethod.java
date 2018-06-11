package impl;

import search.SearchResults;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSearchMethod implements SearchMethod {


    @Override
    public SearchResults searchDocument(File file, String text) throws IOException {
        if (!files.containsKey(file)) {
            files.put(file, readFile(file).toLowerCase());
        }
        text = text.toLowerCase();
        String fileString = files.get(file);
        Pattern pattern = Pattern.compile(String.format(" %s ",text));
        Matcher matcher = pattern.matcher(fileString);

        long start = System.currentTimeMillis();
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        long end = System.currentTimeMillis();
        return new SearchResults(count, end - start);
    }
}
