package com.company.element;

import com.company.algorithm.BFS;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Graph {

    private List<Vertex> vertexes;
    private HashMap<Vertex[],Integer> weights;

    public Graph(List<Vertex> vs, HashMap<Vertex[], Integer> ws){
        this.vertexes = vs;
        this.weights = ws;
    }

    int getNum_vertexes(){
        return vertexes.size();
    }

    List<Vertex> getVertexes(){
        return vertexes;
    }

    public int getWeight(Vertex v1, Vertex v2){
        Vertex[] t1 = new Vertex[]{v1,v2};
        Vertex[] t2 = new Vertex[]{v2,v1};
        if(weights.containsKey(t1)){
            return weights.get(t1);
        }
        else if(weights.containsKey(t2)){
            return weights.get(t2);
        }
        else{
            System.out.println("Trying to get weight of an edge that doesn't exist.");
            throw new NullPointerException();
        }

    }

    public List<Vertex> get_vertexes(){
        return vertexes;
    }

    /*
     * split the graph by 0 - 1 weights
     */
    List<Graph> split(){

        /*
         * 移除所有weight为1的edge
         */

        for(Vertex vx:vertexes){
            vx.restoreTemp();
            for(Vertex vy:vx.temp_unmatching_edges){
                if(getWeight(vx,vy) == 1){
                    vx.temp_unmatching_edges.remove(vy);
                }
            }
        }

        /*
         * 遍历所有的vertex
         * 如果一个vertex没被探索过，则对它用bfs
         * bfs返回所有以weight 0 edge相连接的vertex
         * 并把他们都加进explored，更新explored
         * 这些相连接的vertex组合成一个sub-graph
         * 放入pieces中
         * 最后返回pieces
         */

        ArrayList<Graph> pieces = new ArrayList<>();

        HashSet<Vertex> explored = new HashSet<>();
        for(Vertex v:vertexes){
            if(!explored.contains(v)){
                pieces.add(new Graph(BFS.bfs(v,explored),null));
            }
        }
        return pieces;
    }
}
