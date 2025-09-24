package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import supportGUI.Circle;
import supportGUI.Line;

public class DefaultTeam {

	// calculDiametre: ArrayList<Point> --> Line
	// renvoie une pair de points de la liste, de distance maximum.
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

	// calculDiametreOptimise: ArrayList<Point> --> Line
	// renvoie une pair de points de la liste, de distance maximum.
	public Line calculDiametreOptimise(ArrayList<Point> points) {
		if (points.size() < 3) {
			return null;
		}

		Point p = points.get(1);
		Point q = points.get(2);

		/*******************
		 * PARTIE A ECRIRE *
		 *******************/
		return new Line(p, q);
	}

	public int prodvect(Point q, Point p, Point r) {
		return (p.x - q.x) * (r.y - q.y) - (p.y - q.y) * (r.x - q.x);
	}

	public ArrayList<Point> filtrageAklToussaint(ArrayList<Point> tab, 
						     ArrayList<Point> points) {

		ArrayList<Point> filtre = new ArrayList<Point>();
		for (int i = 0; i < 4; i++) {
			tab.add(points.get(0));
		}
		for (Point p : points) {
			if (p.y > tab.get(0).y)
				tab.set(0, p);
			if (p.y < tab.get(1).y)
				tab.set(2, p);
			if (p.x > tab.get(2).x)
				tab.set(1, p);
			if (p.x < tab.get(3).x)
				tab.set(3, p);
		}
		for (Point p : points) {
			int z1 = prodvect(tab.get(0), tab.get(1), p);
			int z2 = prodvect(tab.get(1), tab.get(2), p);
			int z3 = prodvect(tab.get(2), tab.get(3), p);
			int z4 = prodvect(tab.get(3), tab.get(0), p);
			if (!(z1 < 0 && z2 < 0 && z3 < 0 && z4 < 0))
				filtre.add(p);
		}

		return filtre;

	}

	public ArrayList<Point> triPixel(HashMap<Integer, Point> ymin,
					 HashMap<Integer, Point> ymax,
			ArrayList<Point> points) {
		ArrayList<Point> tri = new ArrayList<Point>();

		for (Point p : points) {
			if (!ymin.containsKey(p.x) && !ymax.containsKey(p.x)) {
				ymin.put(p.x, p);

			} else if (ymin.containsKey(p.x) && !ymax.containsKey(p.x)) {
				if (p.y < ymin.get(p.x).y) {
					ymax.put(p.x, ymin.get(p.x));
					ymin.put(p.x, p);
				} else {
					ymax.put(p.x, p);
				}
			} else if (!ymin.containsKey(p.x) && ymax.containsKey(p.x)) {
				if (p.y > ymax.get(p.x).y) {
					ymin.put(p.x, ymax.get(p.x));
					ymax.put(p.x, p);
				} else {
					ymin.put(p.x, p);
				}
			} else {
				if (p.y > ymax.get(p.x).y) {
					ymax.put(p.x, p);
				}
				if (p.y < ymin.get(p.x).y) {
					ymin.put(p.x, p);
				}
			}
		}

		for (Entry<Integer, Point> entry : ymin.entrySet()) {
			Point value = entry.getValue();
			tri.add(value);
		}
		for (Entry<Integer, Point> entry : ymax.entrySet()) {
			Point value = entry.getValue();
			tri.add(value);
		}
		return tri;
	}

	// calculCercleMin: ArrayList<Point> --> Circle
	// renvoie un cercle couvrant tout point de la liste, de rayon minimum.
	public Circle calculCercleMin(ArrayList<Point> points) {
		if (points.isEmpty()) {
			return null;
		}

		Point center = points.get(0);
		int radius = 100;

		/*******************
		 * PARTIE A ECRIRE *
		 *******************/
		return new Circle(center, radius);
	}

