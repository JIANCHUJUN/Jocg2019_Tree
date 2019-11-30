package com.company.test;

import com.company.algorithm.Hop;
import com.company.algorithm.JOCG;
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
        for(int i = 100; i < 5000; i+=100){
            double timeElapsedHop = 0;
            double timeElapsedJocg = 0;
            int times = 5;
            for(int time = 0; time < times; time++){
                EuclideanGen euclideanGen = new EuclideanGen(100);
                Graph graph = euclideanGen.generate(i,80,5);

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

            double ratio = Math.log(timeElapsedJocg/timeElapsedHop);
            System.out.println("Jocg: " + timeElapsedJocg);
            System.out.println("Hop: " + timeElapsedHop);
            System.out.println("Jocg/Hop: " + ratio);
            writer.append(i + " " + ratio + '\n');
        }
        writer.close();
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
