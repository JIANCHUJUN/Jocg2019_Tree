package com.company;

import com.company.algorithm.JOCG;
import com.company.element.Graph;
import com.company.element.MatchingTable;
import com.company.element.Vertex;
import com.company.generator.Random_Perfect_Gen;
import com.company.generator.WeightAssigner;
import com.company.test.TestEuclidean;
import com.company.test.TestHop;
import com.company.test.TestJocg;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
	// write your code here
//        System.out.println("Hello World!");
//        TestHop.test();
        //TestJocg.test();
        try{
            TestEuclidean.test();
        }
        catch (IOException ioe){
            System.out.println("Exception");
        }
        //TestEuclidean.testGen();
        System.out.println("Done!");

    }
}



