package com.kainos.enstar.source;

import com.kainos.enstar.common.Table;
import com.kainos.enstar.schema.AvroSchemaGroup;
import org.apache.avro.Schema;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;

/**
 *
 */
public class LocalFilesystemSchemaSourceTest
{
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getSingleInvalidSchemaFromSource() throws Exception {
        String schemaDirectoryPath = this.getClass().getClassLoader().getResource("").getPath()+"/singleinvalidavroschema/";

        thrown.expect(Exception.class);
        LocalFilesystemSchemaSource schemaSource = new LocalFilesystemSchemaSource(schemaDirectoryPath);
    }

    @Test
    public void getSingleValidSchemaFromSource() throws Exception{
        String schemaDirectoryPath = this.getClass().getClassLoader().getResource("").getPath()+"/singlevalidavroschema/";

        File schemaDirectory = new File(schemaDirectoryPath);

        Schema.Parser schemaParser = new Schema.Parser();
        ArrayList<Schema> expectedSchemas = new ArrayList<Schema>();

        for (File schemaFile : schemaDirectory.listFiles()) {
            Schema schema = schemaParser.parse(schemaFile);
            expectedSchemas.add(schema);
        }

        LocalFilesystemSchemaSource schemaSource = new LocalFilesystemSchemaSource(schemaDirectoryPath);
        ArrayList<Schema> actualSchemas = new ArrayList<Schema>();
        actualSchemas = schemaSource.getSchemas();

        Assert.assertEquals(expectedSchemas, actualSchemas);
    }


    //TODO - write tests that assert an exception occurring.

    @Test
    public void testDuplicateAvroSchemas() throws Exception{

        String thisPath = this.getClass().getClassLoader().getResource("").getPath();
        Map<String, String> expectedTableComment = new HashMap<String, String>();
        expectedTableComment.put("policy", "Transaction table will contain all forms of transactions at policy / section level. TransactionType values should be used to group them appropriately. Signage of the amount consistently mapped across all the source systems transactions loaded into here.");

        Map<String, String> actualTableComment = new HashMap<String, String>();

        try{
            //AvroSchemaGroup avroSchemaGroup = new AvroSchemaGroup("/Users/darragh/Documents/kainos/enstar/myutils/metadataUtils/target/test-classes/duplicateavroschemas/");
            System.out.println("next");

            //actualTableComment = avroSchemaGroup.getTableComments();

            System.out.println("next");

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
    }
        Assert.assertNotSame("mismatch",false, true);

        Assert.assertThat(expectedTableComment, is(actualTableComment));


        System.out.println("next");

    }


@Test
public void testSingleValidAvroSchema() throws Exception{

    String thisPath = this.getClass().getClassLoader().getResource("").getPath();
    Map<String, String> expectedTableComment = new HashMap<String, String>();
    expectedTableComment.put("policy", "Transaction table will contain all forms of transactions at policy / section level. TransactionType values should be used to group them appropriately. Signage of the amount consistently mapped across all the source systems transactions loaded into here.");

    Map<String, String> actualTableComment = new HashMap<String, String>();

    try{
        //AvroSchemaGroup avroSchemaGroup = new AvroSchemaGroup("/singlevalidavroschema/");
        System.out.println("next");

        //actualTableComment = avroSchemaGroup.getTableComments();

        System.out.println("next");

    }
    catch(Exception e) {
        System.out.println(e.getMessage());
    }
    Assert.assertNotSame("mismatch",false, true);

    Assert.assertThat(expectedTableComment, is(actualTableComment));


    System.out.println("next");

}



/*
    List<Table> tables = new ArrayList<Table>();
    tables = myAvroSchemaGroup.getTablesAndPrimaryKeyColumns("PK - ");

    for (Table thisTable : tables) {
        System.out.println(thisTable.getTableName());
        System.out.println("----");
        System.out.println(thisTable.toString());
        System.out.println("----");
        System.out.println(thisTable.getComment());
    }
}*/

    @Test
    public void testValidMultipleSchemas() throws Exception{
        String thisPath = this.getClass().getClassLoader().getResource("").getPath();

        MySchemaSource mySchemaSource = new MySchemaSource(thisPath + "/multiplevalidavroschemas/");
        AvroSchemaGroup myAvroSchemaGroup = new AvroSchemaGroup(mySchemaSource);

        Map<String, String> schemaComments = new HashMap<String, String>();
        schemaComments = myAvroSchemaGroup.getTablesAndComments();

        List<Table> tables = new ArrayList<Table>();
        tables = myAvroSchemaGroup.getTablesAndPrimaryKeyColumns("PK - ");

        for (Table thisTable : tables) {
            System.out.println(thisTable.getTableName());
            System.out.println("---- Comment -----");
            System.out.println(thisTable.getComment());
            System.out.println("---- Columns ------");
            System.out.println(thisTable.getColumns().toString());

        }
    }


    private class MySchemaSource implements SchemaSource{

        private int numberSchemas = 0;
        String thisPath = null;

        public MySchemaSource(String pathToSchemas){
            thisPath = pathToSchemas;
        }
        public ArrayList<Schema> getSchemas() {

            File directory = new File(thisPath);
            if (!directory.exists() || (!directory.isDirectory())) {
                System.out.println("Can't find directory");
            }

            File[] files = directory.listFiles();

            Schema.Parser schemaParser = new Schema.Parser();
            ArrayList<Schema> schemas = new ArrayList<Schema>();

            try {
                for (File schemaFile : directory.listFiles()) {
                    Schema schema = schemaParser.parse(schemaFile);
                    schemas.add(schema);
                }
            }
         catch (Exception e) {
            e.printStackTrace();
        }

        numberSchemas = schemas.size();
        return schemas;

        }

        public int getNumberOfSchemas(){ return numberSchemas;};
    }


    private void testReadAndWriteAvroSchemaDocumentation() throws IOException {
        String json = "{\"type\": \"record\", \"name\": \"r\", \"doc\": \"this is documentation!\", \"fields\": ["
                + "{ \"name\": \"f1\", \"type\": \"long\" }"
                + "]}";
        Schema.Parser s = new Schema.Parser();
        Schema schema = s.parse(json);
        System.out.println(schema.getDoc());
    }
}