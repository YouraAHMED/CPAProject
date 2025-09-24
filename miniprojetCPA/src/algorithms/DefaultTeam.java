package algorithms;

import java.awt.Point;
import java.util.*;

public class DefaultTeam {

    private static final int Budget = 1664;

    public Tree2D calculSteiner(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
        ArrayList<Edge> edges = Utile.steinerBase(points, edgeThreshold, hitPoints);
        return Kruskal.edgesToTree(edges, hitPoints.get(0));
    }

    public Tree2D calculSteinerBudget(ArrayList<Point> points, int edgeThreshold, ArrayList<Point> hitPoints) {
        ArrayList<Edge> edges = Utile.steinerBase(points, edgeThreshold, hitPoints);
        return Kruskal.edgesToTree(Utile.optimizeSteiner(points, edges, hitPoints, edgeThreshold, Budget), hitPoints.get(0));
    }

}
