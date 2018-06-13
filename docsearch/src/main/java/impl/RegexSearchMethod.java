package impl;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSearchMethod implements SearchMethod {

    @Override
    public int searchDocument(File file, String text) throws IOException {
        if (!files.containsKey(file)) {
            files.put(file, readFile(file));
        }
        String fileString = files.get(file);

        //compile a pattern based on the search term
        Pattern pattern = Pattern.compile(String.format("\\b%s\\b", text));
        Matcher matcher = pattern.matcher(fileString);

        int count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
