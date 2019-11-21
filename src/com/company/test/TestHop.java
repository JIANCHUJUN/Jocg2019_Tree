package com.company.test;

import com.company.algorithm.Hop;
import com.company.element.Graph;
import com.company.element.MatchingTable;
import com.company.element.Vertex;
import com.company.generator.Random_Perfect_Gen;

public class TestHop {

    public static void test(){
        for(int i = 10; i < 1001; i++){
            System.out.println(i+" :---------------------------");
            Random_Perfect_Gen gen = new Random_Perfect_Gen(i);
            Graph graph = gen.generate(321);

//            for(Vertex v:graph.get_vertexes()){
//                System.out.print(v.getID() + ": ");
//                for(Vertex u:v.edges()){
//                    System.out.print(u.getID() + " ");
//                }
//                System.out.println("");
//
//            }

            Hop hop = new Hop(graph);
            MatchingTable matchingTable =  hop.hop_();
            matchingTable.summary();
            assert matchingTable.all_free(null).size() == 0;
        }
    }
}
