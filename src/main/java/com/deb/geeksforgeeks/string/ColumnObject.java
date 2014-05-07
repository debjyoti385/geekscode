package com.deb.geeksforgeeks.string;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 5/2/14
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class ColumnObject {
    String name;
    List<Integer> isArr;
    String mappedName;
    String mappedDimension;

    public ColumnObject(String name, List<Integer> arr, String mappedName, String mappedDimension) {
        this.name = name;
        isArr = arr;
        this.mappedName = mappedName;
        this.mappedDimension = mappedDimension;
    }

    public ColumnObject() {
        name = null;
        isArr = new ArrayList<Integer>();
        mappedName = null;
        mappedDimension = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getArr() {
        return isArr;
    }

    public void setArr(List<Integer> arr) {
        isArr = arr;
    }

    public String getMappedName() {
        return mappedName;
    }

    public void setMappedName(String mappedName) {
        this.mappedName = mappedName;
    }

    public String getMappedDimension() {
        return mappedDimension;
    }

    public void setMappedDimension(String mappedDimension) {
        this.mappedDimension = mappedDimension;
    }
}
