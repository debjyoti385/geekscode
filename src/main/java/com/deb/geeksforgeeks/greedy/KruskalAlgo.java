package com.deb.geeksforgeeks.greedy;

import com.deb.geeksforgeeks.utils.graph.Edge;
import com.deb.geeksforgeeks.utils.graph.Graph;
import com.deb.geeksforgeeks.utils.UnionFind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/22/14
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class KruskalAlgo {
    Graph graph;
    List<Edge> minimumSpanningEdges;
    int spanWeight;


    public void runAlgo(){
        UnionFind  unionFind = new UnionFind(graph.vertices.length);
        minimumSpanningEdges = new ArrayList<Edge>();
        Arrays.sort(graph.edges);
        for (Edge e : graph.edges){
            if (!unionFind.find(e.source.name, e.destination.name)){
                unionFind.union(e.source.name,e.destination.name);
                minimumSpanningEdges.add(e);
            }
        }
    }

    public void show(){
        spanWeight =0;
        for(Edge e : minimumSpanningEdges){
            spanWeight+= e.getWeight();
            System.out.println("Weight : " + e.getWeight() + " source : " + e.source.name + " dest :" + e.destination.name);
        }
        System.out.println("sum :" + spanWeight);
    }


    public static void main (String[] args){
        Edge[] edges = new Edge[5];
        edges[0] = new Edge(0,1,10);
        edges[1]  = new Edge(0,2,6);
        edges[2] = new Edge(0,3,5);
        edges[3] = new Edge(1,3,15);
        edges[4] = new Edge(2,3,4);

        Graph g = new Graph(4,edges);

        KruskalAlgo ka = new KruskalAlgo();
        ka.graph = g;

        ka.runAlgo();
        ka.show();

        Edge[] edges1 = new Edge[14];
        edges1[0] = new Edge(1,7,11);
        edges1[1] = new Edge(0,1,4);
        edges1[2] = new Edge(8,6,6);
        edges1[3] = new Edge(2,5,4);
        edges1[4] = new Edge(3,5,14);
        edges1[5] = new Edge(7,6,1);
        edges1[6] = new Edge(1,2,8);
        edges1[7] = new Edge(3,4,9);
        edges1[8] = new Edge(2,3,7);
        edges1[9] = new Edge(5,4,10);
        edges1[10] = new Edge(8,2,2);
        edges1[11] = new Edge(7,8,7);
        edges1[12] = new Edge(6,5,2);
        edges1[13] = new Edge(0,7,8);

        g = new Graph(14,edges1);
        ka.graph = g;

        ka.runAlgo();
        ka.show();



    }
}
