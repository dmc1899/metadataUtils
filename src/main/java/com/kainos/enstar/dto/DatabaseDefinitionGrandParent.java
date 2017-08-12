package com.kainos.enstar.dto;

import com.kainos.enstar.source.SchemaSourceComplex;
import org.apache.avro.Schema;

import java.util.*;

/**
 * Created by darragh on 16/03/2017.
 */
public class DatabaseDefinitionGrandParent implements Comparable<DatabaseDefinitionGrandParent>, DefinitionGrandParent {

    private String name;
    private String description;
    private List<DefinitionParent> tableDefinitionListAllColumns = Collections.emptyList();
    private List<DefinitionParent> tableDefinitionListPkColumns = Collections.emptyList();


    public DatabaseDefinitionGrandParent(SchemaSourceComplex schemaSource){
        this.name = schemaSource.getName();
        this.description= schemaSource.getDescription();

        populateTableDefinitionLists(schemaSource);
    }

    @Override
    public int compareTo(DatabaseDefinitionGrandParent o) {
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

    public List<DefinitionParent> getChildDefinitionList() {
        return tableDefinitionListAllColumns;
    }

    public List<DefinitionParent> getTableDefinitionListPkColumns() {return tableDefinitionListPkColumns;}

    //public void setTableDefinitionListAllColumns(List<TableDefinitionParentAllColumns> tableDefs) {
    //    this.tableDefinitionListAllColumns = tableDefs;
    //}

    private void populateTableDefinitionLists(SchemaSourceComplex schemaSource) {
        this.tableDefinitionListAllColumns = new ArrayList<DefinitionParent>();
        this.tableDefinitionListPkColumns = new ArrayList<DefinitionParent>();

        for (Schema schema : schemaSource.getSchemas()) {
            TableDefinitionParentAllColumns tableDefinitionAll = new TableDefinitionParentAllColumns(schema, schemaSource.getSchemaFieldPrimaryKeyIdentifier());
            this.tableDefinitionListAllColumns.add(tableDefinitionAll);

            TableDefinitionParentPkColumns tableDefinitionPk = new TableDefinitionParentPkColumns(schema, schemaSource.getSchemaFieldPrimaryKeyIdentifier());
            this.tableDefinitionListPkColumns.add(tableDefinitionPk);
        }
    }
}
