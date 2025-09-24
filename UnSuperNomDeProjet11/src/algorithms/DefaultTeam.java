package algorithms;

import java.awt.Point;
import java.util.ArrayList;

/***************************************************************
* TME 1: calcul de diamètre et de cercle couvrant minimum.    *
*   - Trouver deux points les plus éloignés d'un ensemble de  *
*     points donné en entrée.                                 *
*   - Couvrir l'ensemble de poitns donné en entrée par un     *
*     cercle de rayon minimum.                                *
*                                                             *
* class Circle:                                               *
*   - Circle(Point c, int r) constructs a new circle          *
*     centered at c with radius r.                            *
*   - Point getCenter() returns the center point.             *
*   - int getRadius() returns the circle radius.              *
*                                                             *
* class Line:                                                 *
*   - Line(Point p, Point q) constructs a new line            *
*     starting at p ending at q.                              *
*   - Point getP() returns one of the two end points.         *
*   - Point getQ() returns the other end point.               *
***************************************************************/
import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {

// calculDiametre: ArrayList<Point> --> Line
// renvoie une paire de points de la liste, de distance maximum.
public Line calculDiametre(ArrayList<Point> points) {
if (points.size() < 3) {
	return null;
}

Point p = points.get(0);
Point q = points.get(1);

double d = 0;
for (Point p1 : points) {
	for (Point p2 : points) {
		double tmp1 = p1.getX() - p2.getX();
		double tmp2 = p1.getY() - p2.getY();
		double d2 = tmp1 * tmp1 + tmp2 * tmp2;
		if (d2 > d) {
			p = p1;
			q = p2;
			d = d2;
		}
	}
}

System.out.println("diametre norm : " + distance(p, q));

return new Line(p, q);
}
public double distance(Point a, Point b) {
double tmp1 = a.getX() - b.getX();
double tmp2 = a.getY() - b.getY();
return tmp1 * tmp1 + tmp2 * tmp2;
}

public Point centre(Point a, Point b) {
int x = (int) ((a.getX() + b.getX()) / 2);
int y = (int) ((a.getY() + b.getY()) / 2);
return new Point(x, y);
}

// CercleMinAlgoNaif: ArrayList<Point> --> Circle
// renvoie un cercle couvrant tout point de la liste, de rayon minimum.
public Circle CercleMinAlgoNaif(ArrayList<Point> points) {
if (points.isEmpty()) {
	return null;
}

Point center = points.get(0);
int radius = 100;
for (Point p : points) {
	for (Point q : points) {
		center.x = (p.x + q.x) / 2;
		center.y = (p.y + q.y) / 2;
		double dx = p.x - q.x;
		double dy = p.y - q.y;
		double cRadiusSquared = (dx * dx + dy * dy) / 4;
		boolean allHit = true;
		for (Point r : points) {
			double dx1 = center.x - r.x;
			double dy1 = center.y - r.y;
			if (dx1 * dx1 + dy1 * dy1 > cRadiusSquared) {
				allHit = false;
				break;
			}
		}
		if (allHit) {
			radius = (int) Math.sqrt(cRadiusSquared);
			return new Circle(center, radius);
		}
	}
}

double CX, CY, cRadiusSquared;
double resRadiusSquared = Double.MAX_VALUE;

for (int i = 0; i < points.size(); i++) {
	for (int j = i + 1; j < points.size(); j++) {
		for (int k = j + 1; j < points.size(); k++) {
			Point p = points.get(i);
			Point q = points.get(j);
			Point r = points.get(k);
			if ((q.x - p.x) * (r.y - p.y) - (q.y - p.y) * (r.x - p.x) == 0) {
				continue;
			}
			if ((p.y == q.y) || (p.y == r.y)) {
				if (p.y == q.y) {
					p = points.get(k);
					r = points.get(i);
				} else {
					p = points.get(j);
					q = points.get(i);
				}
			}
			double c1x = (p.x + q.x) / 2;
			double c1y = (p.y + q.y) / 2;
			double c2x = (p.x + r.x) / 2;
			double c2y = (p.y + r.y) / 2;
			double alpha1 = (q.x - p.x) / (p.y - q.y);
			double beta1 = c1y - alpha1 * c1x;
			double alpha2 = (r.x - p.x) / (p.y - r.y);
			double beta2 = c2y - alpha1 * c2x;
			CX = (beta2 - beta1) / (alpha2 - alpha1);
			CY = alpha1 * CX + beta1;
			cRadiusSquared = (p.x - CX) * (p.x - CX) + (p.y - CY) * (p.y - CY);
			if (cRadiusSquared >= resRadiusSquared) {
				continue;
			}
			boolean allHit = true;
			for (Point s : points) {
				double dx1 = s.x - center.x;
				double dy1 = s.y - center.y;
				if 
				(dx1 * dx1 + dy1 * dy1 > cRadiusSquared) {
					allHit = false;
					break;
				}
			}
			if (allHit) {
				radius = (int) Math.sqrt(cRadiusSquared);
				center.x = (int) CX;
				center.y = (int) CY;
			}
		}
	}
}

return new Circle(center, radius);
}

private Circle AlgoRitter(ArrayList<Point> points) {
if (points.size() < 1)
	return null;
ArrayList<Point> rest = (ArrayList<Point>) points.clone();
Point dummy = rest.get(0);
Point p = dummy;
for (Point s : rest)
	if (dummy.distance(s) > dummy.distance(p))
		p = s;
Point q = p;
for (Point s : rest)
	if (p.distance(s) > p.distance(q))
		q = s;
double cX = .5 * (p.x + q.x);
double cY = .5 * (p.y + q.y);
double cRadius = .5 * p.distance(q);
rest.remove(p);
rest.remove(q);
while (!rest.isEmpty()) {
	Point s = rest.remove(0);
	double distanceFromCToS = Math.sqrt((s.x - cX) * (s.x - cX) + 
					    (s.y - cY) * (s.y - cY));
	if (distanceFromCToS <= cRadius)
		continue;
	double cPrimeRadius = .5 * (cRadius + distanceFromCToS);
	double alpha = cPrimeRadius / (double) (distanceFromCToS);
	double beta = (distanceFromCToS - cPrimeRadius) / 
		(double) (distanceFromCToS);
	double cPrimeX = alpha * cX + beta * s.x;
	double cPrimeY = alpha * cY + beta * s.y;
	cRadius = cPrimeRadius;
	cX = cPrimeX;
	cY = cPrimeY;
}
return new Circle(new Point((int) cX, (int) cY), (int) cRadius);
}
}
