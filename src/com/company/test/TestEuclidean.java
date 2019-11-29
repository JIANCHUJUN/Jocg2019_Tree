package com.company.test;

import com.company.algorithm.Hop;
import com.company.algorithm.JOCG;
import com.company.element.Graph;
import com.company.element.MatchingTable;
import com.company.generator.EuclideanGen;

public class TestEuclidean {
    public TestEuclidean(){

    }

    public static void test(){
        EuclideanGen euclideanGen = new EuclideanGen(100);
        Graph graph = euclideanGen.generate(100,80,5);

        Hop hop = new Hop(graph);
        MatchingTable matchingTable_Hop = hop.hop_();
        matchingTable_Hop.printMatching();
        matchingTable_Hop.reset();

        matchingTable_Hop.printMatching();

        MatchingTable matchingTable_Jocg = JOCG.Jocg(graph);
        matchingTable_Jocg.printMatching();


    }

    public static void testGenerate(){
        for(int i = 10; i < 20; i ++){
            int side = 16*i;
            int bottlenect = i;
            EuclideanGen euclideanGen = new EuclideanGen(side);
            Graph graph = euclideanGen.generate(side,side,bottlenect);
        }
    }
}
