package com.kainos.enstar.dto;

import com.kainos.enstar.SchemaUtils;
import com.kainos.enstar.schema.AvroSchemaGroupComplex;
import com.kainos.enstar.source.LocalFilesystemSchemaSourceComplex;
import com.kainos.enstar.source.SchemaSourceComplex;
import org.apache.avro.Schema;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static com.kainos.enstar.common.Utils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
public class DatabaseDefinitionGrandParentTest {

    @Test
    public void getValidCommentForSingleSchema() throws Exception {
        ColumnDefinitionChild mockColumnDefinitionChild = mock(ColumnDefinitionChild.class);
        when(mockColumnDefinitionChild.getName()).thenReturn("Column Name 1");
        when(mockColumnDefinitionChild.getDescription()).thenReturn("Column Description 1");
        when(mockColumnDefinitionChild.isPrimaryKey()).thenReturn(true);

        ColumnDefinitionChild mockColumnDefinitionChild2 = mock(ColumnDefinitionChild.class);
        when(mockColumnDefinitionChild2.getName()).thenReturn("Column Name 2");
        when(mockColumnDefinitionChild2.getDescription()).thenReturn("Column Description 2");
        when(mockColumnDefinitionChild2.isPrimaryKey()).thenReturn(true);

        List<DefinitionChild> columnDefinitionList = new ArrayList<DefinitionChild>();
        columnDefinitionList.add(mockColumnDefinitionChild);
        columnDefinitionList.add(mockColumnDefinitionChild2);

        TableDefinitionParent mockTableDefinitionParent = mock(TableDefinitionParent.class);
        when(mockTableDefinitionParent.getName()).thenReturn("Table Name");
        when(mockTableDefinitionParent.getDescription()).thenReturn("Table Description");
        when(mockTableDefinitionParent.getChildDefinitionList()).thenReturn(columnDefinitionList);

        File schemaDirectory = new File(buildPathToTestInputData("singlevalidavroschema/"));
        List<Schema> schemaSourceComplexList = SchemaUtils.loadSchemas(schemaDirectory);


        SchemaSourceComplex mockSchemaSourceComplex = mock(SchemaSourceComplex.class);
        when(mockSchemaSourceComplex.getSchemaFieldPrimaryKeyIdentifier()).thenReturn("PK - ");
        when(mockSchemaSourceComplex.getSchemas()).thenReturn(schemaSourceComplexList);

        DatabaseDefinitionGrandParent databaseDefinition = new DatabaseDefinitionGrandParent(mockSchemaSourceComplex);

        databaseDefinition.getName();
        databaseDefinition.getChildDefinitionList();

        /*
        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(1));
        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));*/
    }

    private AvroSchemaGroupComplex getAvroSchemaGroup(String sourceSchemaDirectory) throws IOException {
        LocalFilesystemSchemaSourceComplex mockLocalFilesystemSchemaSource = getMockLocalFilesystemSchemaSource(sourceSchemaDirectory);
        return new AvroSchemaGroupComplex(mockLocalFilesystemSchemaSource);
    }

    private LocalFilesystemSchemaSourceComplex getMockLocalFilesystemSchemaSource(String relativePathForSchemas) throws IOException {
        File directory = new File(buildPathToTestInputData(relativePathForSchemas));

        LocalFilesystemSchemaSourceComplex mockLocalFilesystemSchemaSource = mock(LocalFilesystemSchemaSourceComplex.class);

        when(mockLocalFilesystemSchemaSource.getName()).thenReturn("Test Source Name");
        when(mockLocalFilesystemSchemaSource.getDescription()).thenReturn("Test Source Description");
        when(mockLocalFilesystemSchemaSource.getSchemaFieldPrimaryKeyIdentifier()).thenReturn("PK - ");

        Schema.Parser schemaParser = new Schema.Parser();
        List<Schema> schemas = new ArrayList<>();

        for (File schemaFile : directory.listFiles()) {
            Schema schema = schemaParser.parse(schemaFile);
            schemas.add(schema);
        }

        when(mockLocalFilesystemSchemaSource.getSchemas()).thenReturn(schemas);

        return mockLocalFilesystemSchemaSource;
    }

}