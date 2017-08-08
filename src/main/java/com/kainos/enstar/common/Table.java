package com.kainos.enstar.common;

import java.util.List;

/**
 * Created by darragh on 07/08/2017.
 */
public class Table implements Comparable<Table> {
    private String tableName;
    private String comment;
    private List<String> columns;
    //private Map<String, Column> columns = null;

    public String getTableName(){return tableName;}

    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getColumns() { return this.columns;}

    public Table(){};

    public Table (String tableName, String tableComment, List<String> columnList){
        this.tableName = tableName;
        this.comment = tableComment;
        this.columns = columnList;
    }

    public void setColumns(List<String> columns){ this.columns = columns;}
    //public Map<String, Column> getColumns(){return columns;};

        /*public void setColumns(HashMap<String, Column> columns){
            this.columns = columns;
        }*/

/*
    @Override
    public String toString() {

        //TODO This method should print out the name of the table and the columns in the same format as used by the input file in source.
        return tableName + columns.toString();

    }*/

    @Override
    public int compareTo(Table o) {
        return this.getTableName().compareTo(o.getTableName());
    }
}
