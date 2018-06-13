import impl.SearchMethod;
import org.junit.Test;
import search.SearchMethodEnum;
import search.SearchResults;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PerformanceDocSearch {

    private final long testSize = 2000000;

    @Test
    public void testPerformance() throws IOException {
        System.out.println("********************************");
        System.out.println("** Beginning Performance Test **");
        System.out.println("********************************");
        URL file = PerformanceDocSearch.class.getClassLoader().getResource("textexamples");
        File[] files = new File(file.getFile()).listFiles();


        for (SearchMethodEnum s : SearchMethodEnum.values()) {

            SearchMethod search = s.getInstance();
            long elapsedTime = 0;

            for (int i = 0; i < testSize; i++) {
                SearchResults result = search.searchDocument(files[0], "the");
                elapsedTime += result.getTime();
            }
            System.out.println(String.format("%s method in %s", s.getName(), elapsedTime));
        }
    }
}
