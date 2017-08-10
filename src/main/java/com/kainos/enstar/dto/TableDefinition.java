package com.kainos.enstar.dto;

import java.util.List;

/**
 * Created by darragh on 07/08/2017.
 */
public class TableDefinition implements Comparable<TableDefinition> {

    private String name;
    private String comment;
    private List<ColumnDefinition> columnDefinitionList;

    public TableDefinition(){};

    public TableDefinition(String name, String tableComment, List<ColumnDefinition> columnDefs){
        this.name = name;
        this.comment = tableComment;
        this.columnDefinitionList = columnDefs;
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

    public List<ColumnDefinition> getColumnDefinitionList() { return this.columnDefinitionList;}
    
    public void setColumnDefinitionList(List<ColumnDefinition> columnDefs){ this.columnDefinitionList = columnDefs;}

    @Override
    public String toString() {

        //TODO This method should print out the name of the table and the columns in the same format as used by the input file in source.
        return name + columnDefinitionList.toString();

    }

    @Override
    public int compareTo(TableDefinition o) {
        return this.getName().compareTo(o.getName());
    }
}
