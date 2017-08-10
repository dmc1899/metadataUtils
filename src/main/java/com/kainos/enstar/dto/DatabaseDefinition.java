package com.kainos.enstar.dto;

import java.util.List;

/**
 * Created by darragh on 16/03/2017.
 */
public class DatabaseDefinition {

    private String name;
    private String description;
    private List<TableDefinition> tableDefinitionList;

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

    public List<TableDefinition> getTableDefinitionList() {
        return tableDefinitionList;
    }

    public void setTableDefinitionList(List<TableDefinition> tableDefs) {
        this.tableDefinitionList = tableDefs;
    }
}
