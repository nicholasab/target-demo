package search;

import impl.IndexedSearchMethod;
import impl.RegexSearchMethod;
import impl.SearchMethod;
import impl.StringMatchSearchMethod;

import java.util.function.Supplier;

public enum SearchMethodEnum {
    STRING("String Search",StringMatchSearchMethod::new),
    REGEX("Regex Search",RegexSearchMethod::new),
    INDEX("Indexed Search",IndexedSearchMethod::new);

    private Supplier<? extends SearchMethod> method;
    private String name;
    SearchMethodEnum(String name,Supplier<? extends SearchMethod> method){
        this.name = name;
        this.method = method;
    }

    public String getName(){
        return name;
    }
    public SearchMethod getInstance() {
        return method.get();
    }
}
