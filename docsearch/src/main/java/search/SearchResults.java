package search;

public class SearchResults {
    private int count;
    private long time;

    public int getCount() {
        return count;
    }

    public long getTime() {
        return time;
    }

    public SearchResults(int count, long time){
        this.count = count;
        this.time = time;
    }

}
