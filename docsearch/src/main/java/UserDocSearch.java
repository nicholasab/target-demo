import impl.SearchMethod;
import search.SearchMethodEnum;

import java.io.File;
import java.util.HashMap;
import java.util.Scanner;

public class UserDocSearch {

    public static void main(String[] args) throws Exception {

        //Only accept directories that are valid and not empty
        File dir;
        if (args.length < 1 || !(dir = new File(args[0])).exists() || !dir.isDirectory()) {
            System.out.println("Pass a valid directory as an argument, exiting");
            return;
        }
        File[] files = dir.listFiles();
        if (files.length < 1) {
            System.out.println("Directory empty, exiting");
            return;
        }

        //Scan user input accepting search methods inside our range
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the search term:");
        String term = scanner.next();
        int methodInt = 0;
        do {
            System.out.println("Search Method: 1) String Match 2) Regular Expression 3) Indexed");
            methodInt = scanner.nextInt();
        }
        while (!(methodInt > 0 && methodInt < 4));

        //Create an instance of the search method requested
        SearchMethodEnum methodEnum = SearchMethodEnum.getById(methodInt);
        SearchMethod method = methodEnum.getInstance();
        HashMap<File, Integer> resultMap = new HashMap<>();

        long start = System.currentTimeMillis();
        for (File file : files) {
            int results = method.searchDocument(file, term.toLowerCase());
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
