import impl.SearchMethod;
import search.SearchMethodEnum;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class UserDocSearch {

    public static void main(String[] args) throws Exception {
        URL fileFolder = UserDocSearch.class.getClassLoader().getResource("textexamples");
        File[] files = new File(fileFolder.getFile()).listFiles();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the search term:");
        String term = scanner.next();
        int methodInt = 0;
        do {
            System.out.println("Search Method: 1) String Match 2) Regular Expression 3) Indexed");
            methodInt = scanner.nextInt();
        }
        while (!(methodInt > 0 && methodInt < 4));

        SearchMethodEnum methodEnum = SearchMethodEnum.getById(methodInt);
        SearchMethod method = methodEnum.getInstance();
        HashMap<File, Integer> resultMap = new HashMap<>();
        long start = System.currentTimeMillis();
        for (File file : files) {
            int results = method.searchDocument(file, term);
            resultMap.put(file, results);
        }
        long end = System.currentTimeMillis();
        System.out.println("Search results:");
        for (File file : files) {
            System.out.println(String.format("\t%s - %d matches", file.getName(), resultMap.get(file)));
        }
        System.out.println(String.format("Elapsed time: %d ms", end - start));

    }
}
