package com.deb.geeksforgeeks.hiveprocessstring;


import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 4/24/14
 * Time: 7:06 PM
 * To change this template use File | Settings | File Templates.
 */

public class MappedColumnData implements Cloneable{


    private String primaryKeyCol;

    private String mappedData;

    private String mappedSourceColumn;

    private String mappedSourceDataType;

    private ArrayList isArray;

    private String mappedSourceColumnDesc;

    public MappedColumnData() {
    }

    public MappedColumnData(String primaryKeyCol, String mappedData, String mappedSourceColumn, String mappedSourceDataType, String mappedSourceColumnDesc) {
        this.primaryKeyCol = primaryKeyCol;
        this.mappedData = mappedData;
        this.mappedSourceColumn = mappedSourceColumn;
        this.mappedSourceDataType = mappedSourceDataType;
        this.mappedSourceColumnDesc = mappedSourceColumnDesc;
        this.isArray = new ArrayList<Boolean>();

    }

    public String getPrimaryKeyCol() {
        return primaryKeyCol;
    }

    public void setPrimaryKeyCol(String primaryKeyCol) {
        this.primaryKeyCol = primaryKeyCol;
    }

    public String getMappedData() {
        return mappedData;
    }

    public void setMappedData(String mappedData) {
        this.mappedData = mappedData;
    }

    public String getMappedSourceColumn() {
        return mappedSourceColumn;
    }

    public void setMappedSourceColumn(String mappedSourceColumn) {
        this.mappedSourceColumn = mappedSourceColumn;
    }

    public String getMappedSourceDataType() {
        return mappedSourceDataType;
    }

    public void setMappedSourceDataType(String mappedSourceDataType) {
        this.mappedSourceDataType = mappedSourceDataType;
    }

    public String getMappedSourceColumnDesc() {
        return mappedSourceColumnDesc;
    }

    public void setMappedSourceColumnDesc(String mappedSourceColumnDesc) {
        this.mappedSourceColumnDesc = mappedSourceColumnDesc;
    }

    public MappedColumnData clone(){
        try {
            MappedColumnData mappedColumnData = (MappedColumnData) super.clone();
            mappedColumnData.isArray = (ArrayList) this.isArray.clone();
            return mappedColumnData;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public List<Boolean> getArray() {
        return isArray;
    }

    public void setArray(List<Boolean> array) {
        isArray = (ArrayList) array;
    }
}
