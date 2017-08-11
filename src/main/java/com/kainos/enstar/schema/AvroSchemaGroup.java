package com.kainos.enstar.schema;

import com.kainos.enstar.common.Table;
import com.kainos.enstar.dto.DatabaseDefinition;
import com.kainos.enstar.source.SchemaSource;
import com.kainos.enstar.dto.*;

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

    public DatabaseDefinition getDatabaseDefinition(){
        return this.databaseDefinition;
    }

    public String getName(){
        return this.databaseDefinition.getName();
    }

    public Map<String, String> getTableNamesAndDescriptionsOnly(){
        Map<String,String> tablesNamesAndDescriptions = new HashMap<>();

        for (TableDefinition table : databaseDefinition.getTableDefinitionListAllColumns()){
            tablesNamesAndDescriptions.put(table.getName(), table.getDescription());
        }
        return tablesNamesAndDescriptions;
    }

    //TODO Replace the List with our own Collection of Tables?
    public List<TableDefinition> getTablesAndPrimaryKeyColumns(){
        List<TableDefinition> tableDefinitions = new ArrayList<TableDefinition>();
        return tableDefinitions;
    }

    public List<TableDefinition> getTablesAndAllColumns(){

        Table table;
        List<TableDefinition> tablesWithColumns = new ArrayList<>();
/*
        for (Schema schema : this.schemas) {
            table = new Table();
            table.setName(schema.getName());
            table.setComment(schema.getDoc());

            List<String> fields = new ArrayList<>();

            for (Schema.Field field : schema.getFields()) {
                String fieldComment = field.doc();
                String fieldName = field.name();
                System.out.println(field.doc() + field.name());

                fields.add(fieldName);

            }
            //Collections.sort(fields);
            table.setColumns(fields);
            tablesWithColumns.add(table);
        }
        //Collections.sort(tablesWithColumns);*/
        return tablesWithColumns;
    }
}


