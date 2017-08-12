package com.kainos.enstar.schema;

import com.kainos.enstar.dto.DatabaseDefinition;
import com.kainos.enstar.dto.TableDefinition;
import com.kainos.enstar.source.SchemaSourcePrimitive;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by darragh on 16/03/2017.
 *
 * This class provides additional functionality for Avro schemas, such as identifying primary key columns.
 */
public class AvroSchemaGroup implements SchemaGroupPrimitive, SchemaGroupComplex {

    private SchemaSourcePrimitive schemaSource;
    private DatabaseDefinition databaseDefinition;

    public AvroSchemaGroup(SchemaSourcePrimitive schemaSource){
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

    public Map<String, Integer> getTableNamesAndNumberOfColumnsOnly(){
        Map<String,Integer> tableNamesAndNumberOfColumns = new HashMap<>();

        for (TableDefinition table : databaseDefinition.getTableDefinitionListAllColumns()){
            tableNamesAndNumberOfColumns.put(table.getName(), table.getColumnDefinitionList().size());
        }
        return tableNamesAndNumberOfColumns;
    }
}


