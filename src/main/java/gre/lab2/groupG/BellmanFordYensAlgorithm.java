package gre.lab2.groupG;

import java.util.*;

import gre.lab2.graph.BFYResult;
import gre.lab2.graph.IBellmanFordYensAlgorithm;
import gre.lab2.graph.WeightedDigraph;

public final class BellmanFordYensAlgorithm implements IBellmanFordYensAlgorithm {

  private static final int SENTINEL = -2;

  /**
 * This method computes the shortest path tree or detects a negative cycle in a weighted directed graph using the Bellman-Ford algorithm.
 *
 * @param graph The weighted directed graph in which to compute the shortest path tree or detect a negative cycle.
 * @param from The source vertex from which to compute the shortest path tree.
 * @return A BFYResult object that represents either the shortest path tree or a negative cycle.
 */
@Override
public BFYResult compute(WeightedDigraph graph, int from) {
  // The number of vertices in the graph.
  int nbVertices = graph.getNVertices();
  // The array of shortest path distances from the source to each vertex.
  int[] distance = new int[nbVertices];
  // The array of predecessors for each vertex on the shortest path from the source.
  int[] predecessor = new int[nbVertices];
  // The queue of vertices to be processed.
  Queue<Integer> queue = new ArrayDeque<>();
  // The array that keeps track of whether a vertex is in the queue.
  boolean[] inQueue = new boolean[nbVertices];

  // Initialize the distances and predecessors.
  Arrays.fill(distance, Integer.MAX_VALUE);
  Arrays.fill(predecessor, BFYResult.UNREACHABLE);

  // The distance from the source to itself is 0.
  distance[from] = 0;
  // The counter for the number of iterations.
  int counterIterations = 0;

  // Add the source to the queue and mark it as in the queue.
  queue.add(from);
  inQueue[from] = true;
  // Add a sentinel to the queue.
  queue.add(SENTINEL);

  // Process the vertices in the queue.
  while (!queue.isEmpty()) {
    // Remove the vertex from the front of the queue.
    Integer current = queue.poll();
    if(current != SENTINEL) inQueue[current] = false;

    // If the vertex is the sentinel...
    if (current == SENTINEL) {
      // ...and the queue is not empty...
      if (!queue.isEmpty()) {
        // ...then increment the counter of iterations.
        ++counterIterations;
        // If the number of iterations equals the number of vertices...
        if (counterIterations == nbVertices) {
          // ...then a negative cycle has been detected because there are still possible improvements.
          List<Integer> cycle = findNegativeCycle(predecessor);
          // Return the negative cycle and its length.
          return new BFYResult.NegativeCycle(cycle, calculateCycleLength(graph, cycle));
        } else {
          // Otherwise, reinsert the sentinel into the queue.
          queue.add(SENTINEL);
        }
      }
    } else {
      // If the vertex is not the sentinel...
      // ...then relax the edges outgoing from the vertex.
      for (WeightedDigraph.Edge edge : graph.getOutgoingEdges(current)) {
        int neighbor = edge.to();
        int newDist = distance[current] + edge.weight();
        // If the new distance is shorter...
        if (distance[neighbor] > newDist) {
          // ...then update the distance and predecessor.
          distance[neighbor] = newDist;
          predecessor[neighbor] = current;
          // If the neighbor is not in the queue...
          if (!inQueue[neighbor]) {
            // ...then add it to the queue and mark it as in the queue.
            queue.add(neighbor);
            inQueue[neighbor] = true;
          }
        }
      }
    }
  }

  // Return the shortest path tree.
  return new BFYResult.ShortestPathTree(distance, predecessor);
}

 /**
 * This method finds the first negative cycle in a graph using the Bellman-Ford algorithm.
 * It uses the predecessor array that was computed by the Bellman-Ford algorithm.
 *
 * @param predecessor The array of predecessors for each vertex on the shortest path from the source.
 * @return A list of vertices that form a negative cycle, or an empty list if no such cycle exists.
 */
private List<Integer> findNegativeCycle(int[] predecessor) {
  // This array keeps track of the vertices that have been visited.
  boolean[] visited = new boolean[predecessor.length];
  // This list will hold the vertices of the negative cycle, if one is found.
  List<Integer> cycle = new LinkedList<>();

  // Iterate over all vertices.
  for (int i = 0; i < predecessor.length; i++) {
    // If the vertex has been visited, skip it.
    if (visited[i]) continue;

    // This array keeps track of the vertices visited in the current cycle.
    boolean[] cycleCheck = new boolean[predecessor.length];
    int current = i;

    // Traverse the predecessors until we reach an unreachable vertex or a vertex that has been visited in the current cycle.
    while (current != BFYResult.UNREACHABLE && !cycleCheck[current]) {
      cycleCheck[current] = true;
      current = predecessor[current];
    }

    // If we found a cycle...
    if (current != BFYResult.UNREACHABLE) {
      // ...then add the vertices of the cycle to the cycle list.
      int start = current;
      cycle.add(start);
      current = predecessor[start];
      while (current != start) {
        cycle.add(current);
        current = predecessor[current];
      }
      cycle.add(start); // To complete the cycle
      break;
    }

    // Mark the vertices visited in the current cycle as visited.
    for (int j = 0; j < cycleCheck.length; j++) {
      if (cycleCheck[j]) {
        visited[j] = true;
      }
    }
  }

  // Reverse the cycle to get it in the correct order.
  Collections.reverse(cycle);
  return cycle;
}

  /**
 * This method calculates the total weight of a cycle in a weighted directed graph.
 *
 * @param graph The weighted directed graph that contains the cycle.
 * @param cycle A list of vertices that form a cycle in the graph.
 * @return The total weight of the cycle.
 */
private int calculateCycleLength(WeightedDigraph graph, List<Integer> cycle) {
  // Initialize the length of the cycle to 0.
  int length = 0;

  // Iterate over the vertices in the cycle.
  for (int i = 0; i < cycle.size() - 1; i++) {
    // Get the current vertex and the next vertex in the cycle.
    int from = cycle.get(i);
    int to = cycle.get(i + 1);

    // Iterate over the outgoing edges of the current vertex.
    for (WeightedDigraph.Edge edge : graph.getOutgoingEdges(from)) {
      // If the edge leads to the next vertex in the cycle...
      if (edge.to() == to) {
        // ...then add the weight of the edge to the total length of the cycle and break the loop.
        length += edge.weight();
        break;
      }
    }
  }

  // Return the total length of the cycle.
  return length;
}

}

