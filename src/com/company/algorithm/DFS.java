package com.company.algorithm;

import com.company.element.*;

import java.util.*;

public class DFS {


    /*
     * 给定一个v和parentMap
     * 返回一条path
     * 起点为v
     */
    static AugPath getPath(HashMap<Vertex,Vertex> parentMap, Vertex v){
        Vertex curr = v;
        AugPath augPath = new AugPath();
        while(curr != null){
            augPath.add(curr);
            curr = parentMap.get(curr);
        }
        return augPath;
    }

    static public void augment_all_free(Graph graph, MatchingTable matchingTable, int max_length){
        HashSet<Vertex> frees_A =  matchingTable.all_free(Label.A);
        List<AugPath> useless_path = new LinkedList<>();
        matchingTable.restore();

        for(Vertex va: frees_A){
            LinkedList<BFS.Tracker> stack = new LinkedList<>();
            HashSet<Vertex> used = new HashSet<>();
            HashMap<Vertex,Vertex> parentMap = new HashMap<>();
            AugPath augPath = null;

            stack.push(new BFS.Tracker(va,0));



            /*
             这个while之后，我们得到 augPath 和 useless pathes
             */
            while(!stack.isEmpty()){
                BFS.Tracker tracker = stack.pop();
                Vertex v = tracker.vertex;
                used.add(v);
                if(v.getLabel() == Label.B && v.isFree()){
                    augPath = getPath(parentMap,v);
                }
                else if (tracker.length > max_length){
                    System.out.println("In DFS, the program explored deeper than expected length.");
                    throw new NullPointerException();
                }
                else{
                    //A-->B——>A-->B
                    HashSet<Vertex> children;
                    if(v.getLabel() == Label.B){
                        //we know if v is B, v is not free
                        children = new HashSet<>();
                        children.add(v.getMatch());
                    }
                    else{
                        //if v is A, then we want to find unmatched B
                        children = v.getTempUnmatch();
                    }

                    /*
                     * 对于所有的children
                     * 先看到child的距离超了没有
                     * 有的话，回溯，加到useless里
                     * 没有的话，检查是否之前到过这个点，没有的话push到stack里
                     */
                    for(Vertex child:children){
                        if(tracker.length + graph.getWeight(child, v) <= max_length){
                            if(!used.contains(child)){
                                stack.push(new BFS.Tracker(child,tracker.length + graph.getWeight(child, v)));
                                parentMap.put(child,v);
                            }
                        }
                        else{
                            //v走到头了，需要回溯
                            useless_path.add(getPath(parentMap,v));
                        }
                    }

                }


            }

            matchingTable.augment_clear(useless_path,augPath);
        }


    }
}
