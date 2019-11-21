package com.company.generator;

import com.company.element.Graph;
import com.company.element.Label;
import com.company.element.Vertex;

import java.util.*;

public class Random_Perfect_Gen {

    int num_vertex_each_label;

    public Random_Perfect_Gen(int n){
        this.num_vertex_each_label = n;
    }

    public Graph generate(long seed){
        /*
         * 随机种子
         */
        Random r = new Random(seed);

        /*
         * 生成两个array
         * 每个包含顺序排列的ID
         */
        List<Integer> order_a = new ArrayList<>();
        List<Integer> order_b = new ArrayList<>();
        for (int i = 0; i < num_vertex_each_label; i++) {
            order_a.add(i);
            order_b.add(i);
        }
        /*
         * 打乱ID
         */
        Collections.shuffle(order_a,r);
        Collections.shuffle(order_b,r);

        /*
         * 对应每个ID生成一个vertex object
         * 根据ID大小分配label
         */
        ArrayList<Vertex> vertexes = new ArrayList<>();
        for(int i = 0; i < 2 * num_vertex_each_label; i++){
            Label label;
            if(i < num_vertex_each_label){
                label = Label.A;
            }
            else{
                label = Label.B;
            }
            vertexes.add(new Vertex(i,new HashSet<>(),label));
        }

        /*
         * 生成一组perfect matching
         */
        for(int i = 0; i < num_vertex_each_label; i++){
            Vertex a = vertexes.get(i);
            Vertex b = vertexes.get(i+num_vertex_each_label);
            a.edges.add(b);
            b.edges.add(a);
        }

        /*
         * 随机加入3N条边
         */
        generateRandomEdges(num_vertex_each_label*3,r,vertexes);

        return new Graph(new HashSet<>(vertexes),null);
    }

    /*
     * 随机加入num条边
     */
    private void generateRandomEdges(int num, Random r, ArrayList<Vertex> varray){
        int count = 0;
        while(count < num){
            /*
             * 随机生成两个
             */
            Vertex a = varray.get(r.nextInt(num_vertex_each_label));
            Vertex b = varray.get(r.nextInt(num_vertex_each_label) + num_vertex_each_label);
            if(!a.adj_to(b)){
                a.edges.add(b);
                b.edges.add(a);
                count+=1;
            }
        }
    }
}
