package utils;

public class Par {
    private Object first;
    private Object second;

    public Par(Object first, Object second) {
        this.first = first;
        this.second = second;
    }

    public Object getFirst() {
        return first;
    }

    public Object getSecond() {
        return second;
    }

    public void setFirst(Object first) {
        this.first = first;
    }

    public void setSecond(Object second){
        this.second = second;
    }
}
