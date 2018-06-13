package impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class IndexedSearchMethod implements SearchMethod {

    private HashMap<File, Map<String, Integer>> cache = new HashMap<>();

    @Override
    public int searchDocument(File file, String text) throws IOException {
        if (!files.containsKey(file)) {
            files.put(file, readFile(file));
        }
        //populate cache for file if we havent already
        String fileContent = files.get(file);
        if (!cache.containsKey(file)) {
            populateCache(file, fileContent);
        }
        Map<String, Integer> index = cache.get(file);
        //if the term isnt found in the cache, return 0
        return (index.containsKey(text) ? index.get(text) : 0);

    }

    private void populateCache(File file, String fileContents) {
        Map<String, Integer> instance = new HashMap<>();
        String[] split = fileContents.split("[^a-zA-Z0-9]+");
        //split the file text into words and count instances as we iterate caching the result
        for (String s : split) {
            s = s.toLowerCase();
            if (s.length() > 0) {
                if (instance.containsKey(s)) {
                    instance.put(s, instance.get(s) + 1);
                } else {
                    instance.put(s, 1);
                }
            }
        }
        cache.put(file, instance);
    }
}
