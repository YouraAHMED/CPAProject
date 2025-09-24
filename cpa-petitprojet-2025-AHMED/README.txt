Dans ce projet j'utilise le langage de programmation Java avec la version 22.

J'utilise ant pour compiler et exécuter le projet.

Voici les principales commandes pour compiler et exécuter le projet:

Pour lancer ces commandes il faut se placer à l'intérieur du repertoire cpa-petitprojet-2025-AHMED.

- Pour compiler le projet:
    ant compile

- Pour exécuter le projet:
    ant run

Si vous voulez lancer le projet avec l'algorithme naif il faut juste décommenter la ligne 18 et commenter la ligne 19 dans le fichier "src/algorithms/DefaultTeam.java".

- Pour exécuter les expérimentations:
    ant experiment

Quant on lance les experimentations, les resultats sont stockés dans le repertoire "resultatY" ensuite dans le fichier "results.csv".

Enfin pour la visualisation des courbes de performances j'ai utilisé un sript python pour dessiner et les résultats sont aussi stocké dans le repertoire sous l'extension ".png".