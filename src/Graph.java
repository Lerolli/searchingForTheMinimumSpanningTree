import java.util.*;

public class Graph {
    int size;
    ArrayList<Edge> edges;
    int[][] matrix;
    int minimumSpanningTree;
    public Graph(int size){
        this.size = size;
        edges = new ArrayList<>();
        for (int i = 0; i < size; i++){
            var edge = new Edge();
            edge.number = i;
            edge.numberToString = i + 1;
            edge.neighbor = new HashMap<>();
            edges.add(edge);
            edge.compareElement = Integer.MAX_VALUE;
        }
    }

    public void MakeGraph(int[] array){
        matrix = new int[size][size];

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++){
                matrix[i][j] = -1;
            }
        for (int i = 0; i < array[0] - 1; i++) {
            int prevIndex = array[i] - 1;
            int nextIndex = array[i + 1] - 1;
            while (prevIndex < nextIndex){
                matrix[i][array[prevIndex] - 1] = array[prevIndex + 1];
                edges.get(i).neighbor.put(edges.get(array[prevIndex] - 1), array[prevIndex + 1]);
                prevIndex += 2;
            }
        }
    }

    public String matrixToString(){
        var strbuilder = new StringBuilder();
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix.length;j++){
                strbuilder.append(matrix[i][j] + ",");
            }
            strbuilder.deleteCharAt(strbuilder.length() - 1);
            strbuilder.append("\r\n");
        }
        return strbuilder.toString();
    }

    public String findMinimumSpanningTree() {
       var minTree = new Graph(size);
       var arraySpanningTree = new int[size];
       var p = new int[size];
       for (var i = 0; i < size; i++)
           arraySpanningTree[i] = Integer.MAX_VALUE;

       arraySpanningTree[0] = 0;
       edges.get(0).compareElement = Integer.MIN_VALUE;
       var queue = new PriorityQueue<Edge>();
       for (var edge: edges) {
           queue.add(edge);
       }

       var v = queue.poll();
       while (!queue.isEmpty()){
           for (var edgeValue : v.neighbor.entrySet()){
               if (queue.contains(edgeValue.getKey()) &&
                       arraySpanningTree[edgeValue.getKey().number] > edgeValue.getValue()){
                   arraySpanningTree[edgeValue.getKey().number] = edgeValue.getValue();
                   p[edgeValue.getKey().number] = v.number;
                   edgeValue.getKey().compareElement = edgeValue.getValue();
               }
           }
           var tempQueue = new PriorityQueue<Edge>();

           for(var element: queue){
               tempQueue.add(element);
           }
           queue = tempQueue;
           v = queue.poll();
       }

        for (int j : arraySpanningTree) {
            minTree.minimumSpanningTree += j;
        }

       for (int i = 1; i < p.length; i++){
           minTree.edges.get(i).ancestor = edges.get(p[i]);
           if (!minTree.edges.get(i).neighbor.containsKey(minTree.edges.get(p[i]))){
               minTree.edges.get(i).neighbor.put(minTree.edges.get(p[i]), arraySpanningTree[i]);
           }
           minTree.edges.get(i).tempLength = arraySpanningTree[i];
           minTree.edges.get(p[i]).neighbor.put(minTree.edges.get(i), arraySpanningTree[i]);
       }
       return minTree.getAdjacencyList();
    }

    public String getAdjacencyList() {
        var stringBuilder = new StringBuilder();
        for (var edge : edges){
            for (var edgeNeighbor : edge.neighbor.entrySet()){

                stringBuilder.append(edgeNeighbor.getKey().number + 1 + " " + edgeNeighbor.getValue() + " ");
            }

            stringBuilder.append("0\r\n");
        }
        stringBuilder.append(minimumSpanningTree);

        return String.valueOf(stringBuilder);
    }
}
