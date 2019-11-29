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
        for(int i = 100; i < 5000; i+=100){
            EuclideanGen euclideanGen = new EuclideanGen(100);
            Graph graph = euclideanGen.generate(i,80,5);

            Hop hop = new Hop(graph);
            MatchingTable matchingTable_Hop = hop.hop_();
            int counth = matchingTable_Hop.printMatching();
            matchingTable_Hop.reset();

            matchingTable_Hop.printMatching();

            MatchingTable matchingTable_Jocg = JOCG.Jocg(graph);
            int countj = matchingTable_Jocg.printMatching();

            assert counth == countj;
        }



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
