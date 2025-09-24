package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class FloydWarshall {

    public static int[][] floydWarshall(ArrayList<Point> points, int edgeThreshold) {
        int n = points.size();
        int[][] next = new int[n][n]; // tableau des prochains sommets
        double[][] dist = new double[n][n];

        initializeMatrices(points, edgeThreshold, n, next, dist);
        computeShortestPaths(n, next, dist);

        return next;
    }

    private static void initializeMatrices
    (ArrayList<Point> points, int edgeThreshold, int n, int[][] next, double[][] dist) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dist[i][j] = 0;
                    next[i][j] = j;
                } else if (points.get(i).distance(points.get(j)) <= edgeThreshold) {
                    dist[i][j] = points.get(i).distance(points.get(j));
                    next[i][j] = j;
                } else {
                    dist[i][j] = Double.POSITIVE_INFINITY;
                    next[i][j] = -1;
                }
            }
        }
    }

    private static void computeShortestPaths(int n, int[][] next, double[][] dist) {
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }

    public static ArrayList<Integer> reconstructPath(int[][] next, int start, int end) {
        ArrayList<Integer> path = new ArrayList<>();
        if (next[start][end] == -1) {
            return path; // Aucun chemin

                }path.add(start);
        while (start != end) {
            start = next[start][end];
            path.add(start);
        }
        return path;
    }
}
