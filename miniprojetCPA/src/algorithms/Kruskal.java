package algorithms;

import java.awt.Point;
import java.util.*;

public class Kruskal {

    public ArrayList<Edge> kruskal(ArrayList<Point> points) {
        List<Edge> edges = new ArrayList<>();
        for (Point p : points) {
            for (Point q : points) {
                if (!p.equals(q)) {
                    edges.add(new Edge(p, q));
                }
            }
        }
        edges.sort(Comparator.comparingDouble(e -> e.dist));  // trier les arêtes par ordre croissant de distance en u

        Map<Point, Point> parent = new HashMap<>();
        for (Point p : points) {
            parent.put(p, p);  // chaque point est son propre parent pour commencer
        }
        ArrayList<Edge> kruskal = new ArrayList<>();
        for (Edge edge : edges) {
            Point rootP = find(parent, edge.p);
            Point rootQ = find(parent, edge.q);
            if (!rootP.equals(rootQ)) {
                kruskal.add(edge);
                parent.put(rootP, rootQ);
            }
        }
        return kruskal;
    }

    /*
     * Trouver la racine de l'arbre de p en utilisant la compression de chemin pour accélérer les recherches ultérieures
     */
    private Point find(Map<Point, Point> parent, Point p) {
        if (!parent.get(p).equals(p)) {
            parent.put(p, find(parent, parent.get(p)));
        }
        return parent.get(p);
    }

    public static Tree2D edgesToTree(ArrayList<Edge> edges, Point root) {
        ArrayList<Edge> remainder = new ArrayList<>();
        ArrayList<Point> subTreeRoots = new ArrayList<>();
        Edge current;
        while (!edges.isEmpty()) {
            current = edges.remove(0);
            if (current.p.equals(root)) {
                subTreeRoots.add(current.q);
            } else if (current.q.equals(root)) {
                subTreeRoots.add(current.p);
            } else {
                remainder.add(current);
            }
        }

        ArrayList<Tree2D> subTrees = new ArrayList<>();
        for (Point subTreeRoot : subTreeRoots) {
            subTrees.add(edgesToTree(new ArrayList<>(remainder), subTreeRoot));
        }

        return new Tree2D(root, subTrees);
    }
}
