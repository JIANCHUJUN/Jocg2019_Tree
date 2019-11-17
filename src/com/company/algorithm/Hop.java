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
    static private int di;

    public Hop(Graph sub_graph){
        this.graph = sub_graph;
        this.matchingTable = new MatchingTable(graph);
        di = graph.get_vertexes().size();

        /*
         * temp_store用于储存weight为0的edge
         */
        this.temp_store = new HashMap<>();
        for(Vertex v:sub_graph.get_vertexes()){
            ArrayList<Vertex> temp_list = new ArrayList<>(v.getTempUnmatch());
            temp_store.put(v,temp_list);
        }
    }

    public MatchingTable hop_(){
        /*
         * we assume the perfect matching exists
         */
        while(!matchingTable.finish()){
            dfs();
            HashSet<Vertex> free_a = matchingTable.all_free(Label.A);
            restore();
            int a = 0;
        }
        return matchingTable;
    }

//    private boolean bfs(){
//        LinkedList<Vertex> queue = new LinkedList<>();
//        HashSet<Vertex> explored = new HashSet<>();
//        for(Vertex v:matchingTable.all_free(Label.B)){
//            queue.addLast(v);
//            explored.add(v);
//        }
//
//
//        while(!queue.isEmpty()){
//            Vertex v = queue.pop();
//            for(Vertex child: v.get_children(Label.B)){
//                if(child.isFree() && child)
//            }
//        }
//    }

    private void dfs(){
        LinkedList<Vertex> stack = new LinkedList<>();
        HashSet<Vertex> explored = new HashSet<>();
        HashMap<Vertex,Vertex> parentMap = new HashMap<>();
        for(Vertex v:matchingTable.all_free(Label.B)){
            stack.push(v);
            explored.add(v);
        }
        while(!stack.isEmpty()){
            Vertex v = stack.pop();

            if(v.getLabel() == Label.A && v.isFree()){
                AugPath augPath = DFS.getPath(parentMap,v);
                augment(augPath);
            }
            else{
                for(Vertex u:v.get_children(Label.B)){
                    if(!explored.contains(u)){
                        stack.push(u);
                        parentMap.put(u,v);
                        explored.add(u);
                    }
                }
            }
        }
    }

    /*
     * 每次restore会把vertex的weight为0的edge归还
     */
    private void restore(){
        for(Vertex v:graph.get_vertexes()){
            v.restoreTemp(temp_store.get(v));
        }
    }

    private void augment(AugPath augPath){
        if(augPath != null && augPath.path.size()%2 == 0){
            Vertex head = augPath.path.pop();

            for(int i = 0; i < augPath.path.size(); i++){
                Vertex tail = augPath.path.pop();
                if(i%2 == 0){
                    matchingTable.match(head,tail);
                }
                head = tail;
            }
        }
    }


}
