package com.deb.geeksforgeeks.string;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 5/27/14
 * Time: 6:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Trimming {
    String trimString;

    public Trimming(String trimString) {
        this.trimString = trimString;
    }

    public String trim(){
        return trimString.trim();
    }
    public static void main(String args[]){
        Trimming trimming = new Trimming("  badare jdha  hello ");
        System.out.println("|"+trimming.trim()+"|");
    }
}
