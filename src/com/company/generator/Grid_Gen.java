package com.company.generator;

import com.company.element.Graph;
import com.company.element.Label;
import com.company.element.Vertex;

import java.util.*;

/*
 * generate a n-d grid graph
 */
public class Grid_Gen {

    int num_vertex_each_label;
    boolean perfect;

    public Grid_Gen(int n){
        num_vertex_each_label = n;
    }

    public Graph generator(int height, int width, long seed){
        assert height*width == 2*num_vertex_each_label;
        List<Integer> vertex_ids = new ArrayList<>();
        for(int i = 0; i < 2*num_vertex_each_label; i++){
            vertex_ids.add(i);
        }
        Random r = new Random(seed);
        Collections.shuffle(vertex_ids,r);
        HashMap<Integer,Vertex> vertexes = new HashMap<>();
        for(int id:vertex_ids){
            if(id < num_vertex_each_label){
                vertexes.put(id,new Vertex(id,null, Label.A));
            }
        }



        return null;
    }

    private ArrayList<Integer> get_neighbor(ArrayList<Integer> vertex_ids, int loc, int width){
        assert loc >= 0 && loc < vertex_ids.size();
        ArrayList<Integer> result = new ArrayList<>();
        if(loc+width < vertex_ids.size()){
            result.add(loc+width);
        }
        if(loc-width > 0){
            result.add(loc-width);
        }
        if(loc%width != 0 && loc - 1 >= 0){
            result.add(loc - 1);
        }
        if(loc+1 % width != 0 && loc + 1 < vertex_ids.size()){
            result.add(loc + 1);
        }
        return result;
    }

}
