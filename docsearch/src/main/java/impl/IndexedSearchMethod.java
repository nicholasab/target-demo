package impl;

import search.SearchResults;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IndexedSearchMethod implements SearchMethod {

    private HashMap<File, Map<String, Integer>> cache = new HashMap<>();

    @Override
    public SearchResults searchDocument(File file, String text) throws IOException {
        long start = System.currentTimeMillis();
        if (!files.containsKey(file)) {
            files.put(file, readFile(file).toLowerCase());
        }
        String fileContent = files.get(file);
        if (!cache.containsKey(file)) {
            populateCache(file, fileContent);
        }
        Map<String, Integer> index = cache.get(file);
        int count = index.containsKey(text.toLowerCase()) ? index.get(text.toLowerCase()) : 0;
        long end = System.currentTimeMillis();
        return new SearchResults(count, end - start);
    }

    private void populateCache(File file, String fileContents) {
        Map<String, Integer> instance = new HashMap<>();
        String[] split = fileContents.split("[\\.\\,\\s+]");
        for (String s : split) {
            if (instance.containsKey(s)) {
                instance.put(s, instance.get(s) + 1);
            } else {
                instance.put(s, 1);
            }
        }
        cache.put(file, instance);
    }
}
