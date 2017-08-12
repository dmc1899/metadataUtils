package com.kainos.enstar.schema;

import com.kainos.enstar.dto.DatabaseDefinitionGrandParent;
import com.kainos.enstar.dto.DefinitionChild;
import com.kainos.enstar.dto.DefinitionParent;
import com.kainos.enstar.source.SchemaSourceComplex;
import com.kainos.enstar.source.SchemaSourcePrimitive;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by darragh on 16/03/2017.
 *
 * This class provides additional functionality for Avro schemas, such as identifying primary key columns.
 */
public class AvroSchemaGroup implements SchemaGroupComplex {

    private SchemaSourcePrimitive schemaSource;
    private DatabaseDefinitionGrandParent databaseDefinition;

    public AvroSchemaGroup(SchemaSourceComplex schemaSource){
        this.schemaSource = schemaSource;
        this.databaseDefinition = new DatabaseDefinitionGrandParent(schemaSource);
    }

    public String getName(){
        return this.databaseDefinition.getName();
    }

    public String getDescription(){
        return this.databaseDefinition.getDescription();
    }

    public List<DefinitionParent> getTablesAndPrimaryKeyColumns(){
        return this.databaseDefinition.getTableDefinitionListPkColumns();
    }

    public List<DefinitionParent> getTablesAndAllColumns(){
        return this.databaseDefinition.getChildDefinitionList();
    }

    public Map<String, String> getTableNamesAndDescriptionsOnly(){
        Map<String,String> tablesNamesAndDescriptions = new HashMap<>();

        for (DefinitionChild table : databaseDefinition.getChildDefinitionList()){
            tablesNamesAndDescriptions.put(table.getName(), table.getDescription());
        }
        return tablesNamesAndDescriptions;
    }

    public Map<String, Integer> getTableNamesAndNumberOfColumnsOnly(){
        Map<String,Integer> tableNamesAndNumberOfColumns = new HashMap<>();

        for (DefinitionParent table : databaseDefinition.getChildDefinitionList()){
            tableNamesAndNumberOfColumns.put(table.getName(), table.getChildDefinitionList().size());
        }
        return tableNamesAndNumberOfColumns;
    }
}


