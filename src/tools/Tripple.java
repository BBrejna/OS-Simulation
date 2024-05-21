package tools;

public class Tripple<T, E, F> {
    public T first;
    public E second;
    public F third;

    public Tripple(T first, E second, F third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ", " + third + ")";
    }
}
