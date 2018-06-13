package search;

import impl.IndexedSearchMethod;
import impl.RegexSearchMethod;
import impl.SearchMethod;
import impl.StringMatchSearchMethod;

import java.util.function.Supplier;

/**
 * enum of all search methods
 * provides readable name, and supplier
 */
public enum SearchMethodEnum {
    STRING(1, "String Match", StringMatchSearchMethod::new),
    REGEX(2, "Regex", RegexSearchMethod::new),
    INDEX(3, "Indexed", IndexedSearchMethod::new);

    private Supplier<? extends SearchMethod> method;
    private String name;
    private int id;

    SearchMethodEnum(int id, String name, Supplier<? extends SearchMethod> method) {
        this.id = id;
        this.name = name;
        this.method = method;
    }

    public static SearchMethodEnum getById(int id) throws Exception {
        for (SearchMethodEnum e : values()) {
            if (e.getId() == id) {
                return e;
            }
        }
        throw new Exception("Invalid search method requested");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public SearchMethod getInstance() {
        return method.get();
    }
}
