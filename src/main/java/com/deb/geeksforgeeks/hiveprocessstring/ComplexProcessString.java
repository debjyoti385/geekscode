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

            internalCol.setArr(col.getArr());
            internalCol.setTargetName(col.getTargetName());
            internalCol.setAliasTableName(explodedAliasTableName);
            if(col.getMappedDimension() == null){
                List<String> colNameList = Arrays.asList(col.getMappedSourceColumnsArr().get(0).getMappedSourceColumn().split("\\."));
                int level = 1;
                for(Integer val : col.getArr()){
                    if (val == 1){
                        String tempFQN = "";

                        tempFQN = Joiner.on(".").join(colNameList.subList(0,level+1));
                        if(explodeColName == null){
                            internalCol.setExplodeColumn(tempFQN);              // adding explode column name
                            explodeColName = tempFQN;
                            col.getArr().set(level - 1,0);                      // update 1 to 0 for next iteration
                            level++;
                        }
                        else if (explodeColName.equals(tempFQN)){
                            col.getArr().set(level - 1,0);
                            level++;
                            continue;
                        }
                        else{

                            MappedColumnData mappedColumnData = internalCol.getMappedSourceColumnsArr().get(0);
                            mappedColumnData.setMappedSourceColumn(tempFQN);
                            internalCol.getMappedSourceColumnsArr().set(0,mappedColumnData);
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
            else{
                //TODO for dimension
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

                //                for ( MappedColumnData mappedColumnData : col.getMappedSourceColumnsArr()  ){
                //                    lookupFragment.append(",'" + mappedColumnData.getPrimaryKeyCol() + "','" + mappedColumnData.getMappedSourceColumn() + "'");
                //                }

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
            for (int i : col.getArr()){
                System.out.print(i + "\t");
            }
            System.out.println("");
        }
        System.out.println("==============internal==================");
        for (InternalColumnObject col : internalColumnObjectList){
            System.out.print(col.getMappedSourceColumnsArr().get(0).getMappedSourceColumn() + "\t");
            System.out.print(col.getTargetName() + "\t");
            for (int i : col.getArr()){
                System.out.print(i + "\t");
            }
            System.out.println("");
        }
    }

    public static void main (String args[]){
        List<ExternalColumnObject> columns = new ArrayList<ExternalColumnObject>();
        ExternalColumnObject co1 = new ExternalColumnObject();
        List<MappedColumnData>  mappedColumnDataList1 = new ArrayList<MappedColumnData>();
        MappedColumnData mappedColumnData1 = new MappedColumnData();
        mappedColumnData1.setMappedSourceColumn("`data`.OrderId");
        mappedColumnDataList1.add(mappedColumnData1);
        co1.setMappedSourceColumnsArr(mappedColumnDataList1);
        co1.setTargetName("orderID");
        List<Integer> arr1 = new ArrayList<Integer>();
        arr1.add(0);
        co1.setArr(arr1);
        columns.add(co1);


        ExternalColumnObject co2 = new ExternalColumnObject();

        List<MappedColumnData>  mappedColumnDataList2 = new ArrayList<MappedColumnData>();
        MappedColumnData mappedColumnData2 = new MappedColumnData();
        mappedColumnData2.setMappedSourceColumn("`data`.OrderItems.id");
        mappedColumnDataList2.add(mappedColumnData2);
        co2.setMappedSourceColumnsArr(mappedColumnDataList2);
        co2.setTargetName("orderItemsID");
        List<Integer> arr2 = new ArrayList<Integer>();
        arr2.add(1);
        arr2.add(0);
        co2.setArr(arr2);
        columns.add(co2);

        ExternalColumnObject co3 = new ExternalColumnObject();
        List<MappedColumnData>  mappedColumnDataList3 = new ArrayList<MappedColumnData>();
        MappedColumnData mappedColumnData3 = new MappedColumnData();
        mappedColumnData3.setMappedSourceColumn("`data`.OrderItems.type");
        mappedColumnDataList3.add(mappedColumnData3);
        co3.setTargetName("orderItemsType");
        co3.setMappedSourceColumnsArr(mappedColumnDataList3);
        List<Integer> arr3 = new ArrayList<Integer>();
        arr3.add(1);
        arr3.add(0);
        co3.setArr(arr3);
        columns.add(co3);

        ExternalColumnObject co4 = new ExternalColumnObject();
        List<MappedColumnData>  mappedColumnDataList4 = new ArrayList<MappedColumnData>();
        MappedColumnData mappedColumnData4 = new MappedColumnData();
        mappedColumnData4.setMappedSourceColumn("`data`.OrderItems.type.subItems.id");
        mappedColumnDataList4.add(mappedColumnData4);
        co4.setMappedSourceColumnsArr(mappedColumnDataList4);
        co4.setTargetName("orderSubItemsID");
        List<Integer> arr4 = new ArrayList<Integer>();
        arr4.add(1);
        arr4.add(0);
        arr4.add(1);
        arr4.add(0);
        co4.setArr(arr4);
        columns.add(co4);

        ExternalColumnObject co5 = new ExternalColumnObject();
        List<MappedColumnData>  mappedColumnDataList5 = new ArrayList<MappedColumnData>();
        MappedColumnData mappedColumnData5 = new MappedColumnData();
        mappedColumnData5.setMappedSourceColumn("`data`.OrderItems.type.subItems.id.subsub.type");
        mappedColumnDataList5.add(mappedColumnData5);
        co5.setMappedSourceColumnsArr(mappedColumnDataList5);
        co5.setTargetName("subItemsSubType");
        List<Integer> arr5 = new ArrayList<Integer>();
        arr5.add(1);
        arr5.add(0);
        arr5.add(1);
        arr5.add(0);
        arr5.add(1);
        arr5.add(0);
        co5.setArr(arr5);
        columns.add(co5);

        ExternalColumnObject co6 = new ExternalColumnObject();
        List<MappedColumnData>  mappedColumnDataList6 = new ArrayList<MappedColumnData>();
        MappedColumnData mappedColumnData6 = new MappedColumnData();
        mappedColumnData6.setMappedSourceColumn("`data`.OrderItems.type.subItems.id.subsub.internalSub.type");
        mappedColumnDataList6.add(mappedColumnData6);
        co6.setMappedSourceColumnsArr(mappedColumnDataList6);
        co6.setTargetName("subItemsInternalSubType");
        List<Integer> arr6 = new ArrayList<Integer>();
        arr6.add(1);
        arr6.add(0);
        arr6.add(1);
        arr6.add(0);
        arr6.add(1);
        arr6.add(1);
        arr6.add(0);
        co6.setArr(arr6);
        columns.add(co6);

        ComplexProcessString processString = new ComplexProcessString(columns);

        processString.getProcessString();
    }
}
