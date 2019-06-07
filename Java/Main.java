import ilog.concert.IloException;

import java.io.PrintWriter;
import java.util.ArrayList;

//import java.io.PrintWriter;
//import java.util.ArrayList;
//import java.util.concurrent.TimeUnit;
//
//
public class Main {
//
    public static void main(String[] args) {

        try {
            // First Option better - 6,2
            // Second Option better - 20,9
            int sizeOfFirstRoute = 10,  pointsToAdd = 3;

            long start, finish, timeElapsed;
            String fileG1 = "/home/raviv/PycharmProjects/Project/GUI/G1Route.dat", // file for the first Graph
                    fileG2 = "/home/raviv/PycharmProjects/Project/GUI/G2Route.dat", // file for second Graph
                    fileG3 = "/home/raviv/PycharmProjects/Project/GUI/G3Route.dat",  // file for third Graph
                    dataFile = "/home/raviv/PycharmProjects/Project/GUI/Data.dat";



            // CLEAR THE .DAT Files
            PrintWriter writer = new PrintWriter(fileG1);
            writer.print("");
            writer.close();

            writer = new PrintWriter(fileG2);
            writer.print("");
            writer.close();

            writer = new PrintWriter(fileG3);
            writer.print("");
            writer.close();



            Vertices vertices = new Vertices("src/hachula130.dat"); // parsing the Vertices (Euclidean Distance)


            System.out.println("\n\n\n\nCalculating first TSP\n");
            ArrayList<Vertex> G1Vertices = vertices.createRandomSubGraph(sizeOfFirstRoute - 1); // because we always add the first node
            Graph G1 = new Graph(G1Vertices); // create the sub-graph ==> the deliveryman's first route
            TSP tspG1 = new TSP(G1.getGraph(), G1.getVertices().size());
            start = System.currentTimeMillis();
            tspG1.solve();
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            tspG1.printRouteToFileByIndices(G1Vertices, fileG1, timeElapsed);

            System.out.println("\n\n\n\nCalculating second TSP\n");

            ArrayList<Vertex> G2Vertices = vertices.createRandomSubGraph(pointsToAdd-1);
            Graph G2 = new Graph(G2Vertices);
            TSP tspG2 = new TSP(G2.getGraph(),G2.getVertices().size());
            start = System.currentTimeMillis();
            tspG2.solve();
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            tspG2.printRouteToFileByIndices(G2Vertices, fileG2, timeElapsed);


            double FirstOption = tspG1.getObjectiveValue() + tspG2.getObjectiveValue() - tspG1.getFirstEdge();
            G1Vertices.remove(tspG1.getFirstIdx() ); // removing the first point the deliveryman visited --> exactly the jth column in the
            // graph as we built the graph by the order of the vertices in G1Vertices

            G2Vertices.remove(0); // removing the restaurant because it is a duplicate
            G1Vertices.addAll(G2Vertices); // G1Vertics === G3Vertices -> all except first idx from G1Vertics


            System.out.println("\n\n\n\nCalculating third TSP\n");



            Graph G3 = new Graph(G1Vertices);
            TSP tspG3 = new TSP(G3.getGraph(), G3.getVertices().size());
            start = System.currentTimeMillis();
            tspG3.solve();
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            tspG3.printRouteToFileByIndices(G3.getVertices(), fileG3, timeElapsed);

            double SecondOption = tspG3.getObjectiveValue() + tspG1.getFirstEdge();



            // TODO ADD THE PRINT LOGGING TO THE DATA FILE
            writer = new PrintWriter(dataFile);
            writer.print("First Option : " + FirstOption);
            writer.print("\nSecond Option : " + SecondOption );
            writer.print("\nFirst edge : " + tspG1.getFirstEdge() + "\n");
            writer.close();


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
