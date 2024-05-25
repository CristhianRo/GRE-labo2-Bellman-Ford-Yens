package gre.lab2.groupG;

import java.util.*;

import gre.lab2.graph.BFYResult;
import gre.lab2.graph.IBellmanFordYensAlgorithm;
import gre.lab2.graph.WeightedDigraph;

public final class BellmanFordYensAlgorithm implements IBellmanFordYensAlgorithm {

  private static final int SENTINEL = -2;

  @Override
  public BFYResult compute(WeightedDigraph graph, int from) {
    int nbVertices = graph.getNVertices();
    int[] distance = new int[nbVertices];
    int[] predecessor = new int[nbVertices];
    Queue<Integer> queue = new ArrayDeque<>(); // File des sommets à traiter
    boolean[] inQueue = new boolean[nbVertices];

    // Initialiser les distances et les prédécesseurs
    Arrays.fill(distance, Integer.MAX_VALUE);
    Arrays.fill(predecessor, BFYResult.UNREACHABLE);

    distance[from] = 0;
    int compteurIterations = 0;

    queue.add(from);
    inQueue[from] = true;
    queue.add(SENTINEL); // Sentinelle

    while (!queue.isEmpty()) {
      Integer current = queue.poll();
      // Retirer le sommet de la file d'attente
      if(current != SENTINEL) inQueue[current] = false;

      if (current == SENTINEL) {
        if (!queue.isEmpty()) {
          compteurIterations++;
          if (compteurIterations == nbVertices) {
            // Détecter un circuit absorbant
            List<Integer> cycle = findNegativeCycle(predecessor);
            return new BFYResult.NegativeCycle(cycle, calculateCycleLength(graph, cycle));

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
            if (!inQueue[neighbor]) {
              queue.add(neighbor);
              inQueue[neighbor] = true;
            }
          }
        }
      }
    }

    return new BFYResult.ShortestPathTree(distance, predecessor);
  }

  private List<Integer> findNegativeCycle(int[] predecessor) {

    boolean[] visited = new boolean[predecessor.length];
    List<Integer> cycle = new LinkedList<>();

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

        cycle.add(start); // Pour compléter le cycle rajouter le sommet de départ
        break;
      }

      for (int j = 0; j < cycleCheck.length; j++) {
        if (cycleCheck[j]) {
          visited[j] = true;
        }
      }
    }

    Collections.reverse(cycle);
    return cycle;
  }

  private int calculateCycleLength(WeightedDigraph graph, List<Integer> cycle) {
    int length = 0;

    // Parcourir tous les sommets du cycle
    for (int i = 0; i < cycle.size() - 1; i++) {
      int from = cycle.get(i);
      int to = cycle.get(i + 1);

      // Trouver l'arête correspondante de 'from' à 'to'
      for (WeightedDigraph.Edge edge : graph.getOutgoingEdges(from)) {
        if (edge.to() == to) {
          length += edge.weight(); // Ajouter le poids de l'arête à la longueur du cycle
          break;
        }
      }
    }
    return length;
  }

}

