package com.deb.geeksforgeeks.string;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 5/7/14
 * Time: 12:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessStringMain {
    List<InternalColumnObject> internalColumnObjectList;
    String explodeColName ;
    List<ColumnObject> columnObjects;
    final String EXPLODED_TABLE_PREFIX = "exploded";

    public ProcessStringMain(List<ColumnObject> columnObjects) {
        this.internalColumnObjectList = new ArrayList<InternalColumnObject>();
        this.columnObjects = columnObjects;
        this.explodeColName = null;
    }

    public boolean preProcessing(int iteration){
        boolean isExplode = false;
        explodeColName = null;
        internalColumnObjectList.clear();
        String explodedAliasTableName =  EXPLODED_TABLE_PREFIX +iteration;
        for(ColumnObject col : columnObjects){
            InternalColumnObject internalCol = new InternalColumnObject();
            internalCol.setName(col.getName());
            internalCol.setArr(col.getArr());
            internalCol.setMappedName(col.getMappedName());
            internalCol.setAliasTableName(explodedAliasTableName);
            List<String> colNameList = Arrays.asList(col.getName().split("\\."));
            int level = 1;
            for(Integer val : col.getArr()){
                if (val == 1){
                    String tempFQN = "";
                    tempFQN = Joiner.on(".").join(colNameList.subList(0,level+1));
                    if(explodeColName == null){
                        internalCol.setExplodeColumn(tempFQN);          // adding explode column name
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
                        internalCol.setName(tempFQN);
                        internalCol.setMappedName(colNameList.get(level));
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
        explodeColName = isExplode ? explodeColName : null;
        return isExplode;
    }

    public String getProcessStringHelper(List<InternalColumnObject> internalColumnObjectList, String subQuery, String table, int level){

            StringBuilder processFragment = new StringBuilder();
            processFragment.append(" SELECT ");
            List<String> colFragment = new ArrayList<String>();
        String alias = String.valueOf((char) (65 + level));
            for (InternalColumnObject col : internalColumnObjectList){

                if (col.getMappedDimension() == null){
                    if (col.getName().contains(explodeColName)){
                        colFragment.add((col.getName().replace(explodeColName,EXPLODED_TABLE_PREFIX+level)) + " AS " + col.getMappedName());
                    }
                    else{
                        colFragment.add((level>0 ? alias+ "." + col.getMappedName():col.getName().replace(explodeColName,level >0 ? col.getAliasTableName():alias)) + " AS " + col.getMappedName());
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
        while(preProcessing(i)){
            ps = getProcessStringHelper(internalColumnObjectList,ps,"BE_TABLE",i);
            System.out.println(i +": "+ ps);
            i++;
        }
//        print();
        return null;
    }

    public void print(){
        System.out.println("=============external===============");
        for (ColumnObject col : columnObjects){
            System.out.print(col.getName() + "\t");
            System.out.print(col.getMappedName() + "\t");
            for (int i : col.getArr()){
                System.out.print(i + "\t");
            }
            System.out.println("");
        }
        System.out.println("==============internal==================");
        for (InternalColumnObject col : internalColumnObjectList){
            System.out.print(col.getName() + "\t");
            System.out.print(col.getMappedName() + "\t");
            for (int i : col.getArr()){
                System.out.print(i + "\t");
            }
            System.out.println("");
        }
    }

    public static void main (String args[]){
        List<ColumnObject> columns = new ArrayList<ColumnObject>();
        ColumnObject co1 = new ColumnObject();
        co1.setName("`data`.payment_advice_transactions.transaction_deductibles.id");
        co1.setMappedName("advice_transaction_deductibles_id");
        List<Integer> arr1 = new ArrayList<Integer>();
        arr1.add(1);
        arr1.add(1);
        arr1.add(0);
        co1.setArr(arr1);
        columns.add(co1);


        ColumnObject co2 = new ColumnObject();
        co2.setName("`data`.payment_advice_transactions.transaction_deductibles.payment_advice_transaction_id");
        co2.setMappedName("advice_payment_transaction_id");
        List<Integer> arr2 = new ArrayList<Integer>();
        arr2.add(1);
        arr2.add(1);
        arr2.add(0);
        co2.setArr(arr2);
        columns.add(co2);

        ColumnObject co3 = new ColumnObject();
        co3.setName("`data`.payment_advice_transactions.transaction_deductibles.deductible_type");
        co3.setMappedName("advice_transaction_deductibles_deductible_type_key");
        List<Integer> arr3 = new ArrayList<Integer>();
        arr3.add(1);
        arr3.add(1);
        arr3.add(0);
        co3.setArr(arr3);
        columns.add(co3);

        ColumnObject co4 = new ColumnObject();
        co4.setName("`data`.payment_advice_transactions.transaction_deductibles.deductible_value");
        co4.setMappedName("advice_transaction_deductibles_deductible_value");
        List<Integer> arr4 = new ArrayList<Integer>();
        arr4.add(1);
        arr4.add(1);
        arr4.add(0);
        co4.setArr(arr4);
        columns.add(co4);

        ColumnObject co5 = new ColumnObject();
        co5.setName("`data`.payment_advice_transactions.transaction_deductibles.created_at");
        co5.setMappedName("advice_transaction_deductibles_created_at_date_key");
        List<Integer> arr5 = new ArrayList<Integer>();
        arr5.add(1);
        arr5.add(1);
        arr5.add(0);
        co5.setArr(arr5);
        columns.add(co5);

        ColumnObject co6 = new ColumnObject();
        co6.setName("`data`.payment_advice_transactions.transaction_deductibles.created_at");
        co6.setMappedName("advice_transaction_deductibles_created_at_time_key");
        List<Integer> arr6 = new ArrayList<Integer>();
        arr6.add(1);
        arr6.add(1);
        arr6.add(0);
        co6.setArr(arr6);
        columns.add(co6);

        ColumnObject co7 = new ColumnObject();
        co7.setName("`data`.payment_advice_transactions.transaction_deductibles.updated_at");
        co7.setMappedName("advice_transaction_deductibles_updated_at_date_key");
        List<Integer> arr7 = new ArrayList<Integer>();
        arr7.add(1);
        arr7.add(1);
        arr7.add(0);
        co7.setArr(arr7);
        columns.add(co7);

        ColumnObject co8 = new ColumnObject();
        co8.setName("`data`.payment_advice_transactions.transaction_deductibles.updated_at");
        co8.setMappedName("advice_transaction_deductibles_updated_at_time_key");
        List<Integer> arr8 = new ArrayList<Integer>();
        arr8.add(1);
        arr8.add(1);
        arr8.add(0);
        co8.setArr(arr8);
        columns.add(co8);


        ProcessStringMain processString = new ProcessStringMain(columns);

        processString.getProcessString();
    }

    private void sampleRun(){
        List<ColumnObject> columns = new ArrayList<ColumnObject>();
        ColumnObject co1 = new ColumnObject();
        co1.setName("`data`.OrderId");
        co1.setMappedName("orderID");
        List<Integer> arr1 = new ArrayList<Integer>();
        arr1.add(0);
        co1.setArr(arr1);
        columns.add(co1);


        ColumnObject co2 = new ColumnObject();
        co2.setName("`data`.OrderItems.id");
        co2.setMappedName("orderItemsID");
        List<Integer> arr2 = new ArrayList<Integer>();
        arr2.add(1);
        arr2.add(0);
        co2.setArr(arr2);
        columns.add(co2);

        ColumnObject co3 = new ColumnObject();
        co3.setName("`data`.OrderItems.type");
        co3.setMappedName("orderItemsType");
        List<Integer> arr3 = new ArrayList<Integer>();
        arr3.add(1);
        arr3.add(0);
        co3.setArr(arr3);
        columns.add(co3);

        ColumnObject co4 = new ColumnObject();
        co4.setName("`data`.OrderItems.type.subItems.id");
        co4.setMappedName("orderSubItemsID");
        List<Integer> arr4 = new ArrayList<Integer>();
        arr4.add(1);
        arr4.add(0);
        arr4.add(1);
        arr4.add(0);
        co4.setArr(arr4);
        columns.add(co4);

        ColumnObject co5 = new ColumnObject();
        co5.setName("`data`.OrderItems.type.subItems.id.subsub.type");
        co5.setMappedName("subItemsSubType");
        List<Integer> arr5 = new ArrayList<Integer>();
        arr5.add(1);
        arr5.add(0);
        arr5.add(1);
        arr5.add(0);
        arr5.add(1);
        arr5.add(0);
        co5.setArr(arr5);
        columns.add(co5);

        ColumnObject co6 = new ColumnObject();
        co6.setName("`data`.OrderItems.type.subItems.id.subsub.internalSub.type");
        co6.setMappedName("subItemsInternalSubType");
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

        ProcessStringMain processString = new ProcessStringMain(columns);

        processString.getProcessString();
    }
}

