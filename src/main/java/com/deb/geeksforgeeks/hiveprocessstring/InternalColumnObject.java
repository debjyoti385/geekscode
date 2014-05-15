package com.deb.geeksforgeeks.hiveprocessstring;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 5/7/14
 * Time: 12:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class InternalColumnObject {
//    private String sourceName;
    private List<Integer> isArr;
    private String targetName;
    private String mappedDimension;
    private List<MappedColumnData> mappedSourceColumnsArr;

    String explodeColumn;
    String aliasTableName;


    public InternalColumnObject() {
//        this.sourceName= null;
        isArr = new ArrayList<Integer>();
        explodeColumn = null;
        targetName = null;
        aliasTableName = null;
    }

    public InternalColumnObject(String sourceName, List<Integer> arr, String targetName, String mappedDimension, List<MappedColumnData> mappedSourceColumnsArr, String explodeColumn, String aliasTableName) {
//        this.sourceName = sourceName;
        isArr = arr;
        this.targetName = targetName;
        this.mappedDimension = mappedDimension;
        this.mappedSourceColumnsArr = mappedSourceColumnsArr;
        this.explodeColumn = explodeColumn;
        this.aliasTableName = aliasTableName;
    }

//    public String getSourceName() {
//        return sourceName;
//    }
//
//    public void setSourceName(String sourceName) {
//        this.sourceName = sourceName;
//    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public List<Integer> getArr() {
        return isArr;
    }

    public void setArr(List<Integer> arr) {
        isArr = arr;
    }


    public String getMappedDimension() {
        return mappedDimension;
    }

    public void setMappedDimension(String mappedDimension) {
        this.mappedDimension = mappedDimension;
    }

    public String getExplodeColumn() {
        return explodeColumn;
    }

    public void setExplodeColumn(String explodeColumn) {
        this.explodeColumn = explodeColumn;
    }

    public String getAliasTableName() {
        return aliasTableName;
    }

    public void setAliasTableName(String aliasTableName) {
        this.aliasTableName = aliasTableName;
    }

    public List<MappedColumnData> getMappedSourceColumnsArr() {
        return mappedSourceColumnsArr;
    }

    public void setMappedSourceColumnsArr(List<MappedColumnData> mappedSourceColumnsArr) {
        this.mappedSourceColumnsArr = mappedSourceColumnsArr;
    }
}

