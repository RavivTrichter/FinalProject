import java.util.ArrayList;

public class Graph {

    private double[][] graph;

    private ArrayList<Vertex> Vertices;


    public Graph(ArrayList<Vertex> Vertices){
        this.Vertices = new ArrayList<Vertex>(Vertices);
        graph = new double[Vertices.size()][Vertices.size()];
        initGraph();
    }

    public ArrayList<Vertex> getVertices() {
        return Vertices;
    }



    public double[][] getGraph() {
        return graph;
    }


    public double distance(Vertex v1, Vertex v2){
        double deltaX = v1.getX() - v2.getX();
        double deltaY = v1.getY() - v2.getY();
        return Math.sqrt( deltaX * deltaX  +  deltaY * deltaY );
    }

    private void initGraph(){
        for (int i = 0; i < Vertices.size(); i++) {
            for (int j = i; j < Vertices.size(); j++) {
                double weight = distance(Vertices.get(i),Vertices.get(j));
                graph[i][j] = graph[j][i] = weight;
            }
        }
    }


    public void printGraph(){
        for (int i = 0; i < Vertices.size(); i++) {
            for (int j = 0; j < Vertices.size(); j++)
                System.out.printf("%.2f\t",graph[i][j]);
            System.out.println();
        }
    }


}
