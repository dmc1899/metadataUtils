package com.kainos.enstar.source;

import org.apache.avro.Schema;
import org.apache.avro.SchemaParseException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

import static com.kainos.enstar.common.Utils.*;

/**
 *
 */
public class LocalFilesystemSchemaSourceTest
{
    private final boolean enforceNamespaceChecks = true;
    private final String primaryKeyIdentifier = "PK -";
    final boolean ignoreNamespaceChecks = false;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getSingleValidSchemaFromSource() throws Exception{
        File schemaDirectory = new File(buildPathToTestInputData("singlevalidavroschema/"));
        List<Schema> expectedSchemas = loadSchemas(schemaDirectory);

        LocalFilesystemSchemaSourceComplex schemaSource = new LocalFilesystemSchemaSourceComplex(schemaDirectory.getPath(),primaryKeyIdentifier, enforceNamespaceChecks);
        List<Schema> actualSchemas = schemaSource.getSchemas();

        Assert.assertEquals(expectedSchemas, actualSchemas);
    }

    @Test
    public void getMultipleValidSchemaFromSource() throws Exception{
        File schemaDirectory = new File(buildPathToTestInputData("multiplevalidavroschemas/"));
        List<Schema> expectedSchemas = loadSchemas(schemaDirectory);

        LocalFilesystemSchemaSourceComplex schemaSource = new LocalFilesystemSchemaSourceComplex(schemaDirectory.getPath(), primaryKeyIdentifier, enforceNamespaceChecks);
        List<Schema> actualSchemas = schemaSource.getSchemas();

        Assert.assertEquals(expectedSchemas, actualSchemas);
    }

    @Test
    public void getSingleInvalidSchemaFromSource() throws Exception {
        File schemaDirectory = new File(buildPathToTestInputData("singleinvalidavroschema/"));

        thrown.expect(SchemaParseException.class);
        LocalFilesystemSchemaSourceComplex schemaSource = new LocalFilesystemSchemaSourceComplex(schemaDirectory.getPath(), primaryKeyIdentifier, enforceNamespaceChecks);
    }

    @Test
    public void getSingleValidSchemaFromInvalidSource() throws Exception {
        File schemaDirectory = new File(buildPathToTestInputData("singlevalidavroschemainvalidsource/"));

        thrown.expect(IOException.class);
        LocalFilesystemSchemaSourceComplex schemaSource = new LocalFilesystemSchemaSourceComplex(schemaDirectory.getPath(), primaryKeyIdentifier, enforceNamespaceChecks);
    }

    @Test
    public void getDuplicateSchemasFromSource() throws Exception {
        File schemaDirectory = new File(buildPathToTestInputData("duplicateavroschemas/"));

        thrown.expect(SchemaParseException.class);
        LocalFilesystemSchemaSourceComplex schemaSource = new LocalFilesystemSchemaSourceComplex(schemaDirectory.getPath(), primaryKeyIdentifier, enforceNamespaceChecks);
    }

    @Test
    public void getDifferentNameSpaceSchemasFromSource() throws Exception {
        File schemaDirectory = new File(buildPathToTestInputData("differentnamespaceavroschemas/"));

        thrown.expect(SchemaParseException.class);
        thrown.expectMessage(is("Multiple namespaces identified: eclipse_tactical and eclipse_strategic. Only one namespace must be present in each Schema Source."));
        LocalFilesystemSchemaSourceComplex schemaSource = new LocalFilesystemSchemaSourceComplex(schemaDirectory.getPath(), primaryKeyIdentifier, enforceNamespaceChecks);
    }

    private List<Schema> loadSchemas(File schemaDirectory) throws IOException {
        Schema.Parser schemaParser = new Schema.Parser();
        List<Schema> expectedSchemas = new ArrayList<>();

        for (File schemaFile : schemaDirectory.listFiles()) {
            Schema schema = schemaParser.parse(schemaFile);
            expectedSchemas.add(schema);
        }
        return expectedSchemas;
    }
}