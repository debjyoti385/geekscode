package com.deb.geeksforgeeks.sorting;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/21/14
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class QuickSort {
    int[] array ;
    int size;

    public QuickSort(int[] array){
        this.array = array;
        this.size= array.length;
    }

    private void partition(int low, int high){

        if(  low >= high){
            return ;
        }

        int pivot = array[high];
//        System.out.println("\n"+pivot);
        int i = low;
        int j = high -1;
        while(i <= j){
            while (array[i] < pivot ){
                i++;
            }
//            System.out.println("now i " + i + "value " + array[i]);
            while(array[j] > pivot){
                j--;
            }
//            System.out.println("now j " + j );
            if(i <= j ){
                array[i] = array[i] + array[j];
                array[j] = array[i] - array[j];
                array[i] = array[i] - array[j];
            }
        }
//        System.out.println( "i = "+ i + "j = " + j + "value " + array[i]);

        if(i < high){
            array[i] = array[i] + array[high];
            array[high] = array[i] - array[high];
            array[i] = array[i] - array[high];
        }


        partition(low, i -1);
        partition(i+1, high);

    }

    public void show(){
        for (int k =0 ; k<array.length  ; k++ ){
            System.out.print(array[k] + " ");
        }
    }

    public static void main(String[] args){
        int[] array = {10,231,123,51,25,1535,983,131,1313,1352,633,900,100};
        QuickSort qs = new QuickSort(array);
        qs.partition(0,array.length-1);

        qs.show();

    }

}
