package com.deb.geeksforgeeks.string;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 5/2/14
 * Time: 11:44 AM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessString {
    List<ColumnObject> columns;
    List<String> explodeColumnName;
    public ProcessString() {
        columns = new ArrayList<ColumnObject>();
    }

    public ProcessString(List<ColumnObject> columns) {
        this.columns = columns;
        explodeColumnName = new ArrayList<String>();
    }

    public boolean validate(){
        for (ColumnObject col : columns){
           List<String> colNameList = Arrays.asList(col.getName().split("\\."));

           int level = 1;
           for(Integer val : col.getArr()){
               if(val == 0){

               }
               else if (val == 1){
                   String tempFQN = "";
                   tempFQN = Joiner.on(".").join(colNameList.subList(0,level+1));
                   if (explodeColumnName.size() == 0){
                       explodeColumnName.add(tempFQN);
                   }
                   else if (!explodeColumnName.get(0).equals(tempFQN)){
                       if (tempFQN.contains(explodeColumnName.get(0))){
                           explodeColumnName.add(tempFQN);
                       }
                       else{
                           System.out.println("error");
                           return false;
                       }
                   }
               }
               level++;
           }
        }
        System.out.println(explodeColumnName.get(0));
        System.out.println(getProcessString());
        return true;
    }

    public String getProcessString(){
        String result;
        String explodedTable = "exploded";

        String table = "BUSINESS_ENTITY";
        String subQuery = null;
        int level =0 ;

        for (String explodedCol : explodeColumnName){
            StringBuilder processFragment = new StringBuilder();
            processFragment.append(" SELECT ");
            List<String> colFragment = new ArrayList<String>();
            for (ColumnObject col : columns){
                if (col.getMappedDimension() == null){
                    colFragment.add(col.getName().replace(explodedCol,explodedTable+level) + " AS " + col.getMappedName());
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
                processFragment.append("(" + subQuery + ") " + (char) (65 + level) );
            }

            if(explodeColumnName != null){
                processFragment.append(" LATERAL VIEW explode(" + explodedCol + ") exploded_table AS " + explodedTable+level);

            }
            subQuery = processFragment.toString();

            level++;
    }
        result = subQuery;
        return result;
    }


    public static void main(String args[]){

        List<ColumnObject> columns = new ArrayList<ColumnObject>();
        ColumnObject co1 = new ColumnObject();
        co1.setName("`data`.OrderId");
        List<Integer> arr1 = new ArrayList<Integer>();
        arr1.add(0);
        co1.setArr(arr1);
        columns.add(co1);


        ColumnObject co2 = new ColumnObject();
        co2.setName("`data`.OrderItems.id");
        List<Integer> arr2 = new ArrayList<Integer>();
        arr2.add(1);
        arr2.add(0);
        co2.setArr(arr2);
        columns.add(co2);

        ColumnObject co3 = new ColumnObject();
        co3.setName("`data`.OrderItems.type");
        List<Integer> arr3 = new ArrayList<Integer>();
        arr3.add(1);
        arr3.add(0);
        co3.setArr(arr3);
        columns.add(co3);

        ColumnObject co4 = new ColumnObject();
        co4.setName("`data`.OrderItems.type.subItems.id");
        List<Integer> arr4 = new ArrayList<Integer>();
        arr4.add(1);
        arr4.add(0);
        arr4.add(1);
        arr4.add(0);
        co4.setArr(arr4);
        columns.add(co4);

//        ColumnObject co5 = new ColumnObject();
//        co5.setName("`data`.OrderItems.type.subItems.id.subsub.type");
//        List<Integer> arr5 = new ArrayList<Integer>();
//        arr5.add(1);
//        arr5.add(0);
//        arr5.add(1);
//        arr5.add(0);
//        arr5.add(1);
//        arr5.add(0);
//        co5.setArr(arr5);
//        columns.add(co5);

        ProcessString processString = new ProcessString(columns);

        processString.validate();
    }
}
