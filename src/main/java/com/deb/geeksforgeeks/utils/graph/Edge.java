package com.deb.geeksforgeeks.utils.graph;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/22/14
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class Edge implements Comparable<Edge>{
    public Vertex source;
    public Vertex destination;
    public int weight;

    public Edge(Vertex source, Vertex destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Edge(int source, int destination, int weight) {
        this.source = new Vertex(source);
        this.destination = new Vertex(destination);
        this.weight = weight;
    }



    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Edge edge) {
        return this.weight - edge.weight;
    }
}
