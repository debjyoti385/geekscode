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

//    private String sourceName;
    private List<Integer> isArr;
    private String targetName;
    private String mappedDimension;
    private List<MappedColumnData> mappedSourceColumnsArr;

    public ExternalColumnObject(String sourceName, List<Integer> arr, String targetName, String mappedDimension, List<MappedColumnData> mappedSourceColumnsArr) {
//        this.sourceName = sourceName;
        isArr = arr;
        this.targetName = targetName;
        this.mappedDimension = mappedDimension;
        this.mappedSourceColumnsArr = mappedSourceColumnsArr;
    }

    public ExternalColumnObject() {
//        sourceName = null;
        isArr = new ArrayList<Integer>();
        targetName = null;
        mappedDimension = null;
        mappedSourceColumnsArr = new ArrayList<MappedColumnData>();
    }

//    public String getSourceName() {
//        return sourceName;
//    }
//
//    public void setSourceName(String sourceName) {
//        this.sourceName = sourceName;
//    }

    public List<Integer> getArr() {
        return isArr;
    }

    public void setArr(List<Integer> arr) {
        isArr = arr;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
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
