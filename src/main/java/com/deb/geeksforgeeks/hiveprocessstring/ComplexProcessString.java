package com.deb.geeksforgeeks.hiveprocessstring;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 5/12/14
 * Time: 2:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ComplexProcessString {

    List<InternalColumnObject> internalColumnObjectList;
    String explodeColName ;
    List<ExternalColumnObject> columnObjects;
    final String EXPLODED_TABLE_PREFIX = "EXPLODE_";


    public ComplexProcessString(List<ExternalColumnObject> columnObjects) {
        this.internalColumnObjectList = new ArrayList<InternalColumnObject>();
        this.columnObjects = columnObjects;
        this.explodeColName = null;
    }

    private boolean preProcessing(int iteration){
        boolean isExplode = false;
        explodeColName = null;
        internalColumnObjectList.clear();
        String explodedAliasTableName =  EXPLODED_TABLE_PREFIX +iteration;
        for(ExternalColumnObject col : columnObjects){
            InternalColumnObject internalCol = new InternalColumnObject();
            List<MappedColumnData> mappedColumnDataList = new ArrayList<MappedColumnData>();
            for (MappedColumnData data : col.getMappedSourceColumnsArr() ){
                mappedColumnDataList.add(data.clone());
            }

            internalCol.setMappedSourceColumnsArr(mappedColumnDataList);

//            internalCol.setSourceName(col.getSourceName());

            internalCol.setTargetName(col.getTargetName());
            internalCol.setAliasTableName(explodedAliasTableName);
            int length = col.getMappedSourceColumnsArr().size();
            for(int index =0 ; index < length; index++){
                MappedColumnData columnData = col.getMappedSourceColumnsArr().get(index);
                List<String> colNameList = Arrays.asList(columnData.getMappedSourceColumn().split("\\."));
                int level = 1;
                for(Boolean val : columnData.getArray()){
                    if (val == true){
                        String tempFQN = "";

                        tempFQN = Joiner.on(".").join(colNameList.subList(0,level+1));
                        if(explodeColName == null){
                            internalCol.setExplodeColumn(tempFQN);              // adding explode column name
                            explodeColName = tempFQN;
                            columnData.getArray().set(level - 1,false);                      // update 1 to 0 for next iteration
                            level++;
                        }
                        else if (explodeColName.equals(tempFQN)){
                            columnData.getArray().set(level - 1,false);
                            level++;
                            continue;
                        }
                        else{

                            MappedColumnData mappedColumnData = internalCol.getMappedSourceColumnsArr().get(index);
                            mappedColumnData.setMappedSourceColumn(tempFQN);
                            internalCol.getMappedSourceColumnsArr().set(index,mappedColumnData);
                            internalCol.setTargetName(colNameList.get(level));
                            level++;
                            break;
                        }

                        isExplode = true;
                        break;
                    }

                    level++;
                }
                internalColumnObjectList.add(internalCol);
            }
        }
        explodeColName = isExplode ? explodeColName : null;
        return isExplode;
    }

    private String getProcessStringHelper(List<InternalColumnObject> internalColumnObjectList, String subQuery, String table, int level){

        StringBuilder processFragment = new StringBuilder();
        processFragment.append(" SELECT ");
        List<String> colFragment = new ArrayList<String>();
        String alias = String.valueOf((char) (65 + level));
        for (InternalColumnObject col : internalColumnObjectList){

            if (col.getMappedDimension() == null){
                if (col.getMappedSourceColumnsArr().get(0).getMappedSourceColumn().contains(explodeColName)){
                    colFragment.add((col.getMappedSourceColumnsArr().get(0).getMappedSourceColumn().replace(explodeColName,EXPLODED_TABLE_PREFIX+level)) + " AS " + col.getTargetName());
                }
                else{
                    colFragment.add((level>0 ? alias+ "." + col.getTargetName():col.getMappedSourceColumnsArr().get(0).getMappedSourceColumn().replace(explodeColName,level >0 ? col.getAliasTableName():alias)) + " AS " + col.getTargetName());
                }
            }
            else {
                StringBuilder lookupFragment = new StringBuilder();
                lookupFragment.append("lookup('" + col.getMappedDimension() + "','" + col.getMappedDimension() + "_key'");

                                for ( MappedColumnData mappedColumnData : col.getMappedSourceColumnsArr()  ){
                                    lookupFragment.append(",'" + mappedColumnData.getPrimaryKeyCol() + "','" + mappedColumnData.getMappedSourceColumn() + "'");
                                }

                lookupFragment.append(")");
                colFragment.add(lookupFragment.toString());
            }
        }
        processFragment.append(Joiner.on(", ").join(colFragment));
        processFragment.append(" from ");
        if (subQuery == null){
            processFragment.append(table);
        }
        else{
            processFragment.append("(" + subQuery + ") " + alias );
        }
        if(explodeColName != null){
            processFragment.append(" LATERAL VIEW explode(" + (subQuery == null ? explodeColName : (alias +"."+explodeColName.substring(explodeColName.lastIndexOf(".")+1) )) +  ") exploded_table AS " + EXPLODED_TABLE_PREFIX +level);

        }

        return processFragment.toString();
    }


    public String getProcessString(){
        int i = 0;
        String ps = null;
//        print();
        while(preProcessing(i)){
            ps = getProcessStringHelper(internalColumnObjectList,ps,"BE_TABLE",i);
            System.out.println(i +": "+ ps);
            i++;
//            print();
        }
        print();
        return null;
    }

    public void print(){
        System.out.println("=============external===============");
        for (ExternalColumnObject col : columnObjects){
            System.out.print(col.getMappedSourceColumnsArr().get(0).getMappedSourceColumn() + "\t");
            System.out.print(col.getTargetName() + "\t");
            System.out.println(col.getMappedSourceColumnsArr().get(0).getArray().toString());
            System.out.println("");
        }
        System.out.println("==============internal==================");
        for (InternalColumnObject col : internalColumnObjectList){
            System.out.print(col.getMappedSourceColumnsArr().get(0).getMappedSourceColumn() + "\t");
            System.out.print(col.getTargetName() + "\t");
            System.out.println(col.getMappedSourceColumnsArr().get(0).getArray().toString());
            System.out.println("");
        }
    }

    public static void main (String args[]){
        List<ExternalColumnObject> columns = new ArrayList<ExternalColumnObject>();
        ExternalColumnObject co1 = new ExternalColumnObject();
        List<MappedColumnData>  mappedColumnDataList1 = new ArrayList<MappedColumnData>();
        MappedColumnData mappedColumnData1 = new MappedColumnData();
        mappedColumnData1.setMappedSourceColumn("`data`.OrderId");
        co1.setTargetName("orderID");
        List<Boolean> arr1 = new ArrayList<Boolean>();
        arr1.add(false);
        mappedColumnData1.setArray(arr1);
        mappedColumnDataList1.add(mappedColumnData1);
        co1.setMappedSourceColumnsArr(mappedColumnDataList1);
        columns.add(co1);


        ExternalColumnObject co2 = new ExternalColumnObject();

        List<MappedColumnData>  mappedColumnDataList2 = new ArrayList<MappedColumnData>();
        MappedColumnData mappedColumnData2 = new MappedColumnData();
        mappedColumnData2.setMappedSourceColumn("`data`.OrderItems.id");

        co2.setTargetName("orderItemsID");
        List<Boolean> arr2 = new ArrayList<Boolean>();
        arr2.add(true);
        arr2.add(false);
        mappedColumnData2.setArray(arr2);
        mappedColumnDataList2.add(mappedColumnData2);
        co2.setMappedSourceColumnsArr(mappedColumnDataList2);
        columns.add(co2);

        ExternalColumnObject co3 = new ExternalColumnObject();
        List<MappedColumnData>  mappedColumnDataList3 = new ArrayList<MappedColumnData>();
        MappedColumnData mappedColumnData3 = new MappedColumnData();

        co3.setTargetName("orderItemsType");
        co3.setMappedSourceColumnsArr(mappedColumnDataList3);
        List<Boolean> arr3 = new ArrayList<Boolean>();
        arr3.add(true);
        arr3.add(false);
        mappedColumnData3.setArray(arr3);
        mappedColumnData3.setMappedSourceColumn("`data`.OrderItems.type");
        mappedColumnDataList3.add(mappedColumnData3);
        columns.add(co3);

        ExternalColumnObject co4 = new ExternalColumnObject();
        List<MappedColumnData>  mappedColumnDataList4 = new ArrayList<MappedColumnData>();
        MappedColumnData mappedColumnData4 = new MappedColumnData();
        mappedColumnData4.setMappedSourceColumn("`data`.OrderItems.type.subItems.id");
        co4.setTargetName("orderSubItemsID");
        List<Boolean> arr4 = new ArrayList<Boolean>();
        arr4.add(true);
        arr4.add(false);
        arr4.add(true);
        arr4.add(false);
        mappedColumnData4.setArray(arr4);
        mappedColumnDataList4.add(mappedColumnData4);
        co4.setMappedSourceColumnsArr(mappedColumnDataList4);
        columns.add(co4);

        ExternalColumnObject co5 = new ExternalColumnObject();
        List<MappedColumnData>  mappedColumnDataList5 = new ArrayList<MappedColumnData>();
        MappedColumnData mappedColumnData5 = new MappedColumnData();
        mappedColumnData5.setMappedSourceColumn("`data`.OrderItems.type.subItems.id.subsub.type");

        co5.setTargetName("subItemsSubType");
        List<Boolean> arr5 = new ArrayList<Boolean>();
        arr5.add(true);
        arr5.add(false);
        arr5.add(true);
        arr5.add(false);
        arr5.add(true);
        arr5.add(false);
        mappedColumnData5.setArray(arr5);
        mappedColumnDataList5.add(mappedColumnData5);
        co5.setMappedSourceColumnsArr(mappedColumnDataList5);
        columns.add(co5);

        ExternalColumnObject co6 = new ExternalColumnObject();
        List<MappedColumnData>  mappedColumnDataList6 = new ArrayList<MappedColumnData>();
        MappedColumnData mappedColumnData6 = new MappedColumnData();
        mappedColumnData6.setMappedSourceColumn("`data`.OrderItems.type.subItems.id.subsub.internalSub.type");

        co6.setTargetName("subItemsInternalSubType");
        List<Boolean> arr6 = new ArrayList<Boolean>();
        arr6.add(true);
        arr6.add(false);
        arr6.add(true);
        arr6.add(false);
        arr6.add(true);
        arr6.add(true);
        arr6.add(false);
        mappedColumnData6.setArray(arr6);
        mappedColumnDataList6.add(mappedColumnData6);
        co6.setMappedSourceColumnsArr(mappedColumnDataList6);
        columns.add(co6);

        ComplexProcessString processString = new ComplexProcessString(columns);

        processString.getProcessString();
    }
}
