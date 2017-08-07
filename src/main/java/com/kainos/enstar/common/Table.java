package com.kainos.enstar.common;

import java.util.Comparator;
import java.util.List;

/**
 * Created by darragh on 07/08/2017.
 */
public class Table implements Comparable<Table> {
    private String tableName;
    private String comment;
    private List<String> columns;
    //private Map<String, Column> columns = null;

    public String getTableName(){return tableName;};

    public void setTableName(String tableName){
        this.tableName = tableName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getColumns() { return this.columns;};

    public void setColumns(List<String> columns){ this.columns = columns;};
    //public Map<String, Column> getColumns(){return columns;};

        /*public void setColumns(HashMap<String, Column> columns){
            this.columns = columns;
        }*/


    @Override
    public String toString() {
        return "[ rollno=]";
    }

    @Override
    public int compareTo(Table o) {
        int comparator = this.getTableName().compareTo(o.getTableName());
        return comparator;
    }
}
