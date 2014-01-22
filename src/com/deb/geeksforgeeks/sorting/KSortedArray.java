package com.deb.geeksforgeeks.sorting;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/6/14
 * Time: 11:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class KSortedArray {
    int[] heap = null;
    int k;
    int[] array = null;
    int[] sorted = null;

    KSortedArray(){
       array = new int[]{10,20,30,40,50,60,71,80,90,91};
       k=3;
       for (int i=0; i<k;i++){
            heap[i]= this.array[i];
       }
       createHeap(k);

    }

    KSortedArray(int[] array, int k){
        this.array = array;
        this.k=k;
        heap= new int[k];
        sorted = new int[array.length];

        for (int i=0; i<k;i++){
            heap[i]= this.array[i];
        }
        createHeap(k);
    }

    public void sort(){
        int temp= k;
        for (int i= temp , j=0 ;i < array.length + temp  ; i++, j++){
            if(i < array.length )  {
//                System.out.println(i + " " + array[i]);
                sorted[j] = getMin(array[i]);
                printHeap();
            }
            else{

                sorted[j] = extractMin();
                printHeap();
            }
        }
    }

    private void heapify( int pos){

        int l = left(pos);
        int r = right(pos);
        int swapPos =pos ;
        if (l < k && r < k && heap[l] < heap[r]){
            swapPos = l;
        }
        else if(l < k && r < k ){
            swapPos = r ;
        }
        else if( l < k && r >= k ){
            swapPos = l;
        }

        if (heap[swapPos] < heap[pos]){
            int temp = heap[swapPos];
            heap[swapPos] = heap[pos];
            heap[pos]= temp;
            heapify(swapPos);
        }
    }

    private void createHeap(int size){
        int i = (size -1)/2;

        while (i > 0){
            heapify(i--);
        }
    }

    private int getMin(int insert){
        int result = heap[0];
        heap[0] = insert;
        if (insert > result)
            heapify(0);

        return result;
    }

    private int extractMin(){
        int result;
        result = heap[0];
        if(k > 1 ){
            heap[0]= heap[k-1];
        }
        this.k = k-1;
        heapify(0);
        return result;
    }

    private static int left(int pos){
        return 2* pos + 1;
    }
    private static int right(int pos){
        return 2* pos + 2;
    }

    public void print(){
        for(int i = 0; i< sorted.length ; i++){
            System.out.print(sorted[i] + "--> ");
        }
    }

    public void printHeap(){
        for(int i = 0; i< k ; i++){
            System.out.print(heap[i] + "--> ");
        }
        System.out.println("");
    }

    public static void main (String args[]){
        int [] array = {2, 6, 3, 12, 56, 8, 48, 94, 90, 106, 101, 105};
        KSortedArray  kSortedArray = new KSortedArray(array,6);
        kSortedArray.sort();
        kSortedArray.print();

    }


}
