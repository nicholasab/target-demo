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

    Random r = new Random();
    private ArrayList<String> wordList;
    private File[] files;
    @Test
    public void testPerformance() throws IOException {
        System.out.println("** Beginning Performance Test **");
        URL file = PerformanceDocSearch.class.getClassLoader().getResource("textexamples");
        files = new File(file.getFile()).listFiles();
        wordList = generateWordList(files);

        HashMap<SearchMethodEnum, Long> resultTime = new HashMap<>();
        for (SearchMethodEnum s : SearchMethodEnum.values()) {
            SearchMethod search = s.getInstance();

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
    private String randomWord(){
        return wordList.get(r.nextInt(wordList.size()));
    }
    private File randomFile(){
        return files[r.nextInt(files.length)];
    }
    private static ArrayList<String> generateWordList(File[] files) throws IOException {
        ArrayList<String> words = new ArrayList<>();
        for(File file : files){
            String fileContents = new String(Files.readAllBytes(file.toPath()));
            String[] split = fileContents.split("[^a-zA-Z0-9]+");
            for (String s : split) {
                if(s.length() > 0 && !words.contains(s)){
                    words.add(s);
                }
            }
        }
        return words;
    }
}
