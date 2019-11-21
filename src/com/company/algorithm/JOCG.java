package com.company.algorithm;

import com.company.element.Graph;
import com.company.element.Label;
import com.company.element.MatchingTable;
import com.company.element.Vertex;
import com.company.test.TestSplitGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class JOCG {

    /*
     * create matching_table
     *
     */
    static int INF = Integer.MAX_VALUE;
    static HashMap<Vertex,Integer> dist;
    static MatchingTable matchingTable;

    public static MatchingTable Jocg(Graph graph){
        matchingTable = new MatchingTable(graph);
        HashSet<Graph> pieces = matchingTable.splitGraph();

//        TestSplitGraph.Test(pieces,graph.get_vertexes().size(),graph);
//        System.out.println("Test Split Finished");
        /*
         * 刚split过后，所有的weight为1的edge已经被移除了
         */

        for(Graph g: pieces){
            Hop hop = new Hop(g);
            hop.hop_();
        }

        System.out.println("HKs Finished");

        while(bfs()){
            System.out.println("In loop");
            for(Vertex v:matchingTable.all_free(Label.A)){
                dfs(v);
            }
        }

        return matchingTable;
    }

    private static boolean bfs(){
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
                    if(dist.get(u.getMatch()) == INF){
                        dist.put(u.getMatch(), dist.get(v) + 1);
                        queue.addLast(u.getMatch());
                    }
                }
            }
        }
        return dist.get(null) != INF;
    }

    private static boolean dfs(Vertex v){
        /*
         * 一个free B的get match是null
         * 所以null的时候说明找到了一个free B
         */
        if(v == null){
            return true;
        }

        for(Vertex u:v.edges()){
            if(dist.get(u.getMatch()) == dist.get(v) + 1){
                if(dfs(u.getMatch())){
                    matchingTable.match(u,v);
                    return true;
                }
            }
        }
        dist.put(v,INF);
        return false;

    }
}
