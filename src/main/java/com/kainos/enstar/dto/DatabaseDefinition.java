package com.kainos.enstar.dto;

import com.kainos.enstar.source.SchemaSourcePrimitive;
import org.apache.avro.Schema;

import java.util.*;

/**
 * Created by darragh on 16/03/2017.
 */
public class DatabaseDefinition implements Comparable<DatabaseDefinition> {

    private String name;
    private String description;
    private List<TableDefinition> tableDefinitionListAllColumns = Collections.emptyList();
    private List<TableDefinition> tableDefinitionListPkColumns = Collections.emptyList();


    public DatabaseDefinition (SchemaSourcePrimitive schemaSource){
        this.name = schemaSource.getName();
        this.description= schemaSource.getDescription();

        populateTableDefinitionLists(schemaSource);
    }

    @Override
    public int compareTo(DatabaseDefinition o) {
        return this.getName().compareTo(o.getName());
    }

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

    public List<TableDefinition> getTableDefinitionListAllColumns() {
        return tableDefinitionListAllColumns;
    }

    public List<TableDefinition> getTableDefinitionListPkColumns() {
        return tableDefinitionListPkColumns;
    }

    //public void setTableDefinitionListAllColumns(List<TableDefinitionAllColumns> tableDefs) {
    //    this.tableDefinitionListAllColumns = tableDefs;
    //}

    private void populateTableDefinitionLists(SchemaSourcePrimitive schemaSource) {
        this.tableDefinitionListAllColumns = new ArrayList<TableDefinition>();
        this.tableDefinitionListPkColumns = new ArrayList<TableDefinition>();

        for (Schema schema : schemaSource.getSchemas()) {
            TableDefinitionAllColumns tableDefinitionAll = new TableDefinitionAllColumns(schema, schemaSource.getSchemaFieldPrimaryKeyIdentifier());
            this.tableDefinitionListAllColumns.add(tableDefinitionAll);

            TableDefinitionPkColumns tableDefinitionPk = new TableDefinitionPkColumns(schema, schemaSource.getSchemaFieldPrimaryKeyIdentifier());
            this.tableDefinitionListPkColumns.add(tableDefinitionPk);
        }
    }
}
