package com.company.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MatchingTable {


    private Graph graph;
    private List<Graph> pieces;
    private HashMap<Vertex,Integer> piece_table;
    int matching_num;

    public MatchingTable(Graph graph){
        this.graph = graph;
        this.matching_num= 0;
    }

    /*
     * given a vertex
     * return the id of pieces where this vertex is in
     */
    private int getPiece(Vertex v){
        return piece_table.get(v);
    }

    private void remove_temp_edge(Vertex v1, Vertex v2){
        if(!v1.temp_unmatching_edges.contains(v2) || !v2.temp_unmatching_edges.contains(v1)){
            System.out.println("Trying to remove an edge that doesn't exist.");
            throw new NullPointerException();
        }
        v1.temp_unmatching_edges.remove(v2);
        v2.temp_unmatching_edges.remove(v1);
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
    public List<Graph> splitGraph(){
        this.piece_table = new HashMap<>();
        this.pieces = graph.split();
        for(int i = 0; i < pieces.size(); i++){
            for(Vertex v:pieces.get(i).getVertexes()){
                piece_table.put(v,i);
            }
        }
        return pieces;
    }

    /*
     * 处理 augmenting path
     * 把在useless_Path里多余的边去掉
     */
    public void augment_clear(List<AugPath> useless_Path, AugPath augpath){

        if(!augpath.check()){
            System.out.println("This is not an augmenting path.");
            throw new NullPointerException();
        }

        /*
         * record the affected pieces
         */
        HashSet<Integer> effective_pieces = new HashSet<>();
        for(Vertex v:augpath.path){
            effective_pieces.add(getPiece(v));
        }

        /*
         * augment the augmenting path
         */

        augment(augpath);

        /*
         * remove useless edges
         */

        for(AugPath path:useless_Path){
            Vertex head = path.path.pop();
            while(!path.path.isEmpty()){
                Vertex tail = path.path.pop();
                if(!effective_pieces.contains(getPiece(head)) || !effective_pieces.contains(getPiece(tail))){
                    remove_temp_edge(head,tail);
                }
                head = tail;
            }
        }

    }


    public void augment_remove(HashSet<Vertex> used, AugPath augpath){
        if(augpath != null){
            if(!augpath.check()){
                System.out.println("This is not an augmenting path.");
                throw new NullPointerException();
            }
            for(Vertex v:augpath.path){
                used.remove(v);
            }
            augment(augpath);
        }
        for(Vertex v:used){
            for(Vertex child:v.getTempUnmatch()){
                child.getTempUnmatch().remove(v);
            }
        }
    }

    private void augment(AugPath augpath){
        Vertex curr = augpath.path.pop();
        assert curr.isFree():"head is not free";
        boolean matching = true;
        while (!augpath.path.isEmpty()){
            Vertex next = augpath.path.pop();
            if(matching){
                match(curr,next);
            }
            else{
                unmatch(curr,next);
            }
            matching = !matching;
            curr = next;
        }
        assert !matching;
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
        for(Vertex v:graph.getVertexes()){
            if(v.isFree() && (v.label == label || label == null)){
                frees.add(v);
            }
        }
        return frees;
    }


    public void restore(){
        for(Vertex v:graph.getVertexes()){
            v.restoreTemp();
        }
    }

    public void match(Vertex x, Vertex y){
        x.match(y);
    }

    public void unmatch(Vertex x, Vertex y){
        if(exist(x,y)){
            x.unmatch();
        }
    }

    public boolean exist(Vertex x,Vertex y){
        return (x.getMatch() == y && y.getMatch() == x);
    }

    public void printMatching(){
        for(Vertex v:graph.get_vertexes()){
            if(v.label == Label.A){
                System.out.println(v.ID+"-->"+v.getMatch().ID);
            }
        }
    }

    public boolean finish(){
        return all_free(Label.A).size() == 0;
    }
}
