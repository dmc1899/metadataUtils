package com.kainos.enstar.schema;

import com.kainos.enstar.dto.DatabaseDefinition;
import com.kainos.enstar.dto.TableDefinition;
import com.kainos.enstar.source.SchemaSource;


import java.util.*;

/**
 * Created by darragh on 16/03/2017.
 *
 * This class provides additional functionality for Avro schemas, such as identifying primary key columns.
 */
public class AvroSchemaGroup implements SchemaGroup {

    private SchemaSource schemaSource;
    private DatabaseDefinition databaseDefinition;

    public AvroSchemaGroup(SchemaSource schemaSource){
        this.schemaSource = schemaSource;
        this.databaseDefinition = new DatabaseDefinition(schemaSource);
    }

    public String getName(){
        return this.databaseDefinition.getName();
    }

    public String getDescription(){
        return this.databaseDefinition.getDescription();
    }

    public List<TableDefinition> getTablesAndPrimaryKeyColumns(){
        return this.databaseDefinition.getTableDefinitionListPkColumns();
    }

    public List<TableDefinition> getTablesAndAllColumns(){
        return this.databaseDefinition.getTableDefinitionListAllColumns();
    }

    public Map<String, String> getTableNamesAndDescriptionsOnly(){
        Map<String,String> tablesNamesAndDescriptions = new HashMap<>();

        for (TableDefinition table : databaseDefinition.getTableDefinitionListAllColumns()){
            tablesNamesAndDescriptions.put(table.getName(), table.getDescription());
        }
        return tablesNamesAndDescriptions;
    }
}


