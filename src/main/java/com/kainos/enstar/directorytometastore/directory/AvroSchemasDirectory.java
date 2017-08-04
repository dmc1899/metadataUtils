package com.kainos.enstar.directorytometastore.directory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import TOBEREMOVED.Database;


import com.kainos.enstar.avroschemas.SchemaSource;
import org.apache.avro.Schema;

/**
 * Created by darragh on 16/03/2017.
 *
 * This class represents a location on a filesystem that contains one or more Avro schemas.
 */
public class AvroSchemasDirectory implements SchemasDirectory {

    private File directory = null;
    private int fileCount = 0;
    //private Schema.Parser schemaParser = null;
    private ArrayList<Schema> schemas = null;
    private SchemaSource schemaSource = null;


    public AvroSchemasDirectory(SchemaSource schemaSource) throws Exception{

        String previousNamespace = null;

        for (Schema schema : schemaSource.getSchemas()) {
            String currentNamespace = schema.getNamespace();
            if (currentNamespace != previousNamespace) {
                throw new Exception("Multiple namespaces identified: " + currentNamespace + " and " + previousNamespace);
            }
            previousNamespace = currentNamespace;
        }
        this.schemaSource = schemaSource;
        this.schemas = schemaSource.getSchemas();
    }

    public Map<String,String> getSchemaTableComments (){
        Map<String,String> tablesAndComments = new HashMap<String, String>();

        for (Schema schema : this.schemas) {
            String tableName = schema.getName();
            String tableComment = schema.getDoc();

            tablesAndComments.put(tableName, tableComment);
        }
        return tablesAndComments;
    }


    public Map<String,String> getSchemaPrimaryKeyColumns (){
        Map<String,String> tablesAndComments = new HashMap<String, String>();

        for (Schema schema : this.schemas) {
            String tableName = schema.getName();
            String tableComment = schema.getDoc();

            tablesAndComments.put(tableName, tableComment);
        }
        return tablesAndComments;
    }







    public String getNamespace() throws Exception {
        String previousNamespace = null;

        for (Schema schema : this.schemas) {
            String currentNamespace = schema.getNamespace();
            if (currentNamespace != previousNamespace) {
                throw new Exception("Multiple namespaces identified: " + currentNamespace + " and " + previousNamespace);

            }
            previousNamespace = currentNamespace;
        }
        return previousNamespace;
    }




    public AvroSchemasDirectory(String pathToDirectoryContainingSchemaFiles) throws IOException {

        File directory = new File(pathToDirectoryContainingSchemaFiles);
        if (!directory.exists() || (!directory.isDirectory())) {
            throw new IOException("Unable to read from specified directory: " + pathToDirectoryContainingSchemaFiles);
        }

        File[] files = directory.listFiles();
        int numFiles = files.length;
        if (!(numFiles > 0)) {
            throw new IOException("No schema files available in: " + pathToDirectoryContainingSchemaFiles);
        }

        int numReadableFiles = 0;
        for (File f : files) {
            if (f.canRead()) {
                numReadableFiles++;
            }
        }
        if (!(numReadableFiles == numFiles)) {
            throw new IOException("Unable to read " + Integer.toString(numFiles - numReadableFiles) + " of " + Integer.toString(numFiles) + " files.");
        }

        Schema.Parser schemaParser = new Schema.Parser();
        ArrayList<Schema> schemas = new ArrayList<Schema>();

        for (File schemaFile : directory.listFiles()) {
            Schema schema = schemaParser.parse(schemaFile);
            schemas.add(schema);
        }

        this.directory = directory;
        this.fileCount = numFiles;
        //this.schemaParser = schemaParser;
        this.schemas = schemas;
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

}
