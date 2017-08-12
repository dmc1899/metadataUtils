package com.kainos.enstar.common;

import java.util.List;

/**
 * Created by darragh on 07/08/2017.
 */
public class Table implements Comparable<Table> {
    private String name;
    private String comment;
    private List<String> columns;

    public Table(){};

    public Table (String name, String tableComment, List<String> columnList){
        this.name = name;
        this.comment = tableComment;
        this.columns = columnList;
    }

    public String getName(){return name;}

    public void setName(String name){
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getColumns() { return this.columns;}

    public void setColumns(List<String> columns){ this.columns = columns;}
    //public Map<String, ColumnDefinitionChild> getChildDefinitionList(){return columns;};

        /*public void setColumnDefinitionList(HashMap<String, ColumnDefinitionChild> columns){
            this.columns = columns;
        }*/

/*
    @Override
    public String toString() {

        //TODO This method should print out the name of the table and the columns in the same format as used by the input file in source.
        return name + columns.toString();

    }*/

    @Override
    public int compareTo(Table o) {
        return this.getName().compareTo(o.getName());
    }
}
