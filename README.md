# FinalProject

This is the final project of our degree in Computer Science at Tel-Hai College, Israel.

Solving a "Dynamic" TSP using CPLEX in Java, using Integer Programming and Miller-Tucker-Zemlin form.

Project Name : TSPizza.

When the pizza deliveryman goes on a tour, it is exactly like a TSP route, he needs to visit every node once and he wants to do it in minimal time.
Here we are looking at the situation where the deliveryman needs to deliver m deliveries. After he starts the route, the pizza store still get's more deliveries. we assume it receives k more deliveries.

What should the deliveryman do at node i (for 0 <= i < m) ? 
1. Finish his current route of m-i points, assuming he delivered already i deliveries, return to the pizza store and go for another route of k nodes.
2. Return immediately to the pizza store from node i, and make a new and calculate a new TSP route of ((m-i) + k) nodes.



Explaintion about the code :

  TSP - A class implemented using CPLEX API. Receiving a graph represented as a matrix and it's dimension, and calculating a TSP route. We assume that the first row in the matrix is always the restaraunt so the route will start and end there. This TSP is solved by Integer Programming using the Miller-Tucker-Zemlin form. The TSP route saves the route and more information in the class.




In the Main file - Main.java :
  Parses the data from 'hachula.dat' such that each row is a point and each point is a node in the graph.
  
  The main file receives 3 arguments in args variable : m , k and i in this order.
  Calculates 3 TSP routes of 3 different graphs: 
  
    - The first graph of m nodes.
    
    - The second graph of k nodes.
    
    - The third graph of m - i + k nodes (not including the nodes that where already visited in the firsr route).
    
  The main calculates two variables : firstOption, secondOption while :
    
    (TSP(x) - receives a graph with x nodes and solves it using CPLEX)
    
    firstOption = TSP(m) + TSP(k) - edges_that_already_visited
    
    secondOption = TSP(m-i+k) + weight_of_edge_to_return_home
    
  
  
  
  Node - Represents a single node in the graph.
  
  Vertices - Parses the data into an ArrayList of Nodes.
  
  Graph - Class that represents a graph as a matrix. Receives an ArrayList of nodes and creates a matrix such that each edge is the euclidean distance between the two nodes.
  
  
  
  
  *** To run the code you will need to download and install the CPLEX package. here is a great explanation :
  https://www.youtube.com/watch?v=51CcmaISSX0&t=166s
  
  authors : Raviv Trichter and Alon Zemer.
 
