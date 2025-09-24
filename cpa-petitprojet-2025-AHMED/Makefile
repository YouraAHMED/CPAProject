# Définition des variables
JARS = jars/*
SRC = src/Experimentation/*.java src/algorithms/*.java
BIN = bin

# Définition des dossiers et fichiers
SAMPLES = ./samples
SAMPLES_DIFF = ./samplesDiff
RESULTS = ./Resultat/resultats_experimentation.csv
RESULTS_DIFF = ./Resultat/resultats_experimentationDiff.csv
ANT_FILE=build.xml

# Classes principales
MAIN_EXP = Experimentation.Experimentation
MAIN_CURVE = Experimentation.CourbeTemps

# Règle principale
default: compile

# Compilation des fichiers Java
compile:
	@mkdir -p $(BIN)
	javac -cp "$(JARS)" -d $(BIN) $(SRC)

# Exécuter l'Expérimentation sur samples
run_samples: compile
	java -cp "$(BIN):$(JARS)" $(MAIN_EXP) $(SAMPLES) $(RESULTS)

# Exécuter l'Expérimentation sur samplesDiff
run_samplesDiff: compile
	java -cp "$(BIN):$(JARS)" $(MAIN_EXP) $(SAMPLES_DIFF) $(RESULTS_DIFF)

# Générer la courbe pour samples
curve_samples: compile
	java -cp "$(BIN):$(JARS)" $(MAIN_CURVE) $(RESULTS)

# Générer la courbe pour samplesDiff
curve_samplesDiff: compile
	java -cp "$(BIN):$(JARS)" $(MAIN_CURVE) $(RESULTS_DIFF)
	

# Appeler le build.xml avec Ant
ant_build: compile
	ant -f $(ANT_FILE)
	
run_build: compile
	ant -f build.xml run



# Tout exécuter
all: compile run_samples run_samplesDiff curve_samples curve_samplesDiff ant_build run_build

