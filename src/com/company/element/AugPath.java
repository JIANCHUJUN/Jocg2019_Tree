package com.company.element;

import java.util.LinkedList;

public class AugPath {
    public LinkedList<Vertex> path;
    public AugPath(){
        path = new LinkedList<>();
    }

    public void add(Vertex v){
        path.addLast(v);
    }
    public void reverse_add(Vertex v) {path.addFirst(v);}
    public void printPath(){
        for(Vertex v:path){
            System.out.print(v.ID + "-->");
        }
        System.out.println("");
    }

    Boolean check(){

        for(int i = 0; i < path.size(); i++){
            if((i%2 == 0 && path.get(i).label == Label.B) || (i%2 == 1 && path.get(i).label == Label.A)){
                return false;
            }
            if(i < path.size() - 1){
                if(path.get(i).label == Label.B && path.get(i).getMatch() != path.get(i+1)){
                    return false;
                }
            }
        }
        return true;
    }

}
