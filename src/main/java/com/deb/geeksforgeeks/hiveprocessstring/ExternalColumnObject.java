package com.deb.geeksforgeeks.hiveprocessstring;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 5/2/14
 * Time: 11:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class ExternalColumnObject {

//    private List<Integer> isArr;
    private String name;
    private String mappedDimension;
    private List<MappedColumnData> mappedSourceColumnsArr;

    public ExternalColumnObject(String sourceName, List<Integer> arr, String name, String mappedDimension, List<MappedColumnData> mappedSourceColumnsArr) {
//        isArr = arr;
        this.name = name;
        this.mappedDimension = mappedDimension;
        this.mappedSourceColumnsArr = mappedSourceColumnsArr;
    }

    public ExternalColumnObject() {
//        isArr = new ArrayList<Integer>();
        name = null;
        mappedDimension = null;
        mappedSourceColumnsArr = new ArrayList<MappedColumnData>();
    }

//    public List<Integer> getArr() {
//        return isArr;
//    }
//
//    public void setArr(List<Integer> arr) {
//        isArr = arr;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMappedDimension() {
        return mappedDimension;
    }

    public void setMappedDimension(String mappedDimension) {
        this.mappedDimension = mappedDimension;
    }

    public List<MappedColumnData> getMappedSourceColumnsArr() {
        return mappedSourceColumnsArr;
    }

    public void setMappedSourceColumnsArr(List<MappedColumnData> mappedSourceColumnsArr) {
        this.mappedSourceColumnsArr = mappedSourceColumnsArr;
    }
}
