package com.kainos.enstar.source;

import org.apache.avro.Schema;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by darragh on 03/08/2017.
 */
public class LocalFilesystemSchemaSource implements SchemaSource {

    private File directory = null;
    private int fileCount = 0;
    private Schema.Parser schemaParser = null;
    private ArrayList<Schema> schemas = null;
    private String primaryKeyToken = null;

    public LocalFilesystemSchemaSource(String pathToDirectoryContainingSchemaFiles) throws Exception {

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

        // We expect one unique name space per source location.
        String previousNamespace = null;
        for (Schema schema : schemas) {
            String currentNamespace = schema.getNamespace();
            if (currentNamespace != previousNamespace) {
                throw new Exception("Multiple namespaces identified: " + currentNamespace + " and " + previousNamespace);
            }
            previousNamespace = currentNamespace;
        }

        this.directory = directory;
        this.fileCount = numFiles;
        this.schemaParser = schemaParser;
        this.schemas = schemas;
    }

    public void setPrimaryKeyToken(String token){
        primaryKeyToken = token;
    }

    public ArrayList<Schema> getSchemas() {
        return schemas;
    }

    public int getNumberOfSchemas(){
        return this.fileCount;
    }
}
