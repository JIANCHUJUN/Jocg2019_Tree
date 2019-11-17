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
        Random r = new Random(seed);

        List<Integer> order_a = new ArrayList<>();
        List<Integer> order_b = new ArrayList<>();
        for (int i = 0; i < num_vertex_each_label; i++) {
            order_a.add(i);
            order_b.add(i);
        }
        Collections.shuffle(order_a,r);
        Collections.shuffle(order_b,r);



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

        for(int i = 0; i < num_vertex_each_label; i++){
            int a = order_a.get(i);
            int b = order_b.get(i) + num_vertex_each_label;

            for(int j = 0; j < 2; j++){
                int temp = r.nextInt(num_vertex_each_label/2) + num_vertex_each_label;
                vertexes.get(a).getTempUnmatch().add((vertexes.get(temp)));
                vertexes.get(temp).getTempUnmatch().add((vertexes.get(a)));
                vertexes.get(temp).combine_temp();
            }
            vertexes.get(a).getTempUnmatch().add((vertexes.get(b)));


            for(int j = 0; j < 2; j++){
                int temp = r.nextInt(num_vertex_each_label/2);
                vertexes.get(b).getTempUnmatch().add((vertexes.get(temp)));
                vertexes.get(temp).getTempUnmatch().add((vertexes.get(b)));
                vertexes.get(temp).combine_temp();
            }
            vertexes.get(b).getTempUnmatch().add((vertexes.get(a)));

            vertexes.get(a).combine_temp();
            vertexes.get(b).combine_temp();



            assert vertexes.get(a).adj_to(vertexes.get(b)): "error";
            assert vertexes.get(b).adj_to(vertexes.get(a)): "error";


        }

        return new Graph(vertexes,null);
    }

}
