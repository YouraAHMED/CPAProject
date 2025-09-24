package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Utile {

/**
* cette fonction permet de génèrer un arbre de Steiner de base à partir des points à connecter.
* 
* On utilise l'approche suivante:
* - On utilise Kruskal pour garantir un arbre couvrant minimal, car il est efficace 
*   pour minimiser la distance totale.
* - L’algorithme de Floyd-Warshall est utilisé pour s’assurer que les connexions sont 
*   réalisables sous la contrainte de edgeThreshold.
* - L’approche basée sur les chemins courts permet d’éviter des liaisons inutiles 
*   et de mieux répartir les connexions.
*/
public static ArrayList<Edge> steinerBase(ArrayList<Point> points, 
                  int edgeThreshold, ArrayList<Point> hitPoints) {
Kruskal kruskal = new Kruskal();
ArrayList<Edge> edges = kruskal.kruskal(hitPoints);

int[][] paths = FloydWarshall.floydWarshall(points, edgeThreshold);
ArrayList<Edge> newEdges = new ArrayList<>();
HashSet<Point> pointsInclus = new HashSet<>();

for (Edge edge : edges) {
ArrayList<Integer> path = FloydWarshall.reconstructPath(paths, points.indexOf(edge.p), points.indexOf(edge.q));
if (path.isEmpty()) {
continue;
}

Point current = edge.p;
pointsInclus.add(current);

for (int i = 1; i < path.size(); i++) {
Point next = points.get(path.get(i));
newEdges.add(new Edge(current, next));
pointsInclus.add(next);
current = next;
}
}

ArrayList<Point> finalPoints = new ArrayList<>(pointsInclus);
ArrayList<Edge> optimizedEdges = kruskal.kruskal(finalPoints);
ArrayList<Edge> finalTree = new ArrayList<>();

for (Edge edge : optimizedEdges) {
if (edge.distance() <= edgeThreshold) {
finalTree.add(edge);
}
}
return finalTree;
}

/**
* cette fonction optimise l’arbre de Steiner en essayant différentes stratégies pour maximiser 
* le nombre de points connectés tout en restant sous la contrainte de budget.
* 
* On teste plusieurs points de départ pour voir lequel maximise le nombre de connexions
* On priorise les connexions les plus courtes en utilisant une file de priorité
* On compare différentes solutions et on garde la meilleure
* On privilégie les connexions stratégiques pour maximiser le nombre de points connectés
*/
public static ArrayList<Edge> optimizeSteiner(
ArrayList<Point> points, ArrayList<Edge> edges,
ArrayList<Point> hitPoints, int edgeThreshold, int budgetLimit) {

ArrayList<Edge> bestEdges = new ArrayList<>();
double bestDistance = Double.MAX_VALUE;
int maxConnectedPoints = 0;

if (hitPoints.isEmpty()) {
return bestEdges;
}

for (Point startPoint : hitPoints) {
HashSet<Point> connectedPoints = new HashSet<>();
ArrayList<Edge> currentEdges = new ArrayList<>();
double totalDistance = 0;

connectedPoints.add(startPoint);

ArrayList<Point> sortedHitPoints = new ArrayList<>(hitPoints);
sortedHitPoints.sort
(Comparator.comparingDouble(p -> p.distance(startPoint)));

//file de priorité pour sélectionner les connexions les plus courtes en premier
PriorityQueue<Edge> edgeQueue = new PriorityQueue<>(Comparator.comparingDouble(Edge::distance));
edgeQueue.addAll(edges);

while (!edgeQueue.isEmpty() && totalDistance < budgetLimit) {
Edge bestEdge = null;
double minDistance = Double.MAX_VALUE;
Point newPoint = null;

for (Edge edge : edgeQueue) {
if ((connectedPoints.contains(edge.p) 
|| connectedPoints.contains(edge.q))
&& edge.distance() < minDistance) {

Point candidatePoint = 
connectedPoints.contains(edge.p) ? edge.q : edge.p;

if (!connectedPoints.contains(candidatePoint) &&
(totalDistance + edge.distance()) <= budgetLimit) {
bestEdge = edge;
minDistance = edge.distance();
newPoint = candidatePoint;
}
}
}

if (bestEdge == null || newPoint == null) {
break; // Stop si aucune nouvelle connexion possible
}


currentEdges.add(bestEdge);
totalDistance += bestEdge.distance();
connectedPoints.add(newPoint);
edgeQueue.remove(bestEdge);
}


if (connectedPoints.size() > maxConnectedPoints
|| (connectedPoints.size() ==
maxConnectedPoints && totalDistance < bestDistance)) {
bestEdges = new ArrayList<>(currentEdges);
bestDistance = totalDistance;
maxConnectedPoints = connectedPoints.size();
}
}

return bestEdges;
}

}
