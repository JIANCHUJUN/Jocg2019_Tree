package com.company.element;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Vertex {

    int ID;
    Label label;
    public HashSet<Vertex> unmatching_edges; // this attribute should never been changed;
    private Vertex matching_vertex;
    /*
     * matching_vertex不能包含在temp_unmatching_edges中，需要检查
     */
    HashSet<Vertex> temp_unmatching_edges;
    public Vertex(int id, HashSet<Vertex> edges, Label label){
        this.ID = id;
        this.unmatching_edges = edges;
        this.label = label;
        this.matching_vertex = null;
        this.temp_unmatching_edges = new HashSet<>();
    }

    public int getID(){
        return ID;
    }
    public Label getLabel(){
        return label;
    }

    public HashSet<Vertex> getTempUnmatch(){
        return temp_unmatching_edges;
    }



    public Vertex getMatch(){
        return matching_vertex;
    }

    public void match(Vertex x){
        if(x.label == label){
            System.out.println("Trying to matching two vertexes with same labels");
            throw new NullPointerException();
        }
        if(!adj_to(x) || !x.adj_to(this)){
            System.out.println("Trying to matching two vertexes not connected");
            throw new NullPointerException();
        }
        this.direct_match(x);
        x.direct_match(this);
    }

    public void unmatch(){
        matching_vertex.matching_vertex = null;
        matching_vertex = null;
    }

    public void setTemp_unmatching_edges(HashSet<Vertex> temp_unmatching_edges) {
        this.temp_unmatching_edges = temp_unmatching_edges;
    }

    void direct_match(Vertex x){
        x.matching_vertex = this;
        this.matching_vertex = x;
    }

    public boolean adj_to(Vertex x){
        return unmatching_edges.contains(x);
    }

    public boolean isFree(){
        return matching_vertex == null;
    }

    public HashSet<Vertex> get_adj() {
        return new HashSet<>(unmatching_edges);
    }

    public void restoreTemp(){
        temp_unmatching_edges = new HashSet<>();
        temp_unmatching_edges.addAll(unmatching_edges);
        temp_unmatching_edges.remove(matching_vertex);
    }

    public void restoreTemp(ArrayList<Vertex> t){
        temp_unmatching_edges = new HashSet<>(t);
        temp_unmatching_edges.remove(matching_vertex);
    }

    public void combine_temp(){
        unmatching_edges.addAll(temp_unmatching_edges);
    }

    public HashSet<Vertex> get_children(Label lb){
        /*
         * lb规定了是从~lb指向lb
         * 如果lb是B
         * 那么augment path中就是从A指向B
         * 那往回（从B到A）找的时候，遇见B的话就return unmatched A
         * 遇见A的话就return matched B
         * A --> B - > A --> B
         * 虚线，实线，虚线
         */
        HashSet<Vertex> result = new HashSet<>();
        if(lb == label){
            result.addAll(temp_unmatching_edges);
            result.remove(matching_vertex);
        }
        else{
            result.add(matching_vertex);
        }
        return result;
    }

    public void setUnmatching_edges(HashSet<Vertex> edges){
        assert unmatching_edges == null;
        unmatching_edges = edges;
    }

}
