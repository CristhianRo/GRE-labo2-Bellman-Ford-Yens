package gre.lab2.groupG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import gre.lab2.graph.BFYResult;
import gre.lab2.graph.IBellmanFordYensAlgorithm;
import gre.lab2.graph.WeightedDigraph;

public final class BellmanFordYensAlgorithm implements IBellmanFordYensAlgorithm {
  @Override
  public BFYResult compute(WeightedDigraph graph, int from) {
    int n = graph.getNVertices();
    int[] distances = new int[n];
    int[] predecessors = new int[n];
    Arrays.fill(distances, Integer.MAX_VALUE);
    Arrays.fill(predecessors, BFYResult.UNREACHABLE);
    distances[from] = 0;

    int k = 0;  // Compteur d'itérations
    Queue<Integer> queue = new LinkedList<>();
    queue.add(from);
    queue.add(null);  // Sentinelle

    while (!queue.isEmpty()) {
      Integer u = queue.poll();

      if (u == null) {  // Sentinelle
        if (!queue.isEmpty()) {
          k++;
          if (k == n) {
            return new BFYResult.NegativeCycle(new ArrayList<>(), -1);  // Placeholder for indicating a negative cycle
          }
          queue.add(null);  // Réinsérer la sentinelle
        }
      } else {
        for (WeightedDigraph.Edge edge : graph.getOutgoingEdges(u)) {
          int v = edge.to();
          int weight = edge.weight();
          if (distances[u] != Integer.MAX_VALUE && distances[u] + weight < distances[v]) {
            distances[v] = distances[u] + weight;
            predecessors[v] = u;
            if (!queue.contains(v)) {
              queue.add(v);
            }
          }
        }
      }
    }

    return new BFYResult.ShortestPathTree(distances, predecessors);
  }

  private BFYResult findNegativeCycle(int[] predecessors, int start) {
    int n = predecessors.length;
    boolean[] visited = new boolean[n];
    int current = start;

    // Find a vertex on the cycle
    for (int i = 0; i < n; i++) {
      current = predecessors[current];
    }

    // Collect the cycle vertices
    List<Integer> cycle = new ArrayList<>();
    int cycleStart = current;
    do {
      cycle.add(current);
      current = predecessors[current];
    } while (current != cycleStart);

    cycle.add(cycleStart);
    int cycleLength = 0;
    for (int i = 0; i < cycle.size() - 1; i++) {
      cycleLength += getEdgeWeight(cycle.get(i), cycle.get(i + 1));
    }

    return new BFYResult.NegativeCycle(cycle, cycleLength);
  }

  private int getEdgeWeight(int from, int to) {
    // This method should retrieve the actual weight of the edge from 'from' to 'to'.
    // In this context, you may need to maintain a reference to the graph to get the edge weight.
    // Here we assume there's a way to get this information, perhaps via a map or additional method in the WeightedDigraph class.



    return 0; // Placeholder, replace with actual edge weight retrieval logic.
  }

}

