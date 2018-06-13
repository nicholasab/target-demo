import impl.SearchMethod;
import org.junit.Test;
import search.SearchMethodEnum;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class PerformanceDocSearch {

    private final long testSize = 2000000;

    @Test
    public void testPerformance() throws IOException {
        System.out.println("** Beginning Performance Test **");
        URL file = PerformanceDocSearch.class.getClassLoader().getResource("textexamples");
        File[] files = new File(file.getFile()).listFiles();

        HashMap<SearchMethodEnum, Long> resultTime = new HashMap<>();
        for (SearchMethodEnum s : SearchMethodEnum.values()) {
            SearchMethod search = s.getInstance();

            long start = System.currentTimeMillis();
            System.out.println(String.format("Beginning %s Search", s.getName()));
            for (int i = 0; i < testSize; i++) {
                //ignore results, calculate on total time
                search.searchDocument(files[0], "the");
            }
            long end = System.currentTimeMillis();
            resultTime.put(s, end - start);
        }

        System.out.println(String.format("\nResults for %d searches:", testSize));
        for (SearchMethodEnum s : SearchMethodEnum.values()) {
            System.out.println(String.format("\t%s Search - %d ms", s.getName(), resultTime.get(s)));
        }
    }
}
