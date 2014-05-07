package com.deb.geeksforgeeks.utils.graph;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/22/14
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Graph {
    public Vertex[] vertices;
    public Edge[] edges;

    public Graph (int n, Edge[] edges){
        vertices = new Vertex[n];
        this.edges = edges;

        validate();
    }



    public boolean validate(){
        for (int i =0 ; i< edges.length; i++){
            if(edges[i].source.name >= vertices.length || edges[i].destination.name >= vertices.length || edges[i].source.name <0 || edges[i].destination.name <0 ){
                System.out.println("not a validate graph");
                return false;
            }
        }
        return true;
    }

}
