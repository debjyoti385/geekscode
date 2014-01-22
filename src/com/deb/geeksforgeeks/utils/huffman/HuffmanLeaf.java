package com.deb.geeksforgeeks.utils.huffman;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/23/14
 * Time: 1:41 AM
 * To change this template use File | Settings | File Templates.
 */
public class HuffmanLeaf extends HuffmanBase {
    public char c;

    public HuffmanLeaf(int frequency, char c) {
        super(frequency);
        this.c = c;
    }
}
