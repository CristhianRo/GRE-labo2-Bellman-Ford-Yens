package gre.lab2.groupG;

import java.io.IOException;

import gre.lab2.graph.BFYResult;
import gre.lab2.graph.WeightedDigraph;
import gre.lab2.graph.WeightedDigraphReader;

public final class Main {
  public static void main(String[] args) throws IOException {
    // TODO
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

    WeightedDigraph graph52 = WeightedDigraphReader.fromFile("data/reseau5_2.txt");
    BellmanFordYensAlgorithm bfy = new BellmanFordYensAlgorithm();
    BFYResult result52 = bfy.compute(graph52, 0);
    System.out.println(result52);

  }
}
