package com.kainos.enstar.schema;

import java.util.*;

import org.apache.avro.Schema;

import com.kainos.enstar.source.SchemaSource;
import com.kainos.enstar.common.Table;

/**
 * Created by darragh on 16/03/2017.
 *
 * This class provides additional functionality for Avro schemas, such as identifying primary key columns.
 */
public class AvroSchemaGroup implements SchemaGroup {

    private ArrayList<Schema> schemas = null;

    public AvroSchemaGroup(SchemaSource schemaSource) throws Exception{

        this.schemas = schemaSource.getSchemas();
    }


    public Map<String,String> getTablesAndComments(){
        Map<String,String> tablesAndComments = new HashMap<String, String>();

        for (Schema schema : this.schemas) {
            String tableName = schema.getName();
            String tableComment = schema.getDoc();

            tablesAndComments.put(tableName, tableComment);
        }
        return tablesAndComments;
    }


    public List<Table> getTablesAndPrimaryKeyColumns(String primaryKeyStringIdentifier){

        String primaryKeyToken = primaryKeyStringIdentifier;
        Table table = null;
        List<Table> tablesWithPkColumnsOnly = new ArrayList<Table>();

        for (Schema schema : this.schemas) {
            table = new Table();
            table.setTableName(schema.getName());
            table.setComment(schema.getDoc());

            List<String> primaryKeyfields = new ArrayList<String>();

            for (Schema.Field field : schema.getFields()) {
                String fieldComment = field.doc();
                String fieldName = field.name();
                System.out.println(field.doc() + field.name());

                if (fieldComment != null) {
                    if (fieldComment.contains(primaryKeyToken)) {
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

}


