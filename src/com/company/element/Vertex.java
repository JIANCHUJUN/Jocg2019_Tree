package com.company.element;

import java.util.HashSet;

public class Vertex {

    int ID;
    Label label;
    public HashSet<Vertex> edges; // this attribute should never been changed;
    public Vertex matching_vertex;

    public Vertex(int id, HashSet<Vertex> edges, Label label){
        this.ID = id;
        this.edges = edges;
        this.label = label;
        this.matching_vertex = null;
    }

    public int getID(){
        return ID;
    }
    public Label getLabel(){
        return label;
    }

    public Vertex getMatch(){
        return matching_vertex;
    }

    void direct_match(Vertex x){
        x.matching_vertex = this;
        this.matching_vertex = x;
    }
    public boolean adj_to(Vertex x){
        return edges.contains(x) && x.edges.contains(this);
    }

    public boolean isFree(){
        return matching_vertex == null;
    }

    public HashSet<Vertex> edges() {
        return new HashSet<>(edges);
    }

}
