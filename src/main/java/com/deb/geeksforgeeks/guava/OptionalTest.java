package com.deb.geeksforgeeks.guava;

import com.google.common.base.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 2/26/14
 * Time: 6:27 PM
 * To change this template use File | Settings | File Templates.
 */



public class OptionalTest {
    public static void main(String[] args){
        Optional<String> foo= Optional.of("hello");
        System.out.println(foo.isPresent());
        System.out.println(foo.get());
        System.out.println(foo.asSet());

        System.out.println(foo.or("HIIII").toString());
        Optional.absent();
//
//        Iterable<Integer> intList = new Iterable<Integer>() {
//            @Override
//            public Iterator<Integer> iterator() {
//                return null;  //To change body of implemented methods use File | Settings | File Templates.
//            }
//        };
//
//        Iterables iterables = new Iterables.();
    }
}
