package Experimentation;

import java.awt.Point;
import java.io.*;
import java.util.*;

import algorithms.DefaultTeam;

public class Experimentation {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("il faut 2 arguments : <dossier d'entrée> <fichier de sortie>");
            return;
        }


        String inputDirectory = args[0]; // Premier argument : répertoire d'entrée
        String outputFile = args[1];    // Deuxième argument : fichier de sortie

        // Liste pour stocker tous les résultats
        List<String> results = new ArrayList<>();

        File folder = new File(inputDirectory);
        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("Dossier introuvable : " + inputDirectory);
            return;
        }
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".points"));

        if (files != null) {
            Arrays.sort(files, Comparator.comparing(File::getName));
        }

        if (files == null || files.length == 0) {
            System.err.println("Aucun fichier .points trouvé dans : " + inputDirectory);
            return;
        }

        for (File file : files) {
            ArrayList<Point> points = lirePointsDepuisFichier(file);

            if (points.isEmpty()) {
                System.err.println("Fichier vide ou incorrect : " + file.getName());
                continue;
            }

            // Mesurer le temps pour AlgoNaif
            long debutAlgoNaif = System.nanoTime();
            DefaultTeam.calculCercleMinAlgoNaif(points);
            long tempsNaif = (System.nanoTime() - debutAlgoNaif) / 1000;

            // Mesurer le temps pour AlgoWelzl
            long debutAlgoWelzl = System.nanoTime();
            DefaultTeam.minidisk(points);
            long tempsWelzl = (System.nanoTime() - debutAlgoWelzl) / 1000;

            String resultat = String.format("%s, %d, %d", file.getName(), tempsNaif, tempsWelzl);
            results.add(resultat);
            System.out.println(resultat);
        }

        ecrireResultatsDansFichier(results, outputFile);
        System.out.println("Les résultats ont été enregistrés dans : " + outputFile);
    }

    private static ArrayList<Point> lirePointsDepuisFichier(File file) {
        ArrayList<Point> points = new ArrayList<>();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] coordinates = scanner.nextLine().trim().split("\\s+");
                if (coordinates.length == 2) {
                    try {
                        int x = Integer.parseInt(coordinates[0]);
                        int y = Integer.parseInt(coordinates[1]);
                        points.add(new Point(x, y));
                    } catch (NumberFormatException e) {
                        System.err.println("Coordonnées invalides dans : " + file.getName());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Fichier non trouvé : " + file.getAbsolutePath());
        }
        return points;
    }

    // Méthode pour écrire les résultats dans un fichier
    private static void ecrireResultatsDansFichier(List<String> results, String outputFile) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write("Fichier, Temps Naif (µs), Temps Welzl (µs)");
            writer.newLine();
            for (String result : results) {
                writer.write(result);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture du fichier : " + outputFile);
        }
    }
}
