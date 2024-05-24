package gre.lab2.groupG;

import java.util.*;

import gre.lab2.graph.BFYResult;
import gre.lab2.graph.IBellmanFordYensAlgorithm;
import gre.lab2.graph.WeightedDigraph;

public final class BellmanFordYensAlgorithm implements IBellmanFordYensAlgorithm {
  private static final int SENTINEL = -2;

  @Override
  public BFYResult compute(WeightedDigraph graph, int from) {
    int vertices = graph.getNVertices();
    int[] distance = new int[vertices];
    int[] predecessor = new int[vertices];
    Queue<Integer> queue = new ArrayDeque<>();

    // Initialiser les distances et les prédécesseurs
    Arrays.fill(distance, Integer.MAX_VALUE);
    Arrays.fill(predecessor, BFYResult.UNREACHABLE);

    distance[from] = 0;
    int k = 0;

    queue.add(from);
    queue.add(SENTINEL); // Sentinelle

    while (!queue.isEmpty()) {
      Integer current = queue.poll();

      if (current == SENTINEL) {
        if (!queue.isEmpty()) {
          k++;
          if (k == vertices) {
            // Détecter un circuit absorbant
            List<Integer> cycle = findNegativeCycle(graph, distance, predecessor);
            int length = calculateCycleLength(graph, cycle);
            return new BFYResult.NegativeCycle(cycle, length);
          } else {
            queue.add(SENTINEL); // Réinsérer la sentinelle
          }
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

    return cycle.reversed();
  }

  private int calculateCycleLength(WeightedDigraph graph, List<Integer> cycle) {
    int length = 0;
    for (int i = 0; i < cycle.size() - 1; i++) {
      int from = cycle.get(i);
      int to = cycle.get(i + 1);
      for (WeightedDigraph.Edge edge : graph.getOutgoingEdges(from)) {
        if (edge.to() == to) {
          length += edge.weight();
          break;
        }
      }
    }
    return length;
  }

}

