package com.company.test;

import com.company.element.Graph;
import com.company.element.Vertex;

import java.util.HashSet;

public class TestSplitGraph {

    public TestSplitGraph(){

    }

    /*
     * 测试分片之后的graph是否valid
     */
    static public void Test(HashSet<Graph> subs, int V, Graph complete_graph){
        int count = 0;
        for(Graph subg:subs){
            count+=subg.get_vertexes().size();
            /*
             * 对于所有的edges
             * 检测边界edge weight为1
             * 中心edge weight为0
             */
            for(Vertex v:subg.get_vertexes()){
                for(Vertex u: v.edges()){
                    if(subg.get_vertexes().contains(u)){
                        assert complete_graph.getWeight(v,u) == 0;
                    }
                    else{
                        assert complete_graph.getWeight(v,u) == 1;
                    }
                }
            }
        }
        /*
         * 测试是否包含所有的vertex
         */
        assert count==V;

    }

}
