import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
from scipy.interpolate import make_interp_spline
import os
# on charger les résultats de l'expérimentation
data_file = "./resultatY/results.csv"
df = pd.read_csv(data_file)

# supprimer les espaces parasites dans les noms de colonnes
df.columns = df.columns.str.strip()

#extraire les valeurs des colonnes
filenames = df["Fichier"]
execution_times_naif = df["Temps Naif (µs)"] / 1_000_000  # Conversion en secondes
execution_times_welzl = df["Temps Welzl (µs)"] / 1_000_000  # Conversion en secondes

x_values = np.arange(len(filenames))

# j'ppliquer un lissage aux courbes si assez de points (je me suis fait aider de ChatGPT)
if len(x_values) > 10:
    x_smooth = np.linspace(0, len(filenames) - 1, 300)
    spline_naif = make_interp_spline(x_values, execution_times_naif, k=2)
    spline_welzl = make_interp_spline(x_values, execution_times_welzl, k=2)

    y_smooth_naif = spline_naif(x_smooth)
    y_smooth_welzl = spline_welzl(x_smooth)
else:
    x_smooth, y_smooth_naif, y_smooth_welzl = x_values, execution_times_naif, execution_times_welzl

plt.figure(figsize=(12, 6))
plt.plot(x_smooth, y_smooth_naif, label="Algorithme Naïf", linewidth=2, color="orange")
plt.plot(x_smooth, y_smooth_welzl, label="Algorithme Welzl", linewidth=2, color="blue")

plt.xlabel("Fichier de test")
plt.ylabel("Temps d'exécution (s)")
plt.title("Comparaison des temps d'exécution : Algorithme Naïf vs Welzl")

plt.ticklabel_format(style='scientific', axis='y', scilimits=(0, 0))

plt.yscale("log")

plt.legend()
plt.xticks(rotation=45, ha="right")
plt.tight_layout()

output_file = "./resultatY/graph_Naif_Welzl_sec.png"
if os.path.exists(output_file):
    os.remove(output_file)
    print(f"Le fichier {output_file} a été supprimé.")

plt.savefig(output_file)
print(f"Le fichier {output_file} a été enregistré.")

plt.show()


# Calcul des moyennes
mean_naif = execution_times_naif.mean()
mean_welzl = execution_times_welzl.mean()
speedup_avg = mean_naif / mean_welzl  # Facteur d'accélération moyen

print(f"Temps moyen de l'algorithme Naïf : {mean_naif:.6f} secondes")
print(f"Temps moyen de l'algorithme Welzl : {mean_welzl:.6f} secondes")
print(f"Welzl est en moyenne {speedup_avg:.2f} fois plus rapide que Naïf")

plt.figure(figsize=(6, 4))

# je multiplication du temps de Welzl pour qu'il soit visible
scaling_factor = 2000 # c'est la facteur d'échelle arbitraire
adjusted_welzl = mean_welzl * scaling_factor

plt.bar(["Naïf", f"Welzl (×{scaling_factor})"], [mean_naif, adjusted_welzl], color=["orange", "blue"])

plt.xlabel("Algorithmes")
plt.ylabel("Temps moyen d'exécution (s)")
plt.title("Comparaison des temps moyens d'exécution")

plt.savefig("./resultatY/average_execution_time.png")
plt.show()


def plot_speedup_bar_chart(data_file, output_file):

    df = pd.read_csv(data_file)

    df.columns = df.columns.str.strip()

    df["Temps Naif (µs)"] = df["Temps Naif (µs)"] / 1_000_000
    df["Temps Welzl (µs)"] = df["Temps Welzl (µs)"] / 1_000_000

    # ici je calcul le gain en temps (speedup)
    df["Gain en Temps"] = df["Temps Naif (µs)"] / df["Temps Welzl (µs)"]


    df = df.sort_values(by="Gain en Temps", ascending=False)

    df_sample = df.sample(15, random_state=42)  # je sélection 15 fichiers aléatoires

    # création du graphique avec échelle logarithmique pour mieux visualiser les écarts
    plt.figure(figsize=(12, 6))
    bars = plt.bar(df_sample["Fichier"], df_sample["Gain en Temps"], color="green")

    plt.yscale("log")

    plt.xlabel("Fichiers de test")
    plt.ylabel("Facteur d'accélération (Naïf / Welzl)")
    plt.title("Comparaison des gains en temps : Algorithme Welzl vs Naïf")
    plt.xticks(rotation=45, ha='right')
    plt.grid(axis='y', linestyle='--', alpha=0.7)

    # j'ajoute les valeurs exactes sur les 5 plus grands gains pour plus de clarté
    top_indices = np.argsort(df_sample["Gain en Temps"])[-5:]
    for i in top_indices:
        plt.text(i, df_sample["Gain en Temps"].iloc[i] + 0.2, f"{int(df_sample['Gain en Temps'].iloc[i])}",
                 ha='center', fontsize=10, fontweight="bold", color="black")

    plt.tight_layout()
    plt.savefig(output_file)
    plt.show()


plot_speedup_bar_chart("./resultatY/results.csv", "./resultatY/speedup_bar_chart.png")
