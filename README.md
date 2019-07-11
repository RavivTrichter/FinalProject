# FinalProject

This is the final project of our degree in Computer Science at Tel-Hai College, Israel.
Solving a "Dynamic" TSP using CPLEX in Java.

Project Name : TSPizza.

When the pizza deliveryman goes on a tour, it is exactly like a TSP route, he needs to visit every node once and he wants to do it in minimal time.
Here we are looking at the situation where the deliveryman needs to deliver m deliveries. After he starts the route, the pizza store still get's more deliveries. we assume it receives k more deliveries.

What should the deliveryman do at node i (0 <= i < m)? 
1. Finish his current route of m-i points, return to the pizza store and go for another route of k nodes.
2. Return immediately to the pizza store from node i, and make a new and calculate a new TSP route of ((m-i) + k) nodes.




In the Main file - 
