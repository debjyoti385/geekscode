package com.deb.geeksforgeeks.utils.graph;

import com.deb.geeksforgeeks.utils.LinkedListNode;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/30/14
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdjacencyListNode extends LinkedListNode{
    public int weight;


    public AdjacencyListNode(int key, int weight) {
        super(key);
        this.weight = weight;
    }

    public AdjacencyListNode(int key, LinkedListNode next) {
        super(key, next);
    }
}