	// enveloppeConvexe: ArrayList<Point> --> ArrayList<Point>
	// renvoie l'enveloppe convexe de la liste.
	public ArrayList<Point> enveloppeConvexe(ArrayList<Point> points) {
//        return AlgoNaifEnvelopeconvexe(points);
		if (points.size() < 3) {
			return null;
		}
		return triPixel(new HashMap<Integer, Point>(), 
				new HashMap<Integer, Point>(), points);

	}

	private ArrayList<Point> AlgoNaifEnvelopeconvexe(ArrayList<Point> points) {
		if (points.size() < 4)
			return points;

		ArrayList<Point> enveloppe = new ArrayList<Point>();

		for (Point p : points) {
			for (Point q : points) {
				if (p.equals(q))
					continue;
				Point ref = p;
				for (Point r : points)
					if (crossProduct(p, q, p, r) != 0) {
						ref = r;
						break;
					}
				if (ref.equals(p)) {
					enveloppe.add(p);
					enveloppe.add(q);
					continue;
				}
				double signeRef = crossProduct(p, q, p, ref);
				boolean estCote = true;
				for (Point r : points)
					if (signeRef * crossProduct(p, q, p, r) < 0) {
						estCote = false;
						break;
					} // ici sans le break le temps de calcul devient horrible
				if (estCote) {
					enveloppe.add(p);
					enveloppe.add(q);
				}
			}
		}
		return enveloppe;
	}

	private double crossProduct(Point p, Point q, Point s, Point t) {
		return ((q.x - p.x) * (t.y - s.y) - (q.y - p.y) * (t.x - s.x));
	}

	private ArrayList<Point> AlgoQuickHull(ArrayList<Point> points) {
		if (points.size() < 4)
			return points;

		Point ouest = points.get(0);
		Point sud = points.get(0);
		Point est = points.get(0);
		Point nord = points.get(0);
		for (Point p : points) {
			if (p.x < ouest.x)
				ouest = p;
			if (p.y > sud.y)
				sud = p;
			if (p.x > est.x)
				est = p;
			if (p.y < nord.y)
				nord = p;
		}
		ArrayList<Point> result = new ArrayList<Point>();
		result.add(ouest);
		result.add(sud);
		result.add(est);
		result.add(nord);

		ArrayList<Point> rest = (ArrayList<Point>) points.clone();
		for (int i = 0; i < rest.size(); i++) {
			if (triangleContientPoint(ouest, sud, est, rest.get(i))
			|| triangleContientPoint(ouest, est, nord, rest.get(i))) {
				rest.remove(i);
				i--;
			}
		}

		for (int i = 0; i < result.size(); i++) {
			Point a = result.get(i);
			Point b = result.get((i + 1) % result.size());
			Point ref = result.get((i + 2) % result.size());

			double signeRef = crossProduct(a, b, a, ref);
			double maxValue = 0;
			Point maxPoint = a;

			for (Point p : points) {
				double piki = crossProduct(a, b, a, p);
				if (signeRef * piki < 0 && Math.abs(piki) > maxValue) {
					maxValue = Math.abs(piki);
					maxPoint = p;
				}
			}
			if (maxValue != 0) {
				for (int j = 0; j < rest.size(); j++) {
					if (triangleContientPoint
					    (a, b, maxPoint, rest.get(j))) {
						rest.remove(j);
						j--;
					}
				}
				result.add(i + 1, maxPoint);
				i--;
			}
		}
		return result;
	}

	private boolean triangleContientPoint(Point a, Point b, Point c, Point x) {
		double l1 = ((b.y - c.y) * (x.x - c.x) + (c.x - b.x) * (x.y - c.y))
		/ (double) ((b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y));
		double l2 = ((c.y - a.y) * (x.x - c.x) + (a.x - c.x) * (x.y - c.y))
		/ (double) ((b.y - c.y) * (a.x - c.x) + (c.x - b.x) * (a.y - c.y));
		double l3 = 1 - l1 - l2;
		return (0 < l1 && l1 < 1 && 0 < l2 && l2 < 1 && 0 < l3 && l3 < 1);
	}

