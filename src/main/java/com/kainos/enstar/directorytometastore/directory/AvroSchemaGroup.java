package com.kainos.enstar.directorytometastore.directory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import org.apache.avro.*;

import TOBEREMOVED.Database;


import com.kainos.enstar.avroschemas.SchemaSource;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;

/**
 * Created by darragh on 16/03/2017.
 *
 * This class provides additional functionality for Avro schemas, such as identifying primary key columns.
 */
public class AvroSchemaGroup implements SchemaGroup {

    private File directory = null;
    private int fileCount = 0;
    //private Schema.Parser schemaParser = null;
    private ArrayList<Schema> schemas = null;
    private SchemaSource schemaSource = null;


    public AvroSchemaGroup(SchemaSource schemaSource) throws Exception{

        this.schemas = schemaSource.getSchemas();
    }


    public Map<String,String> getSchemaComments (){
        Map<String,String> tablesAndComments = new HashMap<String, String>();

        for (Schema schema : this.schemas) {
            String tableName = schema.getName();
            String tableComment = schema.getDoc();

            tablesAndComments.put(tableName, tableComment);
        }
        return tablesAndComments;
    }


    public List<Schema> getSchemaPrimaryKeyColumns (String primaryKeyStringIdentifier){

        String primaryKeyToken = primaryKeyStringIdentifier;
        List<Schema> schemasWithPkColumnsOnly = new ArrayList<Schema>();
        List<Schema.Field> fieldsThatArePks = null;

        for (Schema schema : this.schemas) {

            fieldsThatArePks = new ArrayList<Schema.Field>();

            for(Schema.Field field : schema.getFields()){
                String fieldComment = field.doc();
                System.out.println(field.doc());

                if (fieldComment != null){
                    if (fieldComment.contains(primaryKeyToken)) {
                        fieldsThatArePks.add(field);
                    }
                }
            }

            schema.setFields(fieldsThatArePks); // This fails with error: AvroRuntimeException: Fields are already set TODO
            fieldsThatArePks = null;

            schemasWithPkColumnsOnly.add(schema);
        }

        return schemasWithPkColumnsOnly;
    }




    public File getDirectory() {
        return directory;
    }

    public int getFileCount() {
        return fileCount;
    }





    public Database getDatabaseDefinition(){

        /*

        2. Is there one avro schema in each file?
        3. Is there one namespace defined across all tables?


for each file in directory
    create table object and assign properties
    for each column in file
        create column object and assign properties
        add column to table
         */





        Database thisDb = new Database();
        return thisDb;
    };

    /** This class should expect to read from a single directory which will contain zero or more
     * Avro schema files.  It is expected that that all Avro schema files which exist in the given
     * directory relate to one schema collection which we model as a database.  The namespace
     * attribute in each Avro schema file should reflect the name of the database.  This should be identical in
     * each Avro schema file.  If not, an exception is thrown.
     * @throws IOException
     */
    private boolean isOnlyOneNamespacePresent(){
        return true;
    }

    private void testReadAndWriteAvroSchemaDocumentation() throws IOException {
        String json = "{\"type\": \"record\", \"name\": \"r\", \"doc\": \"this is documentation!\", \"fields\": ["
                + "{ \"name\": \"f1\", \"type\": \"long\" }"
                + "]}";
        Schema.Parser s = new Schema.Parser();
        Schema schema = s.parse(json);
        System.out.println(schema.getDoc());
    }


    private void createSchemaCollection() throws IOException {

    }

    private class Field {
        private String fieldName;
        private boolean isPrimaryKey;
        private String comment;

        public String getFieldName(){return fieldName;};

        public void setFieldName(String fieldName){
            this.fieldName = fieldName;
        }

        public boolean isFieldPrimaryKey(){return isPrimaryKey;};

        public void setFieldAsPrimaryKey(boolean keyIndicator){
            this.isPrimaryKey = keyIndicator;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }

    private class Table {
        private String tableName;
        private String comment;
        private List<Field> fields = null;

        public String getTableName(){return tableName;};

        public void setTableName(String tableName){
            this.tableName = tableName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public List<Field> getFields(){return fields;};

        public void setFields(List<Field> fields){
            this.fields = fields;
        }
    }
}


