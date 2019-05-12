import java.io.PrintWriter;
import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {


        try {

            // CLEAR THE .DAT Files
            PrintWriter writer = new PrintWriter("TSPG1.dat");
            writer.print("");
            writer.close();

            PrintWriter writer2 = new PrintWriter("TSPG2.dat");
            writer.print("");
            writer.close();

            PrintWriter writer3 = new PrintWriter("TSPG3.dat");
            writer.print("");
            writer.close();


            // First Option better - 6,2
            // Second Option better - 20,9
            int firstSize = 9;
            int pointsToAdd = 20;



            Vertices vertices = new Vertices("src/hachula130.dat");


            System.out.println("\n\n\n\nCalculating first TSP\n");
            ArrayList<Vertex> G1Vertices = vertices.createSubGraph(firstSize,  0);
            Graph G1 = new Graph(G1Vertices);
            TSP tspG1 = new TSP(G1.getGraph(), G1.getVertices().size(), "TSPG1.dat");
            tspG1.solve();
            Thread.sleep(5000); // waiting for the deliveryman to get to his first stop

            System.out.println("\n\n\n\nCalculating second TSP\n");

            ArrayList<Vertex> G2Vertices = vertices.createSubGraph(pointsToAdd,  1);
            Graph G2 = new Graph(G2Vertices);
            TSP tspG2 = new TSP(G2.getGraph(),G2.getVertices().size(),"TSPG2.dat");
            tspG2.solve();


            System.out.println("\n\nObjective value for G1 : " + tspG1.getObjectiveValue() + "\t" + "\nObjective value for G2 : " + tspG2.getObjectiveValue());
            System.out.println(tspG1.getFirstEdge());

            double FirstOption = tspG1.getObjectiveValue() + tspG2.getObjectiveValue() - tspG1.getFirstEdge();
            G1Vertices.remove(tspG1.getIdx() - 1); // removing the first point the deliveryman visited
            G1Vertices.remove(0); // removing the restaurant because it is a duplicate
            G1Vertices.addAll(G2Vertices);

            Graph G3 = new Graph(G1Vertices);
            TSP tspG3 = new TSP(G3.getGraph(), G3.getVertices().size(), "TSPG3.dat");
            tspG3.solve();
            double SecondOption = tspG3.getObjectiveValue() + tspG1.getFirstEdge();

            System.out.println("\n\n\n\nFirst Option : " + FirstOption);
            System.out.println("Second Option : " + SecondOption);


//            tspG1.printRoute();
//            System.out.println("\n\n\n");
//            tspG2.printRoute();
//            System.out.println("\n\n\n");
//            tspG3.printRoute();
//            System.out.println("\n\n\n\n\n\n\n");
//            tspG1.printGraph();


            System.out.println("\n\n\n\n\n\n\n");
            System.out.println("TSP 1 : " + tspG1.getObjectiveValue() + "\nFirst Edge TSP1 : " + tspG1.getFirstEdge() + "  Index : " + tspG1.getIdx());
            System.out.println("TSP 2 : " + tspG2.getObjectiveValue());
            System.out.println("TSP 3 : " + tspG3.getObjectiveValue());

        } catch(Exception e){
            e.printStackTrace();
            System.err.println("Exception Caught\nAborting...");
        }


    }
}
