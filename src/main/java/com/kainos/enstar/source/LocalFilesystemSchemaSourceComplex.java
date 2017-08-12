package com.kainos.enstar.source;

import org.apache.avro.Schema;
import org.apache.avro.SchemaParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by darragh on 03/08/2017.
 */
public class LocalFilesystemSchemaSourceComplex implements SchemaSourceComplex {

    private String name;
    private String description;
    private String schemaFieldPrimaryKeyIdentifier;
    private List<Schema> schemas = Collections.EMPTY_LIST;

    public LocalFilesystemSchemaSourceComplex(String pathToDirectoryContainingSchemaFiles, String schemaFieldPrimaryKeyIdentifier, boolean enforceSingleNamespace) throws IOException, SchemaParseException {
        this.schemaFieldPrimaryKeyIdentifier = schemaFieldPrimaryKeyIdentifier;
        File directory = new File(pathToDirectoryContainingSchemaFiles);

        validateDirectory(directory);
        validateDirectoryContents(directory);

        List<Schema> schemas = loadSchemasFromLocation(directory);
        if (enforceSingleNamespace) {validateSchemaNamespaces(schemas);}

        this.schemas = schemas;
    }
    public String getName(){return this.name;};

    public String getDescription(){return this.description;};

    public List<Schema> getSchemas() {
        return schemas;
    }

    public String getSchemaFieldPrimaryKeyIdentifier(){return this.schemaFieldPrimaryKeyIdentifier;};

    public void setSchemaFieldPrimaryKeyIdentifier(String schemaFieldPrimaryKeyIdentifier){
        this.schemaFieldPrimaryKeyIdentifier = schemaFieldPrimaryKeyIdentifier;
    }

    private void validateSchemaNamespaces(List<Schema> schemas) {
        String previousNamespace = null;

        for (int currentSchemaIndex = 0; currentSchemaIndex < schemas.size(); currentSchemaIndex++){
            String currentNamespace = schemas.get(currentSchemaIndex).getNamespace();
            if (currentSchemaIndex > 0){
                if (!Objects.equals(currentNamespace, previousNamespace)) {
                    throw new SchemaParseException("Multiple namespaces identified: " + currentNamespace + " and " + previousNamespace + ". Only one namespace must be present in each Schema Source.");
                }
            }
            previousNamespace = currentNamespace;
        }
    }

    private List<Schema> loadSchemasFromLocation(File directory) throws IOException {
        Schema.Parser schemaParser = new Schema.Parser();
        List<Schema> schemas = new ArrayList<>();

        for (File schemaFile : directory.listFiles()) {
            Schema schema = schemaParser.parse(schemaFile);
            schemas.add(schema);
        }
        return schemas;
    }

    private void validateDirectoryContents(File directory) throws IOException {
        File[] files = directory.listFiles();
        int numFiles = files != null ? files.length : 0;

        if (!(numFiles > 0)) {
            throw new IOException("No schema files available in location: " + directory.getPath());
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
    }

    private void validateDirectory(File directory) throws IOException {
        if ((!directory.exists()) || (!directory.isDirectory())) {
            throw new IOException("Unable to read from specified directory: " + directory.getPath());
        }
    }
}
