package util;

public class Pagination {
    private String cursor;
    public Pagination(String cursor){
        this.cursor = cursor;
    }

    public String getCursor() {
        return cursor;
    }
}
