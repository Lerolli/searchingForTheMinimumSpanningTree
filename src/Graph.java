import java.util.*;

public class Graph {
    int size;
    ArrayList<Edge> edges;
    int[][] matrix;
    public Graph(int size){
        this.size = size;
        edges = new ArrayList<>();
        for (int i = 0; i < size; i++){
            var edge = new Edge();
            edge.number = i;
            edge.neighbor = new HashMap<>();
            edges.add(edge);
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
                edges.get(array[prevIndex] - 1).compareElement = array[prevIndex + 1];
                prevIndex += 2;
            }
        }
    }

    public String findMinimumSpanningTree() {
       var minTree = new Graph(size);
       var arraySpanningTree = new int[size];
       for (var i = 0; i < size; i++)
           arraySpanningTree[i] = Integer.MAX_VALUE;

       arraySpanningTree[0] = 0;
       edges.get(0).compareElement = Integer.MIN_VALUE;
       var queue = new PriorityQueue<Edge>();
       for (var edge: edges) {
           queue.add(edge);
       }
       while (!queue.isEmpty()){
           var v = queue.poll();
           for (var edgeValue : v.neighbor.entrySet()){
               if (queue.contains(edgeValue.getKey()) &&
                       arraySpanningTree[edgeValue.getKey().number] > arraySpanningTree[v.number] + edgeValue.getValue()){

                   arraySpanningTree[edgeValue.getKey().number] = arraySpanningTree[v.number] + edgeValue.getValue();
                   edgeValue.getKey().compareElement = edgeValue.getValue();
                   if (minTree.edges.get(edgeValue.getKey().number).ancestor != null){
                       minTree.edges.get(edgeValue.getKey().number).ancestor.neighbor.remove(edgeValue.getKey());
                   }

                   var tempEdge = minTree.edges.get(v.number);
                   tempEdge.neighbor.put(edgeValue.getKey(), edgeValue.getValue());
                   minTree.edges.get(edgeValue.getKey().number).ancestor = tempEdge;

               }
           }
           var tempQueue = new PriorityQueue<Edge>();
           for(var element: queue){
               tempQueue.add(element);
           }
           queue = tempQueue;
       }
       return minTree.getAdjacencyList();
    }

    private String getAdjacencyList() {
        var sizeAllEdges = 0;
        var stringBuilder = new StringBuilder();
        for (var edge : edges){
            for (var edgeNeighbor : edge.neighbor.entrySet()){
                sizeAllEdges += edgeNeighbor.getValue();
                stringBuilder.append(edgeNeighbor.getKey().number + 1 + " " + edgeNeighbor.getValue() + " ");
            }
            stringBuilder.append("0\r\n");
        }
        stringBuilder.append(sizeAllEdges);

        return String.valueOf(stringBuilder);
    }
}
