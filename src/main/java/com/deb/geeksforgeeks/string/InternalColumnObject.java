package com.deb.geeksforgeeks.string;

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
    String name;
    List<Integer> isArr;
    String mappedName;
    String mappedDimension;

    String explodeColumn;
    String aliasTableName;

    public InternalColumnObject() {
        this.name= null;
        isArr = new ArrayList<Integer>();
        explodeColumn = null;
        mappedName = null;
        aliasTableName = null;
    }

    public InternalColumnObject(String name, List<Integer> arr, String mappedName, String mappedDimension, String explodeColumn, String aliasTableName) {
        this.name = name;
        isArr = arr;
        this.mappedName = mappedName;
        this.mappedDimension = mappedDimension;
        this.explodeColumn = explodeColumn;
        this.aliasTableName = aliasTableName;
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
}
