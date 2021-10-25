import java.io.*;
import java.util.Scanner;

public class Program {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("input.txt"));
        int n = sc.nextInt();
        var array = new int[n -1];
        for(int i = 0; i < array.length; i++){
            array[i] = sc.nextInt();
        }
        Graph g = new Graph(array[0] - 2);
        g.MakeGraph(array);
        try(FileWriter writer = new FileWriter("result.txt", false))
        {
            String text =  g.findMinimumSpanningTree();;
            writer.write(text);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}