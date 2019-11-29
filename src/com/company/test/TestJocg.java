package com.company.test;

import com.company.algorithm.JOCG;
import com.company.element.Graph;
import com.company.element.MatchingTable;
import com.company.element.Vertex;
import com.company.generator.Random_Perfect_Gen;
import com.company.generator.WeightAssigner;

import java.util.HashMap;
import java.util.HashSet;

public class TestJocg {

    public static void test(){
        for(int i = 20; i < 1000;i++){
            int HALFV = i;
//            Random_Perfect_Gen gen = new Random_Perfect_Gen(HALFV);
//            Graph graph = gen.generate(0);
//            WeightAssigner wa = new WeightAssigner();
//            HashMap<Vertex, HashSet<Vertex>> ws = wa.bfsAssigner(graph,HALFV/8);
//            graph.separator = ws;
//            MatchingTable matchingTable = JOCG.Jocg(graph);
//            matchingTable.printMatching();
//            assert matchingTable.all_free(null).size() == 0;
        }

    }
}
