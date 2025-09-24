*** exercice 02 : ***

*** question 01 :  ***
On utilise l’algorithme l’élimination par pixel et ensuite dans le tableau des ymin et ymax 
et pour chaque 3 point de tableau on utilise le produit vectoriel pour savoir s’il y’a de mauvais côté.
Par exemple dans le tableau on a 3 point p,q,r si ProdCross(p,q,r)<0 donc on supprime le point Q.

private ArrayList<Point> Graham  (ArrayList<Point> points) {
	  Point [] Miny = new Point[RechercheMax(points)+1];
	  Point [] Maxy = new Point[RechercheMax(points)+1];
	
	  for (int i = 0; i < Miny.length; i++) {
		    Miny[i]=null;
		    Maxy[i]=null;
	}
	  
	  
	  for (Point p : points) {
		  if ( (Miny[p.x]==null) || ( p.y<Miny[p.x].y) ) {

			  Miny[p.x]=p;
		  }
		  
	  }
	  
       for (Point p : points) {
		  if ( Maxy[p.x]== null   ) {
			  Maxy[p.x]=p;
		   }else if(p.y>Maxy[p.x].y) {
			   Maxy[p.x]=p;
		   }
		  
	      }
	  
	  ArrayList<Point> enveloppe = new ArrayList<Point>();

      for (int i = 0; i < Miny.length; i++) {
		if ( Miny[i]!=null ) {
			enveloppe.add(Miny[i]);
		}
	}
      
      for (int i = Maxy.length-1; i >=0; i--) {
  		if ( Maxy[i]!=null) {
  			enveloppe.add(Maxy[i]);
  		}
  	    }
      for (int i = 1; i < enveloppe.size() + 2; i++) {
    	    Point p = enveloppe.get((i-1)%enveloppe.size());
    	    Point q = enveloppe.get((i)%enveloppe.size());
    	    Point r = enveloppe.get((i + 1)%enveloppe.size());

    	    if (crossProd(p, q, r) < 0) {
    	        enveloppe.remove(i %enveloppe.size());
    	        if (i==2) i=1;
                if (i>2) i-=2;
    	    }
    	}
      
      return enveloppe;
	  
  }

*** question 02 : ***

private ArrayList<point> question2 ( ArrayList<Point> pointsP1 , ArrayList<Point> pointsP2 ) {

    // on peut supooser que La taille de pointsp1 est L et la taille pointsP2 K et on a |pointsP1| + |pointsP2| = n 
    ArrayList<Point> points = new ArrayList<Point>(); 
    points = Fusion (pointsP1,pointP2) // la complexité est en O(n)
   
  return Graham(points) // La complexité est en O(n)

}

*** Question 03 :  ***

private ArrayList<point> question2 ( ArrayList<Point> points , debut , fin ) {

  if ( points.size()<=3) {
    return points;
  }
  int m = points.size()/2

  gauche = question2(points,0 , m)
  droite = question2(points,m+1,fin)

  return enveloppeConvexe (gauche,droite)

}

private ArrayList<point> enveloppeConvexe(ArrayList<Point> gauche , ArrayList<Point> droite ) {

     ArrayList<point> gauche1 = Graham (gauche)
     ArrayList<point> droite1 = Graham (droite)

     ArrayList<point> List1 = gauche1.addAll(droite1)
     
     return List1
     
}


*** Question 04 : ***

private int[] Question4(Arraylist<point> points) {

       ArrayList<point> pointsConvexe = enveloppeConvexe(points); // La complexité est en O(t)
       int [] tableau = new int [pointsConvexe.size()];
       int i=1;
       for(point p : pointsConevexe) { // la boucle est en O(n)
        tableau[i]=p.x;
        i++;
       }
    return tableau; // donc la complexité de programme est en O(t+n)
}


*** Exercice 03 : ***

*** Question 01 : ***

private int []  Suppresion (int [] T ) {
      
      int size = T.length;
     for (int i=0; i<T.length;i++) {
      int doublon = 0;
     for (int j=i+1; j<T.length;j++) {
       
        if (T[i]==T[j]) {
            for(int k = j; k<T.length;k++ ) {
                T[k]=T[k+1];
                size--;
            }
            
        }

     }
 }
  int [] tableau1 = new int[ size ];
   for (int i=0; i < tableau1.length;i++) {
     Tableau1[i]=Tableau[i];
   }

    return Tableau1;
}

*** Question 02 : *** 

==> Oui, c'est possible de résoudre correctement la suppression de duplicat d'une liste L.

private Question 2 ( int [] tableau , int X ) {

    Panier = new int[X] // La complexite ici en O(X)

    for (int i=0;i<tableau.length;i++) { // La boucle est en O(n)
      if (!Panier[i]) {
        System.out.println(tableau[i]);
        Panier[i]++;

      }
    }
}
==> La complexité est en O(n+X)
==> La valeur que prendre X est max(L)+1

L'ordre grandeur de n+X si le nombre de bits des entiers de la liste est inférieur à: 
bits = 10 ==> 2^10-1=1023 ==> X= 1023+1 = 1024 ==> O(n+X) = 10000+1024=11024
bits = 20 ==> 2^20-1= 1 048 575 ==> X= 1 048 575+1 = 1 048 576 ==> O(n+X)=10000+1M = 1 010 000 
.......

*** Question 03 : *** 

==> Oui, c'est possible de résoudre correctement la suppression de duplicat d'une liste L.

private Hash (int [] tableau ) {

    int tableau1 = new int [(2^512)-1];
    
    for (int i=0;i<tableau++;i++) {
       int HashValue = f(tableau[i]);
       if ( !tableau1[HashValue]) {
           System.out.Println(tableau[i]);
           tableau1[HashValue]++;
       }
          
    }
}
*** Question 04 : ***



*** Question 05 : *** 

==> Non , car la complexité optimale de tri est O(n*log(n)).


*** Exercice 04 :  ***

*** question 01 :  ***
Entrée : MG[n][n] // matrice des poids, avec MG[i][j] = ∞ s'il n'y a pas d'arête
Sortie : Mdist[n][n] // matrice des distances minimales

Initialiser M ← MG

Pour p de 1 à n-1 faire :
    Initialiser M_next[n][n] avec ∞ partout
    Pour i de 0 à n-1 :
        Pour j de 0 à n-1 :
            Pour k de 0 à n-1 :
                M_next[i][j] ← min(M_next[i][j], M[i][k] + MG[k][j])
    M ← M_next

Retourner M


