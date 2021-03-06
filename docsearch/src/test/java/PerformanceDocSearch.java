import impl.SearchMethod;
import org.junit.Test;
import search.SearchMethodEnum;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class PerformanceDocSearch {

    private final long testSize = 2000000;

    private Random r = new Random();
    private ArrayList<String> wordList;
    private File[] files;

    @Test
    public void testPerformance() throws IOException {
        System.out.println("** Beginning Performance Test **");
        //use local resources rather than input directory
        URL file = PerformanceDocSearch.class.getClassLoader().getResource("textexamples");
        files = new File(file.getFile()).listFiles();
        wordList = generateWordList(files);


        HashMap<SearchMethodEnum, Long> resultTime = new HashMap<>();
        for (SearchMethodEnum s : SearchMethodEnum.values()) {
            SearchMethod search = s.getInstance();

            //Capture length of time between starting and stopping search by method
            long start = System.currentTimeMillis();
            System.out.println(String.format("Beginning %s Search", s.getName()));
            for (int i = 0; i < testSize; i++) {
                search.searchDocument(randomFile(), randomWord());
            }
            long end = System.currentTimeMillis();
            resultTime.put(s, end - start);
        }

        System.out.println(String.format("\nResults for %d searches:", testSize));
        for (SearchMethodEnum s : SearchMethodEnum.values()) {
            System.out.println(String.format("\t%s Search - %d ms", s.getName(), resultTime.get(s)));
        }
    }

    //randomly generate a word to search for
    private String randomWord() {
        return wordList.get(r.nextInt(wordList.size()));
    }

    //randomly generate a file to scan
    private File randomFile() {
        return files[r.nextInt(files.length)];
    }

    //Build an arraylist of all words from all files to randomly pick one during testing
    private static ArrayList<String> generateWordList(File[] files) throws IOException {
        ArrayList<String> words = new ArrayList<>();
        for (File file : files) {
            String fileContents = new String(Files.readAllBytes(file.toPath()));
            String[] split = fileContents.split("[^a-zA-Z0-9]+");
            for (String s : split) {
                s = s.toLowerCase();
                if (s.length() > 0 && !words.contains(s)) {
                    words.add(s);
                }
            }
        }
        return words;
    }
}
