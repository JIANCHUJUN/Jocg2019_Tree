package com.company.generator;

import com.company.element.Graph;
import com.company.element.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class WeightAssigner {

    public WeightAssigner(){

    }

    public HashMap<Vertex,HashSet<Vertex>> bfsAssigner(Graph graph, int num){
        /*
         * vset = Set()
         * vset.addall(graph.vertexes)
         *
         * ws = HashMap(Vertex[], int)
         * while(vset.isnotempty()):
         *      root = vset.pop()
         *      nodes = root.bfs(vset,num)
         *      assignWeight(ws,nodes,1)
         *      vset.removeall(nodes)
         *
         * return ws
         */
        LinkedList<Vertex> vset = new LinkedList<>(graph.get_vertexes());
        HashSet<Vertex> explored = new HashSet<>();
        HashMap<Vertex,HashSet<Vertex>> ws = new HashMap<>();

        while(!vset.isEmpty()){
            Vertex root = vset.pop();
            /*
             * used will be updated in bfs
             */
            HashSet<Vertex> nodes = bfs(root,explored,num);
            assignWeight(ws,nodes);
            vset.removeAll(nodes);

        }
        return ws;
    }

    /*
     * 用bfs返回一个|v|大于等于num的piece
     */
    private HashSet<Vertex> bfs(Vertex root, HashSet<Vertex> explored, int num){
        /*
         * explored包含了在之前bfs中探索过的vertex
         * 在这次bfs中explored也被持续更新
         * nodes只包含这次bfs中探索过的vertex
         */
        LinkedList<Vertex> queue = new LinkedList<>();
        HashSet<Vertex> nodes = new HashSet<>();
        nodes.add(root);
        explored.add(root);
        queue.addLast(root);

        while(!queue.isEmpty() && nodes.size() < num){
            Vertex v = queue.pop();
            for(Vertex child:v.edges()){
                if(!explored.contains(child)){
                    queue.addLast(child);
                    nodes.add(child);
                    explored.add(child);
                }
            }
        }
        return nodes;
    }

    /*
     * weight can only be 0 or 1
     * 在ws中，只有weight为1的edge会被记录
     */
    private void assignWeight(HashMap<Vertex,HashSet<Vertex>> ws, HashSet<Vertex> nodes){
        for(Vertex v:nodes){
            for(Vertex u:v.edges()){
                if(!nodes.contains(u)){
                    if(!ws.containsKey(v)){
                        ws.put(v,new HashSet<>());
                    }
                    ws.get(v).add(u);
                    if(!ws.containsKey(u)){
                        ws.put(u,new HashSet<>());
                    }
                    ws.get(u).add(v);
                }
            }
        }
    }

}
