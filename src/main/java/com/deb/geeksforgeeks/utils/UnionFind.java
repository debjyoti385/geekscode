package com.deb.geeksforgeeks.utils;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/22/14
 * Time: 12:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class UnionFind {
    int[] id;
    int[] size;

    public UnionFind(int n) {
        this.id = new int[n];
        this.size= new int[n];
        for(int i=0; i<n ;i++){
            id[i]=i;
            size[i]=1;
        }
    }

    private int root(int i){
        while(id[i] != i){
            i = id[i];
        }
        return i;
    }

    public void union(int i, int j){
        int p = root(i);
        int q = root(j);
        if( p != q){
            if(size[p]< size[q]){
                id[p] = q;
                size[q] =  size[q] + size[p];
            }
            else{
                id[q] = p;
                size[p] = size[q] + size[p];
            }
        }
    }

    public boolean find(int p, int q){
        return root(p) == root(q);
    }
}
