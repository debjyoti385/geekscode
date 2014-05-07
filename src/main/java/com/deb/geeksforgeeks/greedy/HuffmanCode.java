package com.deb.geeksforgeeks.greedy;

import com.deb.geeksforgeeks.utils.huffman.HuffmanBase;
import com.deb.geeksforgeeks.utils.huffman.HuffmanLeaf;
import com.deb.geeksforgeeks.utils.huffman.HuffmanNode;

import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 1/23/14
 * Time: 1:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class HuffmanCode {

    public static HuffmanBase build(int[] charFreq){
        PriorityQueue<HuffmanBase> heap = new PriorityQueue<HuffmanBase>();

        for (int i =0 ; i< charFreq.length ;i++){
            if(charFreq[i] > 0){
                heap.offer(new HuffmanLeaf(charFreq[i],(char)i));
            }
        }

        while(heap.size() > 1 ){
            HuffmanBase l = heap.poll();
            HuffmanBase r = heap.poll();

            heap.offer( new HuffmanNode(l,r));

        }

        return heap.poll();
    }

    public static void getCode(HuffmanBase tree, StringBuilder str){
        if(tree == null)
            return ;

        if(tree instanceof HuffmanLeaf){
            System.out.println(((HuffmanLeaf) tree).c +"\t" + tree.frequency + "\t" + str.toString());
        }
        else if(tree instanceof HuffmanNode){
            str.append("0");
            getCode(((HuffmanNode) tree).left, str );
            str.setLength(str.length() -1);

            str.append("1");
            getCode(((HuffmanNode) tree).right, str );
            str.setLength(str.length() -1);
        }

    }

    public static void main(String[] args){
        String test = "this is an example for huffman encoding";
        int[] charFreqs = new int[256];
        // read each character and record the frequencies
        for (char c : test.toCharArray())
            charFreqs[c]++;

        // build tree
        HuffmanBase tree = build(charFreqs);

        // print out results
        System.out.println("SYMBOL\tWEIGHT\tHUFFMAN CODE");
        getCode(tree, new StringBuilder());
    }

}
