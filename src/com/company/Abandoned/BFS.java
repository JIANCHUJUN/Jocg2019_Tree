//package com.company.Abandoned;
//
//import com.company.element.Graph;
//import com.company.element.Label;
//import com.company.element.MatchingTable;
//import com.company.element.Vertex;
//
//import java.util.*;
//
//
//public class BFS {
//
//    /*
//     * 记录在BFS中的vertex和到这个vertex已经经过的路径长度
//     */
//    static class Tracker {
//        Vertex vertex;
//        int length;
//        public Tracker(Vertex v, int len){
//            this.vertex = v;
//            this.length = len;
//        }
//    }a
//
//    /*
//     * 返回目前最短的augmenting path的长度
//     * weight为0/1
//     */
//    static public int find_all_free_0_1_graph(Graph graph, MatchingTable matchingTable){
//        /*
//         * 用double ended queue来执行 0-1 BFS从而发现最短路径
//         */
//        LinkedList<Tracker> dequeue = new LinkedList<>();
//        HashSet<Vertex> used = new HashSet<>();
//        HashSet<Vertex> frees_b = matchingTable.all_free(Label.B);
//        /*
//         *把所有的free B放入dequeue
//         */
//        for(Vertex vb: frees_b){
//            dequeue.add(new Tracker(vb,0));
//        }
//
//        while(!dequeue.isEmpty()){
//            /*
//             * 如果pop出的vertex遇到过，不处理，跳过 -> P1
//             *
//             * 如果pop出来的是B vertex -> P2
//             * 则把B的所有的children插入deque
//             * weight为1的放到后面
//             * weight为0的放在前面
//             * 所有children中不包含和B match的A，因为B是A的child
//             *
//             * 如果pop出来的是A vertex -> P3
//             * 如果A是free的，则目标找到，返回长度length
//             * 如果A不free,则把A match 的 B插入dequeue
//             *
//             * 如果没有找到path -> P4
//             * 返回-1且打印对应的信息
//             */
//
//            Tracker tracker = dequeue.pop();
//            Vertex v = tracker.vertex;
//            //Part 1
//            if(used.contains(v)){
//                continue;
//            }
//            else{
//                used.add(v);
//            }
//
//            //Part 2
//            if(v.getLabel() == Label.B){
//                HashSet<Vertex> adjacency = v.getTempUnmatch();
//                for(Vertex va: adjacency){
//                    if(graph.getWeight(v,va) == 0){
//                        dequeue.addFirst(new Tracker(va,tracker.length));
//                    }
//                    else{
//                        dequeue.addLast(new Tracker(va,tracker.length+1));
//                    }
//                }
//            }
//            //Part 3
//            else{
//                if(v.getMatch() == null){
//                    return tracker.length;
//                }
//                else{
//                    Vertex vb = v.getMatch();
//                    if(graph.getWeight(v,vb) == 0){
//                        dequeue.addFirst(new Tracker(vb,tracker.length));
//                    }
//                    else{
//                        dequeue.addLast(new Tracker(vb,tracker.length+1));
//                    }
//                }
//            }
//        }
//
//        //Part 4
//        System.out.println("After BFS on all free B vertexes, we find no free A vertex.");
//        return -1;
//    }
//
//
//    /*
//     * Normal BFS
//     */
//    static public HashSet<Vertex> bfs(Vertex v, HashSet<Vertex> explored){
//        LinkedList<Vertex> queue = new LinkedList<>();
//        HashSet<Vertex> array = new HashSet<>();
//        queue.addLast(v);
//        while(!queue.isEmpty()){
//            Vertex curr = queue.pop();
//            explored.add(curr);
//            array.add(curr);
//            for(Vertex child: curr.getTempUnmatch()){
//                if(!explored.contains(child)){
//                    queue.addLast(child);
//                }
//            }
//        }
//        return array;
//    }
//
//    static public int edge_count(Graph graph){
//        int count = 0;
//        for(Vertex v:graph.get_vertexes()){
//            for(Vertex u:v.getTempUnmatch()){
//                count += 1;
//            }
//        }
//        assert count%2 == 0;
//        return count/2;
//    }
//
//
//}
