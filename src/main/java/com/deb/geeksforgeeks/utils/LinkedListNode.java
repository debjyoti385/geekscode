package com.deb.geeksforgeeks.utils;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/4/14
 * Time: 12:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class LinkedListNode {
    private int key;
    private LinkedListNode next;

    public LinkedListNode(int key) {
        this.key = key;
        this.next = null;
    }

    public LinkedListNode(int key, LinkedListNode next) {
        this.key = key;
        this.next = next;
    }



    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public LinkedListNode getNext() {
        return next;
    }

    public void setNext(LinkedListNode next) {
        this.next = next;
    }
}
