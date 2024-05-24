package gre.lab2.groupG;

import java.io.IOException;
import java.util.List;

import gre.lab2.graph.BFYResult;
import gre.lab2.graph.WeightedDigraph;
import gre.lab2.graph.WeightedDigraphReader;

public final class Main {
  public static void main(String[] args) throws IOException {
    /*
     * if (args.length != 1) {
     * System.err.println("Usage: java Main <reseau.txt>");
     * System.exit(1);
     * }
     */

    // Lire le graphe à partir du fichier fourni
    // String filename = args[0];
    String filename = "data/reseau5.txt";
    WeightedDigraph graph = WeightedDigraphReader.fromFile(filename);
    int source = 0; // Sommet source

    // Créer une instance de l'algorithme et exécuter le calcul
    BellmanFordYensAlgorithm algorithm = new BellmanFordYensAlgorithm();
    BFYResult result = algorithm.compute(graph, source);
    System.out.println("Résultat de l'algorithme Bellman-Ford-Yens : " + result);

    // Afficher les résultats en fonction du type de résultat retourné
    if (result.isNegativeCycle()) {
      BFYResult.NegativeCycle negativeCycle = result.getNegativeCycle();
      List<Integer> cycleVertices = negativeCycle.vertices();
      int cycleLength = negativeCycle.length();

      System.out.println("Un circuit absorbant est accessible depuis la source.");
      System.out.println("Longueur du circuit absorbant : " + cycleLength);
      System.out.println("Suite des sommets du circuit absorbant : " + cycleVertices);

    } else {

      BFYResult.ShortestPathTree spt = result.getShortestPathTree();
      int[] distances = spt.distances();
      int[] predecessors = spt.predecessors();

      System.out.println("Aucun circuit absorbant n'est accessible depuis la source.");

      if (graph.getNVertices() < 25) {
        System.out.println("Arborescence des plus courts chemins (tableaux des distances et des prédécesseurs) :");
        System.out.println("Distances :");

        for (int i = 0; i < distances.length; i++) {
          System.out.println("Sommet " + i + " : " + (distances[i] == Integer.MAX_VALUE ? "∞" : distances[i]));
        }

        System.out.println("Prédécesseurs :");

        for (int i = 0; i < predecessors.length; i++) {
          System.out
              .println("Sommet " + i + " : " + (predecessors[i] == BFYResult.UNREACHABLE ? "-" : predecessors[i]));
        }
      }
    }
  }
}
