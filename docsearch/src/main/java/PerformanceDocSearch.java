import impl.SearchMethod;
import search.SearchMethodEnum;
import search.SearchResults;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PerformanceDocSearch {

    public static void main(String [] args) throws IOException {
        URL file = PerformanceDocSearch.class.getClassLoader().getResource("textexamples");
        File[] files = new File(file.getFile()).listFiles();


        for(SearchMethodEnum s : SearchMethodEnum.values()){
            SearchMethod search = s.getInstance();
            SearchResults result = search.searchDocument(files[0],"the");
            System.out.println(String.format("%s method %s in %s",s.getName(),result.getCount(),result.getTime()));
        }
    }
}
