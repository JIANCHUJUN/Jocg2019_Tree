package com.company.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MatchingTable {


    public Graph graph;
    int matching_num;

    public MatchingTable(Graph graph){
        this.graph = graph;
        this.matching_num= 0;
    }

    public void reset(){
        for(Vertex vertex:graph.get_vertexes()){
            vertex.matching_vertex = null;
        }
    }


    /*
     * 返回一个包含free vertex的List
     * 如果label是null，返回所有free vertex
     * 否则返回对应的free vertex
     */
    public HashSet<Vertex> all_free(Label label){ //opt
        /*
         * iterate all vertexes and find free vertex
         */
        HashSet<Vertex> frees = new HashSet<>();
        for(Vertex v:graph.get_vertexes()){
            if(v.isFree() && (v.label == label || label == null)){
                frees.add(v);
            }
        }
        return frees;
    }

    public HashSet<Vertex> all(Label label){
        HashSet<Vertex> result = new HashSet<>();
        for(Vertex v:graph.get_vertexes()){
            if(v.label == label){
                result.add(v);
            }
        }
        return result;
    }



    public void match(Vertex x, Vertex y){
        x.direct_match(y);
    }


    public void summary(){
        System.out.println("Free A: "+ all_free(Label.A).size());
        System.out.println("Free B: "+ all_free(Label.B).size());

        printMatching();
    }

    public int printMatching(){
        int count = 0;
        for(Vertex v:graph.get_vertexes()){
            if(v.label == Label.A && !v.isFree()){
                //System.out.println(v.ID+"-->"+v.getMatch().ID);
                count+=1;
            }
        }
        //System.out.println("Max Matching: " + count);
        return count;
    }

}
