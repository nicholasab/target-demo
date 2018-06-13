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
            files.put(file, readFile(file).toLowerCase());
        }
        String fileContent = files.get(file);
        if (!cache.containsKey(file)) {
            populateCache(file, fileContent);
        }
        Map<String, Integer> index = cache.get(file);
        int count = index.containsKey(text.toLowerCase()) ? index.get(text.toLowerCase()) : 0;
        return count;
    }

    private void populateCache(File file, String fileContents) {
        Map<String, Integer> instance = new HashMap<>();
        String[] split = fileContents.split("[^a-zA-Z0-9]+");
        for (String s : split) {
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
