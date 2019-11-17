package com.company.algorithm;

import com.company.element.Graph;
import com.company.element.MatchingTable;

import java.util.List;

public class JOCG {

    /*
     * create matching_table
     *
     */



    public Graph convert_to_graph(){
        return null;
    }

    public MatchingTable Jocg(){

        Graph graph = convert_to_graph();
        MatchingTable matchingTable = new MatchingTable(graph);
        List<Graph> pieces = matchingTable.splitGraph();
        /*
         * 刚split过后，所有的weight为1的edge已经被移除了
         */

        for(Graph g: pieces){
            Harp harp = new Harp(g);
            harp.Harp_();
        }

        while(!matchingTable.finish()){
            int length = BFS.find_all_free_0_1_graph(graph,matchingTable);
            DFS.augment_all_free(graph,matchingTable,length);
        }

        return matchingTable;
    }
}
