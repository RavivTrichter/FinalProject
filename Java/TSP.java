import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.cplex.IloCplex;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Based on code by Hernan Caceres <github/zonbeka>
 *
 * Solving a TSP by Miller-Tucker-Zemlin formulation
 *
 * @authors Raviv Trichter @ Alon Zemer
 *
 * Tel-Hai College
 */

public class TSP {

    private double[][] Graph;
    private final int N;
    private IloCplex cplex;
    private IloNumVar[][] x;
    private IloNumVar[] u;
    private IloLinearNumExpr obj;
    private double FirstEdge;
    private double ObjectiveValue;
    private ArrayList<Integer> Route;
    private int firstIdx;
    private String status;


    public TSP(double[][] G, int dimension){ // for given Graph
        Graph = G;
        N = dimension;
        Route = new ArrayList<Integer>();
    }



    public ArrayList<Integer> getRoute() {
        return Route;
    }


    public double getFirstEdge() {
        return FirstEdge;
    }

    public double getObjectiveValue() {
        return ObjectiveValue;
    }

    public int getFirstIdx() {
        return firstIdx;
    }

    private void initObjectiveFunction(){   //2
        try {

            obj = cplex.linearNumExpr();

            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    if (i != j)
                        obj.addTerm(Graph[i][j], x[i][j]); //multiplying (from model cij*xij)
            cplex.addMinimize(obj);

        } catch (IloException e) {
            System.err.println("In initObjectiveFunction()\nAborting Program");
            e.printStackTrace();
            System.exit(1);
        }

    }

    private void initVariables(){     //1
        try {

            cplex = new IloCplex();
            x = new IloNumVar[N][N];

            /* Telling the model we are going to be using boolean variables */
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    if (i != j)
                        x[i][j] = cplex.boolVar("x." + i + "." + j);
            /*
             *
             * ui - uj + (n-1)*xij <= n-2 for every i and  j except i = j = 1
             *
             * part of the model -> this part assures us that there is a real path
             *
             * */
            u = new IloNumVar[N];
            for (int i = 1; i < N; i++)
                u[i] = cplex.numVar(0, Double.MAX_VALUE, "u." + i);



        }catch (IloException e) {
            e.printStackTrace();
        }
    }

    private void addConstraints(){
        try {

            /* For each j :  xij = 1 and i != j      ==>    (For every column)    */

            for (int j = 0; j < N; j++) {
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int i = 0; i < N; i++) {
                    if (i != j)
                        expr.addTerm(1, x[i][j]);
                }
                cplex.addEq(expr, 1.0);
            }

            /* For each i :  xij = 1 and i != j      ==>     (For every row)      */

            for (int i = 0; i < N; i++) {
                IloLinearNumExpr expr = cplex.linearNumExpr();
                for (int j = 0; j < N; j++) {
                    if (j != i)
                        expr.addTerm(1, x[i][j]);
                }
                cplex.addEq(expr, 1.0);
            }

            /*   ui - uj + (n-1)*xij <= n-2     ==>  for every i and j except i = j = 1    */

            for (int i = 1; i < N; i++) {
                for (int j = 1; j < N; j++) {
                    if (i!=j){
                        IloLinearNumExpr expr = cplex.linearNumExpr();
                        expr.addTerm(1, u[i]);
                        expr.addTerm(-1, u[j]);
                        expr.addTerm(N - 1, x[i][j]);
                        cplex.addLe(expr, N - 2);
                    }
                }
            }


        } catch(IloException e){
            e.printStackTrace();
        }
    }

    public boolean solve(){
        initVariables();
        initObjectiveFunction();
        addConstraints();

        try {
            if(cplex.solve()) {

                for (int i = 0; i < x.length  ; i++) {

                    for (int j = 0; j < x[i].length ; j++) {

                        if(i != j && cplex.getValue(x[i][j]) > 0.5) { // part of the path
                            if (i == 0) {
                                FirstEdge = Graph[i][j];
                                firstIdx = j;
                            }
                            Route.add(j); // inserting the columns that are in the path in a certain order
                        }

                    }
                }
                ObjectiveValue = cplex.getObjValue();
                status = cplex.getStatus().toString();


                int nextNode, prevNode=0;

                ArrayList<Integer> RouteByIndices = new ArrayList<Integer>();

                RouteByIndices.add(prevNode); // adding zero as first node by our definition
                while(RouteByIndices.size() < N+1){
                    nextNode = Route.get(prevNode);
                    RouteByIndices.add(nextNode); // only add here
                    prevNode = nextNode;
                }
                Route = RouteByIndices;

            }
            cplex.end();

        }catch (IloException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }




    public void printRouteToFileByIndices(ArrayList<Vertex> Vertices, String filename, long timeInSeconds) {
        try{
            PrintWriter outFile = new PrintWriter(new FileWriter(filename, true));

            for (int i = 0; i < Route.size(); i++) {
                outFile.print(Vertices.get( Route.get(i) ).getIndex() ); // printing the index of every vertex ('hachula130.dat')
                outFile.print(" ");
            }
            outFile.print("\nObjective Value : " + ObjectiveValue + "\nSeconds : " + TimeUnit.MILLISECONDS.toSeconds(timeInSeconds) + "\nStatus : " + status);
            outFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


}