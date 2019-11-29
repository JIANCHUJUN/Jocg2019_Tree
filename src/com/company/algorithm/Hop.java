package com.company.algorithm;

import com.company.element.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Hop {

    private HashMap<Vertex, ArrayList<Vertex>> temp_store;
    private MatchingTable matchingTable;
    private Graph graph;
    private HashMap<Vertex,Integer> dist;
    private Integer INF;
    static private int di;

    public Hop(Graph sub_graph){
        this.INF = Integer.MAX_VALUE;
        this.graph = sub_graph;
        this.matchingTable = new MatchingTable(graph);
    }

    public MatchingTable hop_(){
        /*
         * we assume the perfect matching exists
         */
        while(bfs()){
            for(Vertex v:matchingTable.all_free(Label.A)){
                assert v.getMatch() == null;
                dfs(v);
            }
            //restore();
        }
        return matchingTable;
    }

    private boolean bfs(){
        LinkedList<Vertex> queue = new LinkedList<>();
        dist = new HashMap<>();
        dist.put(null,INF);
        for(Vertex v:matchingTable.all(Label.A)){
            if(v.isFree()){
                queue.addLast(v); dist.put(v,0);
            }
            else{
                dist.put(v,INF);
            }
        }
        while (!queue.isEmpty()){
            Vertex v = queue.pop();
            /*
             * 之后到达null的路径不会再更新dist[null]
             */
            if(dist.get(v) < dist.get(null)){
                for(Vertex u:v.edges()){
                    /*
                     * 第一个到达null的路径
                     * 会set dist[null] 为最短路径的值
                     */
                    if(graph.get_vertexes().contains(u)){
                        if(dist.get(u.getMatch()) == INF){
                            dist.put(u.getMatch(), dist.get(v) + 1);
                            queue.addLast(u.getMatch());
                        }
                    }
                }
            }
        }
        return dist.get(null) != INF;
    }

    private boolean dfs(Vertex v){
        /*
         * 一个free B的get match是null
         * 所以null的时候说明找到了一个free B
         */
        if(v == null){
            return true;
        }

        for(Vertex u:v.edges()){
            //System.out.println(v.getID() + " " + u.getID());
            assert dist != null;
            assert u != null;
            //assert dist.containsKey(u.getMatch());
            if(!dist.containsKey(u.getMatch())){
                int a = 0;
            }
            if(graph.get_vertexes().contains(u)){
                if(dist.get(u.getMatch()) == dist.get(v) + 1){
                    if(dfs(u.getMatch())){
                        matchingTable.match(u,v);
                        //System.out.println(v.getID() + " " + u.getID());
                        return true;
                    }
                }
            }

        }
        dist.put(v,INF);
        return false;

    }



}
