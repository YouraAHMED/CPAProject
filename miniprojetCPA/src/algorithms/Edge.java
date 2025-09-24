package algorithms;

import java.awt.Point;

public class Edge {

    public final Point p;
    public final Point q;
    public final double dist;

    public Edge(Point u, Point v) {
        this.p = u;
        this.q = v;
        this.dist = u.distance(v);
    }

    public double distance() {
        return dist;
    }
}
