package com.deb.geeksforgeeks.searching;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/3/14
 * Time: 8:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class InterpolationSearch {
    public static int interpolateSearch(int[] array, int key){
        int low=0;
        int mid;
        int high= array.length - 1;
            while(array[low] <= key && array[high] >= key && (high -low) > 1  ){
                mid = low + ((key - array[low]) * (high - low) /(array[high] - array[low]));
                if (array[mid] < key){
                    low = mid + 1;
                }
                else if (array[mid] > key){
                    high = mid - 1;
                }
                else {
                    return mid;
                }
            }
        return -1;
    }

}
