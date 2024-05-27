package gre.lab2.groupG;

import java.io.IOException;

import gre.lab2.graph.BFYResult;
import gre.lab2.graph.WeightedDigraph;
import gre.lab2.graph.WeightedDigraphReader;
import java.io.File;

/**
 * This is the main class of the application. It contains methods to process a directory of files,
 * where each file contains data for a weighted directed graph. The Bellman-Ford-Yens algorithm is
 * applied to each graph, and the results are printed.
 *
 * @author Cristhian Ronquillo, Mehdi Benzekri
 */
public final class Main {

  // The source vertex from which to compute the shortest path tree.
  private static final int SOURCE = 0;

  public static void main(String[] args) throws IOException {
    // TODO:
    //  - Renommage du package ;
    //  - Écrire le code dans le package de votre groupe et UNIQUEMENT celui-ci ;
    //  - Documentation soignée comprenant :
    //    - la javadoc, avec auteurs et description des implémentations ;
    //    - des commentaires sur les différentes parties de vos algorithmes.

    // Exemple d'utilisation de la classe WeightedDigraphReader
    // WeightedDigraph graph = WeightedDigraphReader.fromInputStreamReader(new InputStreamReader(System.in));
    // IBellmanFordYensAlgorithm bfy = new BellmanFordYensAlgorithm();
    // BFYResult result = bfy.compute(graph, 0);
    // System.out.println(result);

    // Specify the path of the directory to read
    String directoryPath = "data";

    // Create a File object for the directory
    File directory = new File(directoryPath);

    // Process the directory
    processDirectory(directory);
  }

  /**
   * Processes all the files in the specified directory.
   *
   * @param directory the directory to process
   * @throws IOException if an I/O error occurs
   */
  private static void processDirectory(File directory) throws IOException {
    // Check if the path is a directory
    if (directory.isDirectory()) {
      // List the files in the directory
      File[] files = directory.listFiles();

      // Iterate over the array of files
      if (files != null) {
        for (File file : files) {
          // Check if the path is a file
          if (file.isFile()) {
            // Process the file
            processFile(file);
          }
        }
      } else {
        System.out.println("The directory is empty.");
      }
    } else {
      System.out.println("The specified path is not a directory.");
    }
  }

  /**
   * Processes a single file by reading the graph data, applying the algorithm of Bellman Ford Yens,
   * and printing the results.
   *
   * @param file the file to process, this file contains the graph data
   * @throws IOException if an I/O error occurs
   */
  private static void processFile(File file) throws IOException {
    // Read the graph from the provided file
    WeightedDigraph graph = WeightedDigraphReader.fromFile(file.getAbsolutePath());

    // Create an instance of the algorithm and execute the computation
    BellmanFordYensAlgorithm algorithm = new BellmanFordYensAlgorithm();
    BFYResult result = algorithm.compute(graph, SOURCE);

    // Print the file name
    System.out.println();
    System.out.println("---------------------------------------------");
    System.out.println("File: " + file.getName());

    // Display the results based on the type of result returned
    if (result.isNegativeCycle()) {
      BFYResult.NegativeCycle negativeCycle = result.getNegativeCycle();
      System.out.println("A negative cycle has been detected.");
      System.out.println("The length (total weight) of the negative cycle is: " + negativeCycle.length());
      System.out.println("The vertices of the negative cycle are: " + negativeCycle.vertices());
    } else {
      BFYResult.ShortestPathTree spt = result.getShortestPathTree();
      System.out.println("Shortest path tree found.");
      if (graph.getNVertices() < 25) {
        System.out.println(spt);
      } else {
        System.out.println("The graph has more than 25 vertices, the results are not displayed.");
      }
    }
  }
}