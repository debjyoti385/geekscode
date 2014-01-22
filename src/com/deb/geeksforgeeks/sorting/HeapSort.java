package com.deb.geeksforgeeks.sorting;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/21/14
 * Time: 6:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class HeapSort {
    private int[] heap ;
    int size ;


    public HeapSort(int[] array){
        heap = array;
        size = array.length;
        for(int i =size ; i >=0 ; i-- ){
            heapify(i);
        }
    }

    public void heapify(int pos){
        int i= pos;
        int l = getLeft(i);
        int r = getRight(i);

        int minindex = pos;
        if(l < size && heap[l] < heap[pos] ){
            minindex = l;
        }
        if(r < size && heap[r] < heap[minindex]){
            minindex = r;
        }
        if(minindex != pos){
            heap[pos] = heap[pos] + heap[minindex];
            heap[minindex] = heap[pos] - heap[minindex];
            heap[pos] = heap[pos] - heap[minindex];
//            System.out.println("exchanged "+ heap[pos] +" and " + heap[minindex]);
            heapify(minindex);
        }
    }

    private int getLeft(int i){
       return 2*i + 1;
    }
    private int getRight(int i){
        return 2*i + 2;
    }


    public int getMin(){
        int result = heap[0];
        if(size > 0){
            heap[0] = heap[size -1 ];
            size--;
            heapify(0);
        }
        return result;
    }


    public int[] heapSort(){
        int[] result = new int[size];
        int len = size;
        for (int i = 0 ; i<len ; i++){
            result[i] = getMin();
//            System.out.println("got " + result[i]);
        }
        return result;
    }

    public static void main(String args[]){
        int[] array = {9,10,-1,89,52,651,0,7,75,21,20,78,532,18,83,94};
        HeapSort heapSort = new HeapSort(array);

        array = heapSort.heapSort();
        for(int i=0;i<array.length; i++)
            System.out.print(array[i] + " ");

    }
}
