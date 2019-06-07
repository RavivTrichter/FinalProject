import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RandomGraphMain {


    public static void main(String[] args) {

        try{

            PrintWriter writer = new PrintWriter("/home/raviv/PycharmProjects/Project/GUI/TSPG1.dat");
            writer.print("");
            writer.close();

            writer = new PrintWriter("/home/raviv/PycharmProjects/Project/GUI/TSPG2.dat");
            writer.print("");
            writer.close();

            writer = new PrintWriter("/home/raviv/PycharmProjects/Project/GUI/TSPG3.dat");
            writer.print("");
            writer.close();

            writer = new PrintWriter("/home/raviv/PycharmProjects/Project/GUI/G1Route.dat");
            writer.print("");
            writer.close();


            writer = new PrintWriter("/home/raviv/PycharmProjects/Project/GUI/G2Route.dat");
            writer.print("");
            writer.close();


            writer = new PrintWriter("/home/raviv/PycharmProjects/Project/GUI/G3Route.dat");
            writer.print("");
            writer.close();




            // First Option better - 6,2
            // Second Option better - 20,9
            int firstSize = 12; // always plus one because the restaurant ==> n-1
            int pointsToAdd = 3;
            long start, finish, timeElapsed;

            Vertices vertices = new Vertices("src/hachula130.dat"); // parsing the Vertices (Euclidean Distance)



            System.out.println("\n\n\n\nCalculating first TSP\n");
            ArrayList<Vertex> G1Vertices = vertices.createRandomSubGraph(firstSize-1);
            Graph G1 = new Graph(G1Vertices); // create the sub-graph
            TSP tspG1 = new TSP(G1.getGraph(), G1.getVertices().size());
            start = System.currentTimeMillis();
            tspG1.solve();
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            tspG1.printRouteToFileByIndices(G1Vertices,"/home/raviv/PycharmProjects/Project/GUI/G1Route.dat", timeElapsed);


            System.out.println("\n\n\n\nCalculating second TSP\n");
            ArrayList<Vertex> G2Vertices = vertices.createRandomSubGraph(pointsToAdd-1);
            Graph G2 = new Graph(G2Vertices);
            TSP tspG2 = new TSP(G2.getGraph(),G2.getVertices().size());
            start = System.currentTimeMillis();
            tspG2.solve();
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            tspG2.printRouteToFileByIndices(G2Vertices, "/home/raviv/PycharmProjects/Project/GUI/G2Route.dat", timeElapsed);


            /*TODO Check that we really delete the first idx as in line 68*/
            double FirstOption = tspG1.getObjectiveValue() + tspG2.getObjectiveValue() - tspG1.getFirstEdge();


            G1Vertices.remove(tspG1.getFirstIdx()); // removing the first point the deliveryman visited
            G2Vertices.remove(0); // removing the restaurant because it is a duplicate
            G1Vertices.addAll(G2Vertices);



            System.out.println("\n\n\n\nCalculating third TSP\n");
            Graph G3 = new Graph(G1Vertices);
            TSP tspG3 = new TSP(G3.getGraph(), G3.getVertices().size());
            start = System.currentTimeMillis();
            tspG3.solve();
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            tspG3.printRouteToFileByIndices(G3.getVertices(),"/home/raviv/PycharmProjects/Project/GUI/G3Route.dat", timeElapsed);
            double SecondOption = tspG3.getObjectiveValue() + tspG1.getFirstEdge();


            System.out.println("\n\n\n\n\n\n" + "TSP 1 : " + tspG1.getObjectiveValue() + "\nTSP 2 : " + tspG2.getObjectiveValue()
                    + "\nTSP 3 : " + tspG3.getObjectiveValue());
            System.out.println("\n\n\n\nFirst Option : " + FirstOption);
            System.out.println("Second Option : " + SecondOption);

            writer = new PrintWriter("/home/raviv/PycharmProjects/Project/GUI/Data.dat");
            writer.print("");
            writer.close();
            writer = new PrintWriter("/home/raviv/PycharmProjects/Project/GUI/Data.dat");
            writer.print("First Option : TSP1 + TSP2 - TSP1_first_Edge   == ");
            writer.print(FirstOption);
            writer.print("\nTSP G1 Objective : ");
            writer.print(tspG1.getObjectiveValue());
            writer.print("\nTSP G2 Objective :");
            writer.print(tspG2.getObjectiveValue());
            writer.print("\nG1 first edge :  ");
            writer.print(tspG1.getFirstEdge());
            writer.print("\n\n\nSecond Option : TSP3 + TSP1_First_Edge  == ");
            writer.print(SecondOption);
            writer.print("\nTSP G3 Objective : ");
            writer.print(tspG3.getObjectiveValue());
            writer.print("\nG1 first edge :  ");
            writer.print(tspG1.getFirstEdge());
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}




//
//
//
//
//
//
//
//
//
//
//
///import java.io.IOException;
//        import java.io.PrintWriter;
//        import java.util.ArrayList;
//        import java.util.concurrent.TimeUnit;
//
//public class RandomGraphMain {
//
//
//    public static void main(String[] args) {
//
//        long start = System.currentTimeMillis();
//
//        try{
//
//            PrintWriter writer = new PrintWriter("TSPG1.dat");
//            writer.print("");
//            writer.close();
//
//            writer = new PrintWriter("TSPG2.dat");
//            writer.print("");
//            writer.close();
//
//            writer = new PrintWriter("TSPG3.dat");
//            writer.print("");
//            writer.close();
//
//            writer = new PrintWriter("G1Route.dat");
//            writer.print("");
//            writer.close();
//
//
//            writer = new PrintWriter("G2Route.dat");
//            writer.print("");
//            writer.close();
//
//
//            writer = new PrintWriter("G3Route.dat");
//            writer.print("");
//            writer.close();
//
//
//
//
//            // First Option better - 6,2
//            // Second Option better - 20,9
//            int firstSize = 25;
//            int pointsToAdd = 3;
//
//            Vertices vertices = new Vertices("src/hachula130.dat"); // parsing the Vertices (Euclidean Distance)
//
//
//
//            System.out.println("\n\n\n\nCalculating first TSP\n");
//            ArrayList<Vertex> G1Vertices = vertices.createRandomSubGraph(firstSize);
//            Graph G1 = new Graph(G1Vertices); // create the sub-graph
//            TSP tspG1 = new TSP(G1.getGraph(), G1.getVertices().size(), "TSPG1.dat");
//            tspG1.solve();
//            tspG1.printRouteToFileByIndices(G1Vertices,"G1Route.dat");
//
//
//
//            System.out.println("\n\n\n\nCalculating second TSP\n");
//            ArrayList<Vertex> G2Vertices = vertices.createRandomSubGraph(pointsToAdd);
//            Graph G2 = new Graph(G2Vertices);
//            TSP tspG2 = new TSP(G2.getGraph(),G2.getVertices().size(),"TSPG2.dat");
//            tspG2.solve();
//            tspG2.printRouteToFileByIndices(G2Vertices, "G2Route.dat");
//
//
//            /*TODO Check that we really delete the first idx as in line 68*/
//            double FirstOption = tspG1.getObjectiveValue() + tspG2.getObjectiveValue() - tspG1.getFirstEdge();
//
//            G1Vertices.remove(tspG1.getFirstIdx() - 1); // removing the first point the deliveryman visited
//            G2Vertices.remove(0); // removing the restaurant because it is a duplicate
//            G1Vertices.addAll(G2Vertices);
//
//
//
//            System.out.println("\n\n\n\nCalculating third TSP\n");
//            Graph G3 = new Graph(G1Vertices);
//            TSP tspG3 = new TSP(G3.getGraph(), G3.getVertices().size(), "TSPG3.dat");
//            tspG3.solve();
//            tspG3.printRouteToFileByIndices(G3.getVertices(),"G3Route.dat");
//            double SecondOption = tspG3.getObjectiveValue() + tspG1.getFirstEdge();
//
//            System.out.println("\n\n\n\n\n\n" + "TSP 1 : " + tspG1.getObjectiveValue() + "\nTSP 2 : " + tspG2.getObjectiveValue()
//                    + "\nTSP 3 : " + tspG3.getObjectiveValue());
//            System.out.println("\n\n\n\nFirst Option : " + FirstOption);
//            System.out.println("Second Option : " + SecondOption);
//
//            long finish = System.currentTimeMillis();
//            long timeElapsed = finish - start;
//            System.out.println("\n\nTime of TSP : " + TimeUnit.MILLISECONDS.toSeconds(timeElapsed) + " seconds");
//
//
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//}
