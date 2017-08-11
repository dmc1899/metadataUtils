package com.kainos.enstar.dto;

import java.util.Collections;
import java.util.List;

/**
 * Created by darragh on 11/08/2017.
 */
public abstract class TableDefinition implements Comparable<TableDefinition> {
    protected String name;
    protected String description;
    protected String tokenUsedToIdentifyPrimaryKeyColumns;
    protected List<ColumnDefinition> columnDefinitionList = Collections.emptyList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ColumnDefinition> getColumnDefinitionList() {
        return this.columnDefinitionList;
    }

    public int compareTo(TableDefinition o) {
        return this.getName().compareTo(o.getName());
    }

    public String toString() {

        //TODO This method should print out the name of the table and the columns in the same format as used by the input file in source.
        return name + columnDefinitionList.toString();
    }
}
