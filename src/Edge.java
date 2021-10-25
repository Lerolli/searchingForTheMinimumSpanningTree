
import java.util.Comparator;
import java.util.HashMap;


public class Edge implements Comparable, Comparator {
    public int number;
    public Edge ancestor;
    public HashMap<Edge, Integer> neighbor;
    public int compareElement;

    @Override
    public int compare(Object o1, Object o2) {
        if (((Edge) o1).compareElement < ((Edge) o2).compareElement) {
            return 1;
        }
        if (((Edge) o1).compareElement > ((Edge) o2).compareElement) {
            return -1;
        }
        return 0;
    }
    @Override
    public int compareTo(Object o) {
        var edge = (Edge) o;
        if (edge.compareElement < compareElement)
            return 1;
        if (edge.compareElement > compareElement)
            return -1;
        else return 0;
    }
}

