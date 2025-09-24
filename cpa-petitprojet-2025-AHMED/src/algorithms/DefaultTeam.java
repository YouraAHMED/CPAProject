package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import supportGUI.Circle;
import supportGUI.Line;

/***************************************************************
 * J'ai repris la squellette du TME1 pour realiser ce petit projet.
 ***************************************************************/

public class DefaultTeam {

	// Trouver le cercle minimum couvrant tous les points (par défaut, l’algo naïf est activé)
	public Circle calculCercleMin(ArrayList<Point> inputPoints) {
		//return calculCercleMinAlgoNaif(inputPoints);  // Active cette ligne pour l'algo naïf
		return minidisk(inputPoints);  // Active cette ligne pour l’algo de Welzl
	}

	/**
	 * Trouver la paire de points la plus éloignée dans l'ensemble de points donné.
	 */
	public Line calculDiametre(ArrayList<Point> points) {
		if (points.size() < 2) {
			return null;
		}

		Point p = points.get(0);
		Point q = points.get(1);
		double maxDistance = 0;

		for (Point p1 : points) {
			for (Point p2 : points) {
				double d2 = distance(p1, p2);
				if (d2 > maxDistance) {
					p = p1;
					q = p2;
					maxDistance = d2;
				}
			}
		}

		return new Line(p, q);
	}

	/**
	 * Algorithme naïf pour le plus petit cercle englobant.
	 * Teste toutes les paires et triplets possibles.
	 */
	public static Circle calculCercleMinAlgoNaif(ArrayList<Point> points) {
		if (points.isEmpty()) return new Circle(new Point(0, 0), 0);

		Circle bestCircle = null;
		double minRadius = Double.MAX_VALUE;

		// on teste toutes les paires de points
		for (int i = 0; i < points.size(); i++) {
			for (int j = i + 1; j < points.size(); j++) {
				Circle c = createCircleFromTwoPoints(points.get(i), points.get(j));
				if (isValidDisk(c, points) && c.getRadius() < minRadius) {
					bestCircle = c;
					minRadius = c.getRadius();
				}

				for (int k = j + 1; k < points.size(); k++) {
					c = createCircleFromThreePoints(points.get(i), points.get(j), points.get(k));
					if (c != null && isValidDisk(c, points) && c.getRadius() < minRadius) {
						bestCircle = c;
						minRadius = c.getRadius();
					}
				}
			}
		}

		return bestCircle;
	}

	/**
	 * Algorithme de Welzl pour le plus petit cercle englobant.
	 * @param inputPoints
	 * @return le cercle minimum couvrant tous les points
	 */
	public static Circle minidisk(ArrayList<Point> inputPoints) {
		return b_minidisk(inputPoints, new ArrayList<>());
	}

	/**
	 * cette fonction permet l’implémentation de l'algorithme récursif de Welzl qui permet de calculer le plus petit disque englobant (Smallest Enclosing Disk, SED)
	 * @param P
	 * @param R
	 * @return le cercle minimum couvrant tous les points
	 */
	private static Circle b_minidisk(ArrayList<Point> P, ArrayList<Point> R) {
		if (P.isEmpty() || R.size() == 3) {
			return constructMinDisk(R);
		}

		// Sélection d'un point aléatoire
		Point p = P.remove(P.size() - 1);

		// Calcul du disque sans p
		Circle D = b_minidisk(P, R);

		// Si p est déjà dans D et que D est le disque minimum, on le retourne
		if (D != null && p.distance(D.getCenter()) <= D.getRadius()) {
			P.add(p);
			return D;
		}

		// Sinon, p doit être ajouté à la frontière
		R.add(p);
		Circle newD = b_minidisk(P, R);
		R.remove(R.size() - 1);
		P.add(p);

		return newD;
	}

	/**
	 * cette fonction construit un disque en fonction des points de R.
	 * @param R
	 * Cas particuliers :
	 * - 0 point : disque nul
	 * - 1 point : disque centré sur ce point avec rayon 0
	 * - 2 points : disque passant par ces deux points
	 * - 3 points : cercle circonscrit au triangle formé par ces 3 points
	 * @return le cercle minimum couvrant les points de R
	 */
	private static Circle constructMinDisk(ArrayList<Point> R) {
		if (R.isEmpty()) {
			return new Circle(new Point(0, 0), 0);
		} else if (R.size() == 1) {
			return new Circle(R.get(0), 0);
		} else if (R.size() == 2) {
			return createCircleFromTwoPoints(R.get(0), R.get(1));
		} else {
			return createCircleFromThreePoints(R.get(0), R.get(1), R.get(2));
		}
	}

	/**
	 * Crée un cercle à partir de deux points.
	 * @param A
	 * @param B
	 * @return
	 */
	private static Circle createCircleFromTwoPoints(Point A, Point B) {
		Point center = new Point((A.x + B.x) / 2, (A.y + B.y) / 2);
		int radius = (int) Math.ceil(A.distance(B) / 2);
		return new Circle(center, radius);
	}

	/**
	 * Crée un cercle à partir de trois points.
	 * @param A
	 * @param B
	 * @param C
	 * @return
	 */
	private static Circle createCircleFromThreePoints(Point A, Point B, Point C) {
		double D = 2 * ((A.x * (B.y - C.y)) + (B.x * (C.y - A.y)) + (C.x * (A.y - B.y)));

		if (D == 0) return null; // Points alignés → Pas de cercle

		double ax2 = A.x * A.x + A.y * A.y;
		double bx2 = B.x * B.x + B.y * B.y;
		double cx2 = C.x * C.x + C.y * C.y;

		double Ux = (ax2 * (B.y - C.y) + bx2 * (C.y - A.y) + cx2 * (A.y - B.y)) / D;
		double Uy = (ax2 * (C.x - B.x) + bx2 * (A.x - C.x) + cx2 * (B.x - A.x)) / D;

		Point centre = new Point((int) Ux, (int) Uy);
		int rayon = (int) Math.ceil(centre.distance(A));

		return new Circle(centre, rayon);
	}

	/**
	 * verifie si tous les points sont dans le cercle
	 * @param c
	 * @param points
	 * @return
	 */
	private static boolean isValidDisk(Circle c, ArrayList<Point> points) {
		for (Point p : points) {
			//si un point n'est pas dans le cercle, on retourne false
			if (p.distance(c.getCenter()) > c.getRadius()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Calcul de la distance au carré entre deux points (évite la racine carrée inutile).
	 */
	public static double distance(Point a, Point b) {
		double dx = a.getX() - b.getX();
		double dy = a.getY() - b.getY();
		return dx * dx + dy * dy;
	}
}
