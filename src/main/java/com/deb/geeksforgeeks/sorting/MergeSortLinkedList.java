package com.deb.geeksforgeeks.sorting;

import com.deb.geeksforgeeks.utils.LinkedListNode;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/4/14
 * Time: 12:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class MergeSortLinkedList {

    private static class TwoList{
        LinkedListNode first;
        LinkedListNode second;

        TwoList(LinkedListNode first, LinkedListNode second){
            this.first = first;
            this.second = second;
        }
    }

    public static LinkedListNode mergeSort(LinkedListNode head){

        LinkedListNode firstSplitHead = null;
        LinkedListNode secondSplitHead = null;

        if ( head == null || head.getNext() == null){
            return head;
        }

        TwoList twoList = splitList(head);

        firstSplitHead = mergeSort(twoList.first);
        secondSplitHead = mergeSort(twoList.second);

        return mergeList(firstSplitHead, secondSplitHead);

    }

    private static LinkedListNode mergeList (LinkedListNode first, LinkedListNode second){
        LinkedListNode head = null ;

        if(first == null ){
            return second;
        }
        else if( second == null){
            return first;
        }

        if (first.getKey() <= second.getKey()){
            head = first;
            head.setNext(mergeList(first.getNext(),second));
        }
        else{
            head = second;
            head.setNext(mergeList(first, second.getNext()));
        }
        return head;
    }

    private static TwoList splitList(LinkedListNode head){

        LinkedListNode first = null;
        LinkedListNode second= null;

        LinkedListNode slow = head;
        LinkedListNode fast = head.getNext();

        if (head == null || head.getNext() == null){
            first = head ;
            second = null;
        }
        else {
            while(fast != null){
                fast = fast.getNext();
                if (fast !=null){
                    slow = slow.getNext();
                    fast = fast.getNext();
                }
            }
        }
        first = head;
        second = slow.getNext();
        slow.setNext(null);
        TwoList twoList = new TwoList(first, second);
        return twoList;
    }

    public static void printList(LinkedListNode head){
        LinkedListNode run= head;
        if (run!= null){
            System.out.print(run.getKey());
            run= run.getNext();
        }

        while( run != null){
            System.out.print(" --> " + run.getKey());
            run = run.getNext();
        }
    }

    public static void main(String[] args){
        LinkedListNode node1 = new LinkedListNode(21);
        LinkedListNode node2 = new LinkedListNode(34, node1);
        LinkedListNode node3 = new LinkedListNode(3, node2);
        LinkedListNode node4 = new LinkedListNode(32, node3);
        LinkedListNode node5 = new LinkedListNode(1, node4);
        LinkedListNode node6 = new LinkedListNode(78, node5);
        LinkedListNode node7 = new LinkedListNode(13, node6);
        LinkedListNode node8 = new LinkedListNode(20, node7);
        LinkedListNode node9 = new LinkedListNode(5, node8);
        LinkedListNode node10 = new LinkedListNode(10, node9);

        node10 = mergeSort(node10);

        printList(node10);

    }
}
