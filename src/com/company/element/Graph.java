package com.company.element;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;


public class Graph {

    private HashSet<Vertex> vertexes;
    /*
     * weights只储存weight为1的edge
     */
    public HashMap<Vertex,HashSet<Vertex>> weights;

    public Graph(HashSet<Vertex> vs, HashMap<Vertex,HashSet<Vertex>> ws){
        this.vertexes = vs;
        this.weights = ws;
    }


    public int getWeight(Vertex v1, Vertex v2){
        /*
         * 因为我们不知道顺序，所以t1 t2都要检测一下
         */
        if(!v1.adj_to(v2)){
            System.out.println("Trying to get weight of an edge that doesn't exist.");
            throw new NullPointerException();
        }
        if(weights.containsKey(v1) && weights.get(v1).contains(v2)){
            return 1;
        }
        if(weights.containsKey(v2) && weights.get(v2).contains(v1)){
            return 1;
        }

        return 0;

    }

    public HashSet<Vertex> get_vertexes(){
        return vertexes;
    }

    /*
     * split the graph by 0 - 1 weights
     */
    HashSet<Graph> split(){

        /*
         * 遍历所有的vertex
         * 如果一个vertex没被探索过，则对它用bfs
         * bfs返回所有以weight 0 edge相连接的vertex
         * 并把他们都加进explored，更新explored
         * 这些相连接的vertex组合成一个sub-graph
         * 放入pieces中
         * 最后返回pieces
         */
        HashSet<Graph> pieces = new HashSet<>();
        HashSet<Vertex> explored = new HashSet<>();

        for(Vertex v:vertexes){
            if(!explored.contains(v)){
                pieces.add(bfs(v,explored));
            }
        }
        return pieces;
    }

    /*
     * 从root出发，用bfs遍历所有的vertex
     * explored中的vertex不会被包含
     * edge weight为1的不会被包含
     */
    private Graph bfs(Vertex root, HashSet<Vertex> explored){
        LinkedList<Vertex> queue = new LinkedList<>();
        HashSet<Vertex> vertexHashSet = new HashSet<>();
        queue.addLast(root);
        while (!queue.isEmpty()){
            Vertex v = queue.pop();
            vertexHashSet.add(v);
            for(Vertex u:v.edges()){
                /*
                 * 如果u没被探索过，且（u，v）的weight为0
                 * 则把u加入queue中
                 */
                if(!explored.contains(u) && getWeight(u,v) == 0){
                    queue.addLast(u);
                    explored.add(u);
                }
            }
        }
        return new Graph(vertexHashSet,null);
    }
}
