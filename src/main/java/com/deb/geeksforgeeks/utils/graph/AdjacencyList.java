package com.deb.geeksforgeeks.utils.graph;

import com.deb.geeksforgeeks.utils.LinkedListNode;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/30/14
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class AdjacencyList {

    public List<AdjacencyListNode> head;



    public List<AdjacencyListNode> getHead() {
        return head;
    }

    public void setHead(List<AdjacencyListNode> head) {
        this.head = head;
    }

    public void addNode(AdjacencyListNode node){
        if (!head.contains(node)){
            this.head.add(node);
        }
    }


}
