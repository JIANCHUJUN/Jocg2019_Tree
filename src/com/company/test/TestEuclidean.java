package com.company.test;

import com.company.algorithm.Hop;
import com.company.algorithm.JOCG;
import com.company.algorithm.JOCG_N;
import com.company.element.Graph;
import com.company.element.MatchingTable;
import com.company.generator.EuclideanGen;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TestEuclidean {
    public TestEuclidean(){

    }

    public static void test() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("EculideanOut.txt"));
        writer.write("");
        for(int i = 200; i < 3000; i+=100){
            double timeElapsedHop = 0;
            double timeElapsedJocg = 0;
            int times = 5;
            for(int time = 0; time < times; time++){
                EuclideanGen euclideanGen = new EuclideanGen(100);
                Graph graph = euclideanGen.generate(i,80,10,1,1);

                long starth = System.currentTimeMillis();
                Hop hop = new Hop(graph);
                MatchingTable matchingTable_Hop = hop.hop_();
                int counth = matchingTable_Hop.printMatching();
                long finishh = System.currentTimeMillis();
                timeElapsedHop += finishh - starth;


                matchingTable_Hop.reset();

                //matchingTable_Hop.printMatching();

                long startj = System.currentTimeMillis();
                MatchingTable matchingTable_Jocg = JOCG.Jocg(graph);
                int countj = matchingTable_Jocg.printMatching();
                long finishj = System.currentTimeMillis();
                timeElapsedJocg += finishj - startj;
                assert counth == countj;
            }
            timeElapsedHop /= times;
            timeElapsedJocg /= times;

            double ratio = timeElapsedJocg/timeElapsedHop;
            System.out.println("Jocg: " + timeElapsedJocg);
            System.out.println("Hop: " + timeElapsedHop);
            System.out.println("Jocg/Hop: " + ratio);
            writer.append(i + " " + ratio + '\n');
        }
        writer.close();
    }

    public static void testGen(){
        for(int i = 1000; i < 3000; i+=100){
            EuclideanGen euclideanGen = new EuclideanGen(2131);
            Graph graph = euclideanGen.generate(i,80,40,0.5,4);
            MatchingTable matchingTable = new MatchingTable(graph);
            int smallestSub = Integer.MAX_VALUE;
            for(Graph sub:graph.pieces){
                if(sub.vertexes.size() < smallestSub){
                    smallestSub = sub.vertexes.size();
                }
            }
            System.out.println(smallestSub + " " + graph.vertexes.size());
            System.out.println(i + ": "+(double) smallestSub/graph.vertexes.size()*100 + "%");
            int a = 0;
        }
    }
}
