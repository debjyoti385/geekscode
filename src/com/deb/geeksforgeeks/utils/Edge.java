package com.deb.geeksforgeeks.utils;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/22/14
 * Time: 12:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class Edge {
    public Vertex source;
    public Vertex destination;
    public int weight;



    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
