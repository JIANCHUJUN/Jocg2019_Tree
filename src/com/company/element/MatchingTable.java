package com.company.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MatchingTable {


    private Graph graph;
    private HashSet<Graph> pieces;
    private HashMap<Vertex,Graph> piece_table;
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

    public int getWeight(Vertex v, Vertex u){
        return graph.getWeight(v,u);
    }

    /*
     * given a vertex
     * return the id of pieces where this vertex is in
     */
    public Graph getPiece(Vertex v){
        return piece_table.get(v);
    }


    /*
     * 返回v匹配的vertex，如果没有则是null
     */
    public Vertex get(Vertex v){
        return v.getMatch();
    }

    /*
     * 返回原graph的sub-graph
     * 返回类型为List<Graph>
     */
    public HashSet<Graph> splitGraph(){
        this.piece_table = new HashMap<>();
        this.pieces = graph.split();
        for(Graph subgraph:this.pieces){
            for(Vertex v:subgraph.get_vertexes()){
                piece_table.put(v,subgraph);
            }
        }
        return pieces;
    }

    /*
     * 处理 augmenting path
     * 把在useless_Path里多余的边去掉
     */
//    public void augment_clear(List<AugPath> useless_Path, AugPath augpath){
//
//        if(!augpath.check()){
//            System.out.println("This is not an augmenting path.");
//            throw new NullPointerException();
//        }
//
//        /*
//         * record the affected pieces
//         */
//        HashSet<Graph> effective_pieces = new HashSet<>();
//        for(Vertex v:augpath.path){
//            effective_pieces.add(getPiece(v));
//        }
//
//        /*
//         * augment the augmenting path
//         */
//
//        augment(augpath);
//
//        /*
//         * remove useless edges
//         */
//
//        for(AugPath path:useless_Path){
//            Vertex head = path.path.pop();
//            while(!path.path.isEmpty()){
//                Vertex tail = path.path.pop();
//                if(!effective_pieces.contains(getPiece(head)) || !effective_pieces.contains(getPiece(tail))){
//                    remove_temp_edge(head,tail);
//                }
//                head = tail;
//            }
//        }
//
//    }
//
//
//    private void augment(AugPath augpath){
//        Vertex curr = augpath.path.pop();
//        assert curr.isFree():"head is not free";
//        boolean matching = true;
//        while (!augpath.path.isEmpty()){
//            Vertex next = augpath.path.pop();
//            if(matching){
//                match(curr,next);
//            }
//            else{
//                unmatch(curr,next);
//            }
//            matching = !matching;
//            curr = next;
//        }
//        assert !matching;
//    }

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

    public boolean exist(Vertex x,Vertex y){
        return (x.getMatch() == y && y.getMatch() == x);
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
        System.out.println("Max Matching: " + count);
        return count;
    }

    public boolean finish(){
        return (all_free(Label.A).size() == 0 || all_free(Label.B).size() == 0) ;
    }
}
