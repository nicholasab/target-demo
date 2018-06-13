package impl;

import java.io.File;
import java.io.IOException;

public class StringMatchSearchMethod implements SearchMethod {

    @Override
    public int searchDocument(File file, String text) throws IOException {
        if (!files.containsKey(file)) {
            files.put(file, readFile(file));
        }
        String fileString = files.get(file);
        int count = 0;

        //find instances of search term incrementally across the file contents
        for (int i = 0; i < fileString.length(); i += text.length()) {
            i = fileString.indexOf(text, i);
            //break when we reach the end of the file
            if (i == -1) {
                break;
            }
            count++;
        }
        return count;
    }
}
