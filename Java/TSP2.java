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
 * @authors Alon Zemer & Raviv Trichter
 */

public class TSP2 {

    private double[][] Graph;
    private final int N;
    private IloCplex cplex;
    private IloNumVar[][] x;
    private IloNumVar[] u;
    private IloLinearNumExpr obj;
    private String filename;
    private double FirstEdge;
    private double ObjectiveValue;
    private ArrayList<Integer> Route;
    private int firstIdx;
    private String status;


    public TSP2(double[][] G, int dimension, String filename){ // for given Graph
        Graph = G;
        N = dimension;
        Route = new ArrayList<Integer>();
        this.filename = filename;
    }

    public TSP2(int n){ // for random Graph
        N = n;
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

    public void printGraph(){
        for (int i = 1; i < x.length; i++) {
            for (int j = 1; j < x[i].length; j++)
                System.out.print(x[i][j]);
            System.out.println();
        }
    }

    public boolean solve(){
        initVariables();
        initObjectiveFunction();
        addConstraints();

        try {
            PrintWriter outFile = new PrintWriter(new FileWriter(filename, true));
            if(cplex.solve()) {

                for (int i = 0; i < x.length  ; i++) {

                    for (int j = 0; j < x[i].length ; j++) {

                        if(i != j && cplex.getValue(x[i][j]) > 0.5) {
                            if (i == 0) {
                                FirstEdge = Graph[i][j];
                                firstIdx = j;
                            }
                            Route.add(j);
                        }

                    }
                }
                ObjectiveValue = cplex.getObjValue();
                status = cplex.getStatus().toString();
                System.out.println(status + " \nRoute length : " + Route.size()+"\nPrinting Route :\n" );
                for (int i = 0; i < Route.size(); i++) {
                    System.out.print(Route.get(i) + " ");
                }
                System.out.println();
                System.out.println();

            }
            cplex.end();
            outFile.close();

        }catch (IloException e){
            e.printStackTrace();
            return false;
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }




    public void printRouteToFileByIndices(ArrayList<Vertex> Vertices, String filename, long timeInSeconds) {

        try{
            PrintWriter outFile = new PrintWriter(new FileWriter(filename, true));
            System.out.println("Route : ");
            for (int i = 0; i < Route.size(); i++) {
                outFile.print(Vertices.get( Route.get(i) ).getIndex() ); // printing the index of every vertex ('hachula130.dat')
                System.out.print(Vertices.get( Route.get(i) ).getIndex() + " ");
                outFile.print(" ");
            }
            System.out.println();
            outFile.print("\n" + ObjectiveValue + "\n" + TimeUnit.MILLISECONDS.toSeconds(timeInSeconds) + "\n");
            outFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }




    public void printRoute(){
        for (int i = 0; i < Route.size(); i++) {
            System.out.print(i != Route.size()-1 ? Route.get(i) + " -> " : Route.get(i));
        }
        System.out.println();
    }



}