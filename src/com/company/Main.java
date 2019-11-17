package com.company;

import com.company.algorithm.Harp;
import com.company.algorithm.Hop;
import com.company.element.Graph;
import com.company.element.MatchingTable;
import com.company.generator.Random_Perfect_Gen;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        System.out.println("Hello World!");
//        for(int i = 10; i < 5000; i++){
//            System.out.println(i+" :---------------------------");
//            Random_Perfect_Gen gen = new Random_Perfect_Gen(i);
//            Graph graph = gen.generate(0);
//            Hop hop = new Hop(graph);
//            MatchingTable matchingTable =  hop.hop_();
//            matchingTable.printMatching();
//        }

        Random_Perfect_Gen gen = new Random_Perfect_Gen(100000);
        Graph graph = gen.generate(0);
        Hop hop = new Hop(graph);
        MatchingTable matchingTable =  hop.hop_();
        matchingTable.printMatching();


        System.out.println("Done!");

    }
}



