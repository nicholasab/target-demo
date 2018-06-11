import impl.SearchMethod;
import search.SearchMethodEnum;

import java.io.File;
import java.net.URL;

public class UserDocSearch {

    public static void main(String [] args){
        URL file = UserDocSearch.class.getClassLoader().getResource("textexamples");
        File[] files = new File(file.getFile()).listFiles();


        for(SearchMethodEnum s : SearchMethodEnum.values()){
            SearchMethod search = s.getInstance();

        }
    }
}
