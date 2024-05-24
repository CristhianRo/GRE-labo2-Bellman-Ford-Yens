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

    WeightedDigraph graph51 = WeightedDigraphReader.fromFile("data/reseau5_1.txt");
    BellmanFordYensAlgorithm bfy1 = new BellmanFordYensAlgorithm();
    BFYResult result51 = bfy1.compute(graph51, 0);
    System.out.println(result51);
    //(0,—) (1,3) (5,1) (4,0)

    //***********************************************************************
    WeightedDigraph graph52 = WeightedDigraphReader.fromFile("data/reseau5_2.txt");
    BellmanFordYensAlgorithm bfy2 = new BellmanFordYensAlgorithm();
    BFYResult result52 = bfy2.compute(graph52, 0);
    System.out.println(result52);
    //(0,—) (0,3) (4,1) (1,0) (3,2) (∞,—)

    //***********************************************************************
    WeightedDigraph graph53 = WeightedDigraphReader.fromFile("data/reseau5_3.txt");
    BellmanFordYensAlgorithm bfy3 = new BellmanFordYensAlgorithm();
    BFYResult result53 = bfy3.compute(graph53, 0);
    System.out.println(result53);
    //  Le réseau contient donc un circuit à coût négatif accessible depuis le sommet 1.


  }
}
