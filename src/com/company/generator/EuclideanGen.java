package com.company.generator;

import com.company.element.Graph;
import com.company.element.Label;
import com.company.element.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;


public class EuclideanGen {

    class Point{
        double x; double y;
        Point(double x,double y){
            this.x = x;this.y = y;
        }
    }
    /*
     * generate 2 n vertexes in 2d euclidean space
     */
    int seed;
    public EuclideanGen(int seed){
        this.seed = seed;
    }

    public Graph generate(int num, double side, double bottleneck){
        /*
         * 随机种子
         */
        Random r = new Random(seed);

        /*
         * 在side*side的square中生成2n个点
         * 对每个点生成相应的vertex，储存在hashmap中
         */
        ArrayList<Point> points = new ArrayList<>();
        HashMap<Point,Vertex> vertices = new HashMap<>();
        for(int i = 0; i < num; i++){
            Point point = new Point(r.nextDouble()*side,r.nextDouble()*side);
            points.add(point);
            if(i < num/2){
                vertices.put(point, new Vertex(i,null, Label.A));
            }
            else{
                vertices.put(point, new Vertex(i,null, Label.B));
            }

        }

        //生成长度为bottleneck以下的edge
        for(Point source:points){
            HashSet<Vertex> edges = new HashSet<>();
            for(Point target:points){
                //如果label相同，或者是source自身，则不计算距离
                if(source == target || vertices.get(source).getLabel() == vertices.get(target).getLabel()){
                    continue;
                }
                double x = source.x - target.x;
                double y = source.y - target.y;
                double d = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
                if(d <= bottleneck){
                    edges.add(vertices.get(target));
                }
            }
            vertices.get(source).edges = edges;
        }

        for(Vertex v:vertices.values()){
            for(Vertex u:v.edges){
                assert v.adj_to(u);
            }
        }

        /*
         * 选择grid
         * 默认：每个小grid的side为small，大grid的side1为middle
         * bcMap储存着各条线内boundary cells的数量
         * bc = boundary cell count
         * small为side的整数倍
         */
        double small = bottleneck;
        /*
         * middle必须为整数倍的small
         */
        double middle = 4*small;

        assert side%middle == 0;
        assert middle%small == 0;

        int step = (int)(middle/small);
        int hstep = (int)(side/small);


        HashSet<Vertex>[][] cells = new HashSet[hstep][hstep];
        /*
         *把每个points插入到所属的cell中
         */
        {
            for (Point point : points) {
                //找到所属的格子
                int x = search(point.x, small);
                int y = search(point.y, small);
                assert point.x >= x * small && point.x <= (x + 1) * small;
                assert point.y >= y * small && point.y <= (y + 1) * small;
                if (cells[x][y] == null) {
                    cells[x][y] = new HashSet<Vertex>();
                }
                cells[x][y].add(vertices.get(point));
            }
        }

        //找到最少的boundary point的组合
        HashSet<Vertex> bestx = null;
        HashSet<Vertex> besty = null;
        {
            /*
             *x轴
             */

            //小方块向右移动
            for (int i = 0; i < middle / small; i++) {
                HashSet<Vertex> currBC = new HashSet<>();
                //大方块向右移动
                for (int j = 0; j < side / middle; j++) {
                    //每个大方块的起始位置
                    int line = i + step * j;
                    for (int k = 0; k < side / small; k++) {
                        if (cells[line][k] != null) {
                            //这里算的是每个cell里point数量的总和，而不是cell的数量
                            currBC.addAll(cells[line][k]);
                        }
                    }
                }
                if (bestx == null || currBC.size() < bestx.size()) {
                    bestx = currBC;
                }
            }


            /*
             *y轴
             */

            //小方块向右移动
            for (int i = 0; i < middle / small; i++) {
                HashSet<Vertex> currBC = new HashSet<>();
                //大方块向右移动
                for (int j = 0; j < side / middle; j++) {
                    //每个大方块的起始位置
                    int line = i + step * j;
                    for (int k = 0; k < side / small; k++) {
                        if (cells[k][line] != null) {
                            //这里算的是每个cell里point数量的总和，而不是cell的数量
                            currBC.addAll(cells[k][line]);
                        }
                    }
                }
                if (besty == null || currBC.size() < besty.size()) {
                    besty = currBC;
                }
            }
        }

        //知道bestx,besty之后开始assign weights
        HashSet<Vertex> separator = new HashSet<>();
        separator.addAll(bestx);
        separator.addAll(besty);
        Graph graph = new Graph(new HashSet<Vertex>(vertices.values()),separator);

        return graph;

    }

    private int search(double coord,double small) {
        return (int)(coord/small);
    }


}
