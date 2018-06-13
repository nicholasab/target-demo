package impl;

import java.io.File;
import java.io.IOException;

public class StringMatchSearchMethod implements SearchMethod {

    @Override
    public int searchDocument(File file, String text) throws IOException {
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
        return count;
    }
}
