package com.deb.geeksforgeeks.greedy;

import com.deb.geeksforgeeks.utils.graph.AdjacencyList;

import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/30/14
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class PrimsAlgo {

    private class heapNode{
        int key;
        int dist;

        private heapNode(int key, int dist) {
            this.key = key;
            this.dist = dist;
        }
    }

    public void runAlgo(int size,AdjacencyList graph){
        int[] parent = new int[size];
        int[] key = new int[size];

        PriorityQueue<heapNode> heap = new PriorityQueue<heapNode>();

        key[0] = 0;
        heap.add(new heapNode(0,key[0]));


        for(int i=1; i< size;i++){
            parent[i] = -1;
            key[i]= Integer.MAX_VALUE;
            heap.add(new heapNode(i,key[i]));
        }




    }
}
