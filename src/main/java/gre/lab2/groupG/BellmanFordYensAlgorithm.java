package gre.lab2.groupG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.HashSet;
import java.util.Set;

import gre.lab2.graph.BFYResult;
import gre.lab2.graph.IBellmanFordYensAlgorithm;
import gre.lab2.graph.WeightedDigraph;

public final class BellmanFordYensAlgorithm implements IBellmanFordYensAlgorithm {
  @Override
  public BFYResult compute(WeightedDigraph graph, int from) {
    int vertices = graph.getNVertices();
    int[] distance = new int[vertices];
    int[] predecessor = new int[vertices];
    Queue<Integer> queue = new LinkedList<>();

    // Initialiser les distances et les prédécesseurs
    Arrays.fill(distance, Integer.MAX_VALUE);
    Arrays.fill(predecessor, BFYResult.UNREACHABLE);

    distance[from] = 0;
    int k = 0;

    queue.add(from);
    queue.add(null); // Sentinelle

    while (!queue.isEmpty()) {
      Integer current = queue.poll();

      if (current == null) {
        if (!queue.isEmpty()) {
          k++;
          if (k == vertices) {
            // Détecter un circuit absorbant
            return new BFYResult.NegativeCycle(findNegativeCycle(graph, distance, predecessor), -1);
          }
          queue.add(null); // Réinsérer la sentinelle
        }
      } else {
        for (WeightedDigraph.Edge edge : graph.getOutgoingEdges(current)) {
          int neighbor = edge.to();
          int newDist = distance[current] + edge.weight();
          if (newDist < distance[neighbor]) {
            distance[neighbor] = newDist;
            predecessor[neighbor] = current;
            if (!queue.contains(neighbor)) {
              queue.add(neighbor);
            }
          }
        }
      }
    }

    return new BFYResult.ShortestPathTree(distance, predecessor);
  }

  private List<Integer> findNegativeCycle(WeightedDigraph graph, int[] distance, int[] predecessor) {
    // Cette méthode devrait implémenter la logique pour trouver le premier circuit absorbant
    // à partir des prédécesseurs et des distances.
    // Cette implémentation est simplifiée et peut nécessiter des ajustements.

    boolean[] visited = new boolean[predecessor.length];
    List<Integer> cycle = new ArrayList<>();

    for (int i = 0; i < predecessor.length; i++) {
      if (visited[i]) continue;

      boolean[] cycleCheck = new boolean[predecessor.length];
      int current = i;

      while (current != BFYResult.UNREACHABLE && !cycleCheck[current]) {
        cycleCheck[current] = true;
        current = predecessor[current];
      }

      if (current != BFYResult.UNREACHABLE) {
        int start = current;
        cycle.add(start);
        current = predecessor[start];
        while (current != start) {
          cycle.add(current);
          current = predecessor[current];
        }
        cycle.add(start); // Pour compléter le cycle
        break;
      }

      for (int j = 0; j < cycleCheck.length; j++) {
        if (cycleCheck[j]) {
          visited[j] = true;
        }
      }
    }

    return cycle;
  }


}

