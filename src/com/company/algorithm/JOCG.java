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

    static HashSet<Vertex> explored;
    static HashSet<Vertex> tempexpl;
    static HashSet<Vertex> temppath;

    public static MatchingTable Jocg(Graph graph){

        long start = System.currentTimeMillis();

        matchingTable = new MatchingTable(graph);
        HashSet<Graph> pieces =graph.pieces;

        long splitEnd = System.currentTimeMillis();

        /*
         * 刚split过后，所有的weight为1的edge已经被移除了
         */

        for(Graph g: pieces){
            Hop hop = new Hop(g);
            hop.hop_();
        }

        long hkEnd = System.currentTimeMillis();
        //System.out.println("HKs Finished");

        while(bfs()){
            //System.out.println("In loop");
            explored = new HashSet<>();
            for(Vertex v:matchingTable.all_free(Label.A)){
                tempexpl = new HashSet<>();
                temppath = new HashSet<>();
                dfs(v);

                HashSet<Graph> effectiveP = new HashSet<>();
                for(Vertex p:temppath){
                    effectiveP.add(graph.piecesTable.get(p));
                }
                for(Vertex t:tempexpl){
                    if(effectiveP.contains(graph.piecesTable.get(t))){
                        explored.remove(t);
                    }
                }
            }
        }

        long bdfsEnd = System.currentTimeMillis();

        double splitDur = (double) (splitEnd- start)/(bdfsEnd - start) * 100;
        double hkDur = (double) (hkEnd - splitEnd)/(bdfsEnd - start) * 100;
        double bdfsDur = (double) (bdfsEnd -hkEnd)/(bdfsEnd - start) * 100;
        System.out.println("split takes: " + splitDur + "%");
        System.out.println("hk takes: " + hkDur + "%");
        System.out.println("bdfs takes: " + bdfsDur + "%");

        return matchingTable;
    }

    private static boolean bfs(){
        LinkedList<Vertex> queue = new LinkedList<>();
        dist = new HashMap<>();
        dist.put(null,INF);
        //这里要用double ended queue
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
             * 只会更新距离等于最短距离的v
             * 因为0/1中，距离不一定总是增加（可以不变），所以用<=
             */
            if(dist.get(v) <= dist.get(null)){
                for(Vertex u:v.edges()){
                    /*
                     * 第一个到达null的路径
                     * 会set dist[null] 为最短路径的值
                     */
                    /*
                     * u.getMatch()的意思：
                     * v是A，u是B
                     * u的距离程序是不记录的，而u的match是A或者无
                     * 如果是无的话，则说明u是free
                     * 否则查看u.getMatch()的距离
                     * 如果是INF，说明未被探索过，目前是最短距离
                     * 否则的话说明之前assign的距离为最短距离，不作更新
                     */
                    if(dist.get(u.getMatch()) == INF){
                        //getMatch == null，所以探索结束，
                        if(u.getMatch() == null){
                            dist.put(u.getMatch(), dist.get(v));
                        }
                        else{
                            //getMatch!=null，所以根据weight更新距离
                            dist.put(u.getMatch(), dist.get(v) + matchingTable.graph.getWeight(u,u.getMatch()));
                            if(matchingTable.graph.getWeight(u,u.getMatch()) == 0){
                                queue.addLast(u.getMatch());
                            }
                            else{
                                queue.addFirst(u.getMatch());
                            }
                        }
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
        if(explored.contains(v)){
            return false;
        }
        explored.add(v);
        tempexpl.add(v);
        if(v == null){
            return true;
        }

        LinkedList<Vertex> stack = new LinkedList<>();
        for(Vertex u:v.edges()){
            if(dist.get(u.getMatch()) == dist.get(v) + 1){
                stack.addFirst(u);
            }
            else if(dist.get(u.getMatch()) == dist.get(v)){
                stack.addLast(u);
            }
        }

        for(Vertex u:stack){
            if(dfs(u.getMatch())){
                temppath.add(v);
                matchingTable.match(u,v);
                return true;
            }

        }
        dist.put(v,INF);
        return false;

    }
}
