package impl;

import search.SearchResults;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class StringMatchSearchMethod implements SearchMethod {

    @Override
    public SearchResults searchDocument(File file, String text) throws IOException {
        if (!files.containsKey(file)) {
            files.put(file, readFile(file).toLowerCase());
        }
        text = text.toLowerCase();
        String fileString = files.get(file);
        long start = System.currentTimeMillis();
        int count = 0;
        for(int idx = 0;idx<fileString.length();idx+=text.length()) {
            idx = fileString.indexOf(text,idx);
            if(idx == -1){
                break;
            }
            count ++;
        }
        long end = System.currentTimeMillis();
        return new SearchResults(count,end-start);
    }
}