	private ArrayList<Point> AlgoJarvis(ArrayList<Point> points) {
		if (points.size() < 4)
			return points;

		Point ouest = points.get(0);
		for (Point p : points)
			if (p.x < ouest.x || (p.x == ouest.x && p.y > ouest.x))
				ouest = p;
		ArrayList<Point> enveloppe = new ArrayList<Point>();
		enveloppe.add(ouest);
		for (Point q : points) {
			if (q.equals(ouest))
				continue;
			Point ref = q;
			for (Point r : points)
				if (crossProduct(ouest, q, ouest, r) != 0) {
					ref = r;
					break;
				}
			if (ref.equals(q)) {
				enveloppe.add(q);
				continue;
			}
			double signeRef = crossProduct(ouest, q, ouest, ref);
			boolean estCote = true;
			for (Point r : points)
				if (signeRef * crossProduct(ouest, q, ouest, r) < 0) {
					estCote = false;
					break;
				}
			if (estCote) {
				enveloppe.add(q);
				break;
			}
		}

		do {
			Point p = enveloppe.get(enveloppe.size() - 2);
			Point q = enveloppe.get(enveloppe.size() - 1);
			Point r = points.get(0);
			for (Point s : points)
				if (!s.equals(p) && !s.equals(q)) {
					r = s;
					break;
				}
			for (Point s : points) {
				if (s.equals(p) || s.equals(q))
					continue;
				if (angle(p, q, q, s) < angle(p, q, q, r))
					r = s;
			}
			enveloppe.add(r);
		} while (!enveloppe.get(enveloppe.size() - 1).equals(enveloppe.get(0)));
		enveloppe.remove(0);
		return enveloppe;
	}

	private double angle(Point p, Point q, Point s, Point t) {
		if (p.equals(q) || s.equals(t))
			return Double.MAX_VALUE;
		double cosTheta = dotProduct(p, q, s, t) / 
			(double) (p.distance(q) * s.distance(t));
		return Math.acos(cosTheta);
	}

	private double dotProduct(Point p, Point q, Point s, Point t) {
		return ((q.x - p.x) * (t.x - s.x) + (q.y - p.y) * (t.y - s.y));
	}
	private ArrayList<Point> AlgoGraham(ArrayList<Point> points){
        if (points.size()<4) return points;

        ArrayList<Point> result = TriParPixel(points);
        for (int i=1;i<result.size()+2;i++) {
            Point p = result.get((i-1)%result.size());
            Point q = result.get(i%result.size());
            Point r = result.get((i+1)%(result.size()));
            if (crossProduct(p,q,p,r)<0) {
                result.remove(i%result.size());
                if (i==2) i=1;
                if (i>2) i-=2;
            }
        }
        return result;
    }
	private ArrayList<Point> TriParPixel(ArrayList<Point> points){
        if (points.size()<4) return points;
        int maxX=points.get(0).x;
        for (Point p: points) if (p.x>maxX) maxX=p.x;
        Point[] maxY = new Point[maxX+1];
        Point[] minY = new Point[maxX+1];
        for (Point p: points) {
            if (maxY[p.x]==null||p.y>maxY[p.x].y) maxY[p.x]=p;
            if (minY[p.x]==null||p.y<minY[p.x].y) minY[p.x]=p;
        }
        ArrayList<Point> result = new ArrayList<Point>();
        for (int i=0;i<maxX+1;i++) 
		if (minY[i]!=null) result.add(minY[i]);
        for (int i=maxX;i>=0;i--) 
		if (maxY[i]!=null && !result.get(result.size()-1).equals(maxY[i])) 
			result.add(maxY[i]);

        if (result.get(result.size()-1).equals(result.get(0))) 
		result.remove(result.size()-1);

        return result;
    }
}
