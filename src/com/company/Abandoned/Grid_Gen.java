package com.company.Abandoned;

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

//    public Graph generator(int height, int width, long seed){
//        /*
//         * 规定长宽
//         */
//        assert height*width == 2*num_vertex_each_label;
//
//        /*
//         * 初始换vertex id，前一半是A后一半是B
//         */
//        ArrayList<Integer> vertex_ids = new ArrayList<>();
//        for(int i = 0; i < 2*num_vertex_each_label; i++){
//            vertex_ids.add(i);
//        }
//
//        /*
//         * 随机打乱id顺序
//         * 这个Array是用来记载Vertex的顺序的
//         */
//        Random r = new Random(seed);
//        Collections.shuffle(vertex_ids,r);
//
//        /*
//         * 生成相应的vertex object
//         * 插入HashMap
//         */
//        HashMap<Integer,Vertex> vertex_map = new HashMap<>();
//        for(int id:vertex_ids){
//            if(id < num_vertex_each_label){
//                vertex_map.put(id,new Vertex(id,null, Label.A));
//            }
//            else{
//                vertex_map.put(id,new Vertex(id,null, Label.B));
//            }
//        }
//
//        /*
//         * 存入edge
//         */
//        for(int id:vertex_ids){
//            ArrayList<Integer> neighbors = get_neighbor(vertex_ids,id,width);
//            HashSet<Vertex> edges = new HashSet<>();
//            for(int i:neighbors){
//                edges.add(vertex_map.get(i));
//            }
//            vertex_map.get(id).setUnmatching_edges(edges);
//        }
//
//        /*
//         * 生成一个vertex list，list.get(x)得到的是id为x的vertex
//         */
//        HashSet<Vertex> vertex_list = new HashSet<>();
//        for(int i = 0; i < 2*num_vertex_each_label; i++){
//            vertex_list.add(vertex_map.get(i));
//        }
//        print(vertex_ids,width);
//        return new Graph(vertex_list,null);
//    }

    private ArrayList<Integer> get_neighbor(ArrayList<Integer> vertex_ids, int loc, int width){
        assert loc >= 0 && loc < vertex_ids.size();
        ArrayList<Integer> result = new ArrayList<>();
        if(loc+width < vertex_ids.size()){
            result.add(loc+width);
        }
        if(loc-width >= 0){
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

    private void print(ArrayList<Integer> vertex_ids,int width){
        for(int i = 0; i < vertex_ids.size(); i++){
            if(i%width == 0){
                System.out.print("\n");
            }
            System.out.printf("%2d ",vertex_ids.get(i));
        }
        System.out.println("");
    }

}
