package com.kainos.enstar.schema;

import com.kainos.enstar.common.Table;
import com.kainos.enstar.source.SchemaSource;
import org.apache.avro.Schema;

import java.util.*;

/**
 * Created by darragh on 16/03/2017.
 *
 * This class provides additional functionality for Avro schemas, such as identifying primary key columns.
 */
public class AvroSchemaGroup implements SchemaGroup {

    private ArrayList<Schema> schemas = null;

    public AvroSchemaGroup(SchemaSource schemaSource){
        this.schemas = schemaSource.getSchemas();
    }

    public Map<String,String> getTablesAndComments(){
        Map<String,String> tablesAndComments = new HashMap<>();

        for (Schema schema : this.schemas) {
            String tableName = schema.getName();
            String tableComment = schema.getDoc();

            tablesAndComments.put(tableName, tableComment);
        }
        return tablesAndComments;
    }

    public List<Table> getTablesAndPrimaryKeyColumns(String primaryKeyStringIdentifier){

        Table table;
        List<Table> tablesWithPkColumnsOnly = new ArrayList<>();

        for (Schema schema : this.schemas) {
            table = new Table();
            table.setTableName(schema.getName());
            table.setComment(schema.getDoc());

            List<String> primaryKeyfields = new ArrayList<>();

            for (Schema.Field field : schema.getFields()) {
                String fieldComment = field.doc();
                String fieldName = field.name();
                System.out.println(field.doc() + field.name());

                if (fieldComment != null) {
                    if (fieldComment.contains(primaryKeyStringIdentifier)) {
                        primaryKeyfields.add(fieldName);
                    }
                }
            }
            Collections.sort(primaryKeyfields);
            table.setColumns(primaryKeyfields);
            tablesWithPkColumnsOnly.add(table);
        }
        Collections.sort(tablesWithPkColumnsOnly);
        return tablesWithPkColumnsOnly;
    }

    public List<Table> getTablesAndColumns(){

        Table table;
        List<Table> tablesWithColumns = new ArrayList<>();

        for (Schema schema : this.schemas) {
            table = new Table();
            table.setTableName(schema.getName());
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
        //Collections.sort(tablesWithColumns);
        return tablesWithColumns;
    }
}


