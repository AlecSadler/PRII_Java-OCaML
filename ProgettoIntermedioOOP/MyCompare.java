import java.util.Comparator;

public class MyCompare<T extends Data> implements Comparator<T> {

    @Override
    public int compare(T a, T b) {
        return -a.compareTo(b);
    }
}
