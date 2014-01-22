package com.deb.geeksforgeeks.utils.huffman;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/23/14
 * Time: 1:39 AM
 * To change this template use File | Settings | File Templates.
 */
public class HuffmanNode extends HuffmanBase{
    public HuffmanBase left;
    public HuffmanBase right;

    public HuffmanNode(HuffmanBase left, HuffmanBase right) {
        super(left.frequency + right.frequency);
        this.left = left;
        this.right = right;
    }
}
