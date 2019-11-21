//package com.company.Abandoned;
//
//import com.company.Abandoned.BFS;
//import com.company.Abandoned.DFS;
//import com.company.element.*;
//
//import java.util.*;
//
//public class Harp {
//
//    private HashMap<Vertex,ArrayList<Vertex>> temp_store;
//    private MatchingTable matchingTable;
//    private Graph graph;
//    static private int di;
//
//
//    static class ParentMap{
//        HashMap<Integer,HashMap<Vertex,HashSet<Vertex>>> pMs;
//        ParentMap(){
//            pMs = new HashMap<>();
//        }
//
//
//
//        /*
//         * 把child以及parent加到layer中
//         */
//        void add(Vertex child, Vertex parent, int layer){
//            if(!pMs.containsKey(layer)){ // 如果layer不存在，新建layer
//                pMs.put(layer,new HashMap<>());
//            }
//            if(!pMs.get(layer).containsKey(child)){ // 如果是第一次加入child，新建child的位置
//                pMs.get(layer).put(child,new HashSet<>());
//            }
//            pMs.get(layer).get(child).add(parent); // 加入parent
//        }
//
//        Set<Vertex> get_layer(int layer){
//            if (pMs.get(layer) == null){
//                System.out.println("layer " + layer + " is null");
//                assert pMs.get(layer) != null;
//            }
//            return pMs.get(layer).keySet();
//        }
//
//        /*
//         * delete在stage 2 和 augment中被用到
//         * 从某个layer中删去child，以及所有它去往下一层的edge
//         * 如果有任何的v在这个过程中parent变成空的，一并删除
//         */
//        void delete(Vertex child, int layer){
//            assert (pMs.get(layer) != null); // assert 这一层存在
//
//            if(child.getID() == 27 || child.getID() == 28){
//                int a = 0;
//            }
//
//            if(pMs.get(layer + 1) != null){ // 如果下一层也存在
//                for(Vertex v:pMs.get(layer+1).keySet()){ // 对于所有下一层的v
//                    HashSet<Vertex> parentv = pMs.get(layer+1).get(v); // 从这个v的parent中删除child
//                    parentv.remove(child);
//                }
//            }
//            pMs.get(layer).remove(child); //删除child
//        }
//
//        /*
//         * 如果v在layer之前的层中出现(不包含layer)，则返回true
//         */
//        boolean in_early_layers(Vertex v,int layer){
//            for(int i = 1; i < layer; i++){
//                if(pMs.get(i).containsKey(v)){
//                    return true;
//                }
//            }
//            return false;
//        }
//    }
//
//    public Harp(Graph sub_graph){
//
//        this.graph = sub_graph;
//        this.matchingTable = new MatchingTable(graph);
//        di = graph.get_vertexes().size();
//
//        /*
//         * temp_store用于储存weight为0的edge
//         */
//        this.temp_store = new HashMap<>();
//        for(Vertex v:sub_graph.get_vertexes()){
//            ArrayList<Vertex> temp_list = new ArrayList<>(v.getTempUnmatch());
//            temp_store.put(v,temp_list);
//        }
//
//
//    }
//
//
//    public MatchingTable Harp_(){
//        ParentMap pMs = new ParentMap();
//
//        /*
//         * 第一层加入
//         */
//        for(Vertex free_a:matchingTable.all_free(Label.A)){
//            pMs.add(free_a,null,0);
//        }
//
//        int i = 0;
//        while(!matchingTable.finish()){
//            //stage 1
//            for(Vertex v:pMs.get_layer(i)){ //对于所有在i layer的v
//                for(Vertex u:v.getTempUnmatch()){ //对于v 的child
//                    if(!pMs.in_early_layers(u,i+1)){ //如果child 不在之前的layer中
//                        pMs.add(u,v,i+1); //把child加到下个layer中
//                    }
//                }
//            }
//
//            if(di == 52){
//                int a = 0;
//            }
//
//            //stage 2
//            augment(pMs,i+1); //augmnet所有在i+1 layer中剩下的
//
//            HashSet<Vertex> aa = matchingTable.all_free(Label.A);
//            HashSet<Vertex> bb = matchingTable.all_free(Label.B);
//            System.out.println("There are " + aa.size() + " free A");
//            if(aa.size() == 0){
//                break;
//            }
//
//            //stage 3
//            for(Vertex v:pMs.get_layer(i+1)){ //对于所有在i+1 layer中的v
//                Vertex u = v.getMatch(); //让u == v的match
//                if(!pMs.in_early_layers(u,i+2)){ //如果u不在之前的layer中
//                    pMs.add(u,v,i+2); //把u加入i+2的layer中
//                }
//            }
//
//
//            i = i + 2;
//
//        }
//
//        return matchingTable;
//    }
//
//    /*
//     * 从layer中的v出发，回溯一条augmenting path
//     */
//    private AugPath trace_back(Vertex v, ParentMap pms, int layer){
//        assert v.isFree();
//        assert v.getLabel() == Label.B;
//        HashMap<Vertex,Vertex> parent = new HashMap<>();
//        LinkedList<BFS.Tracker> stack = new LinkedList<>();
//        HashSet<Vertex> explored = new HashSet<>();
//        stack.push(new BFS.Tracker(v,layer));
//        explored.add(v);
//        while(!stack.isEmpty()){
//             BFS.Tracker tracker = stack.pop();
//             Vertex u = tracker.vertex;
//             int u_layer = tracker.length;
//             if(u_layer == 0 && u.getLabel() == Label.A && u.isFree()){
//                 AugPath path = DFS.getPath(parent,u);
//                 assert path.path.size()%2 == 0;
//                 return path;
//             }
//             else if(u_layer > 0){
//                 for(Vertex pu:pms.pMs.get(u_layer).get(u)){
//                     if(!explored.contains(pu)){
//                         stack.push(new BFS.Tracker(pu,u_layer-1));
//                         parent.put(pu,u);
//                         explored.add(u);
//                     }
//                 }
//             }
//        }
//        return null;
//    }
//
//
//    /*
//     * augment t layer
//     */
//    private void augment(ParentMap pms, int t){
//
//        for(Vertex v:pms.get_layer(t)){
//
//            if(!v.isFree()){
//                continue;
//            }
//            LinkedList<BFS.Tracker> delete_queue = new LinkedList<>();
//            /*
//             * 从layer 0到layer t的augmenting path
//             * 第一个出来的是layer 0
//             */
//            AugPath augPath = trace_back(v,pms,t);
//            if(augPath != null && augPath.path.size() == t+1){
//                Vertex head = augPath.path.pop();
//
//                for(int i = 0; i < t; i++){
//                    Vertex tail = augPath.path.pop();
//                    if(i%2 == 0){
//                        matchingTable.match(head,tail);
//                    }
//                    delete_queue.addLast(new BFS.Tracker(head,i));
//                    head = tail;
//                }
//            }
//            for(BFS.Tracker tracker:delete_queue){
//                pms.delete(tracker.vertex,tracker.length);
//            }
//        }
//
//
//
//        for(int i = 1; i <= t; i++){
//            HashMap<Vertex,HashSet<Vertex>> layer = pms.pMs.get(i);
//            LinkedList<Vertex> deq = new LinkedList<>();
//            for(Vertex v: layer.keySet()){
//                if(layer.get(v).size() == 0){
//                    deq.addLast(v);
//                }
//            }
//            for(Vertex v:deq){
//                pms.delete(v,i);
//            }
//        }
//    }
//
//
//    /*
//     * 每次restore会把vertex的weight为0的edge归还
//     */
//    private void restore(){
//        for(Vertex v:graph.get_vertexes()){
//            v.restoreTemp(temp_store.get(v));
//        }
//    }
//
//}
