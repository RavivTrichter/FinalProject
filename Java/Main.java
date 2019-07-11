import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {


    public static void main(String[] args) {

        try {
            int m = Integer.parseInt(args[0]),  k = Integer.parseInt(args[1]) ,i_ = Integer.parseInt(args[2]);

            long start, finish, timeElapsed;
            String fileG1 = "G1Route.dat", // file for the first Graph
                    fileG2 = "G2Route.dat", // file for second Graph
                    fileG3 = "G3Route.dat",  // file for third Graph
                    dataFile = "Data.dat";



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

            writer = new PrintWriter(dataFile);
            writer.print("");
            writer.close();



            Vertices vertices = new Vertices("src/hachula130.dat"); // parsing the Vertices (Euclidean Distance)


            System.out.println("\n\n\n\nCalculating first TSP\n");
            ArrayList<Node> G1Vertices = vertices.createRandomSubGraph(m - 1); // because we always add the first node
            Graph G1 = new Graph(G1Vertices); // create the sub-graph ==> the deliveryman's first route
            TSP tspG1 = new TSP(G1.getGraph(), G1.getVertices().size());
            start = System.currentTimeMillis();
            tspG1.solve();
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            tspG1.printRouteToFileByIndices(G1Vertices, fileG1, timeElapsed);

            System.out.println("\n\n\n\nCalculating second TSP\n");

            ArrayList<Node> G2Vertices = vertices.createRandomSubGraph(k-1);
            Graph G2 = new Graph(G2Vertices);
            TSP tspG2 = new TSP(G2.getGraph(),G2.getVertices().size());
            start = System.currentTimeMillis();
            tspG2.solve();
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            tspG2.printRouteToFileByIndices(G2Vertices, fileG2, timeElapsed);



            //G1Vertices.remove(tspG1.getFirstIdx() ); // removing the first point the deliveryman visited --> exactly the jth column in the
            // graph as we built the graph by the order of the vertices in G1Vertices
            double weightOfEdgesVisited = 0;

            for (int i = 1; i <= i_; i++) {
                int fromIndexInRoute = tspG1.getRoute().get((i-1));
                Node From = G1Vertices.get(fromIndexInRoute);
                int toIndexInRoute = tspG1.getRoute().get(i);
                Node To = G1Vertices.get(toIndexInRoute);
                weightOfEdgesVisited += G1.distance(From, To);
            }


            double FirstOption = tspG1.getObjectiveValue() + tspG2.getObjectiveValue() - weightOfEdgesVisited;

            int fromIndexInRoute = tspG1.getRoute().get((i_));
            Node vertexFrom = G1Vertices.get(fromIndexInRoute);
            int toIndexInRoute = tspG1.getRoute().get(0);
            Node vertexTo = G1Vertices.get(toIndexInRoute);
            double weightReturnHome = G1.distance(vertexFrom, vertexTo);

            ArrayList<Node> removeVertices = new ArrayList<Node>(); // contains k vertices from the start that we
            // are going to remove
            for (int i = 1; i <= i_; i++)
                removeVertices.add(G1Vertices.get(tspG1.getRoute().get(i)));


            /* Erasing the vertices that were visited in the firs route */
            for (int i = 0; i < removeVertices.size(); i++) {
                for (int j = 0; j < G1Vertices.size(); j++) {
                    if (removeVertices.get(i).getIndex() == G1Vertices.get(j).getIndex()) {
                        G1Vertices.remove(j);
                        break;
                    }
                }
            }


            G2Vertices.remove(0); // removing the restaurant because it is a duplicate
            G1Vertices.addAll(G2Vertices); // G1Vertics === G3Vertices


            System.out.println("\n\n\n\nCalculating third TSP\n");



            Graph G3 = new Graph(G1Vertices);
            TSP tspG3 = new TSP(G3.getGraph(), G3.getVertices().size());
            start = System.currentTimeMillis();
            tspG3.solve();
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            tspG3.printRouteToFileByIndices(G3.getVertices(), fileG3, timeElapsed);

            double SecondOption = tspG3.getObjectiveValue() + weightReturnHome;



            writer = new PrintWriter(dataFile);
            writer.print("First Option : " + FirstOption);
            writer.print("\nSecond Option : " + SecondOption );
            writer.print("\nEdges That Were Visited : " + weightOfEdgesVisited);
            writer.print("\nEdge For Returning Home : " + weightReturnHome + "\n");
            writer.close();


        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}
