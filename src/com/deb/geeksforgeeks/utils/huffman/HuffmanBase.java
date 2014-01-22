package com.deb.geeksforgeeks.utils.huffman;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/23/14
 * Time: 1:37 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class HuffmanBase implements Comparable<HuffmanBase> {
    public int frequency;

    protected HuffmanBase(int frequency) {
        this.frequency = frequency;
    }


    @Override
    public int compareTo(HuffmanBase huffmanBase) {
        return frequency - huffmanBase.frequency;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
