package com.kainos.enstar.driver;

import com.kainos.enstar.source.SchemaSource;
import com.kainos.enstar.common.Table;
import com.kainos.enstar.schema.AvroSchemaGroup;
import org.apache.avro.Schema;
import org.junit.Test;
import org.junit.Assert;

import org.hamcrest.collection.IsMapContaining;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @Test
    public void testValidCommentForSingleSchema() throws Exception {
        String thisPath = this.getClass().getClassLoader().getResource("").getPath();

        AvroSchemaGroup myAvroSchemaGroup = new AvroSchemaGroup(new MySchemaSource(thisPath + "/singlevalidavroschema/"));
        Map<String, String> actualSchemaComments = new HashMap<String, String>();
        actualSchemaComments = myAvroSchemaGroup.getTablesAndComments();

        Map<String, String> expectedTableComment = new HashMap<String, String>();
        expectedTableComment.put("policy", "Transaction table will contain all forms of transactions at policy / section level. TransactionType values should be used to group them appropriately. Signage of the amount consistently mapped across all the source systems transactions loaded into here.");

        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(1));
    }

    @Test
    public void testValidCommentForMultipleSchemas() throws Exception {
        String thisPath = this.getClass().getClassLoader().getResource("").getPath();

        AvroSchemaGroup myAvroSchemaGroup = new AvroSchemaGroup(new MySchemaSource(thisPath + "/multiplevalidavroschemas/"));
        Map<String, String> actualSchemaComments = new HashMap<String, String>();
        actualSchemaComments = myAvroSchemaGroup.getTablesAndComments();

        Map<String, String> expectedTableComment = new HashMap<String, String>();
        expectedTableComment.put("policy", "Transaction table will contain all forms of transactions at policy / section level. TransactionType values should be used to group them appropriately. Signage of the amount consistently mapped across all the source systems transactions loaded into here.");
        expectedTableComment.put("claim", "Centralizes all the information, contacts, and business activities associated with a claimant's loss. The Claim entity is the primary object in the claim data model");

        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(2));
    }

    @Test
    public void testEmptyStringCommentForSingleSchema() throws Exception {
        String thisPath = this.getClass().getClassLoader().getResource("").getPath();

        AvroSchemaGroup myAvroSchemaGroup = new AvroSchemaGroup(new MySchemaSource(thisPath + "/singleavroschemablankcomment/"));
        Map<String, String> actualSchemaComments = new HashMap<String, String>();
        actualSchemaComments = myAvroSchemaGroup.getTablesAndComments();

        Map<String, String> expectedTableComment = new HashMap<String, String>();
        expectedTableComment.put("policy", "");

        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(1));
    }

    @Test
    public void testNoCommentFieldForOneSchema() throws Exception {
        String thisPath = this.getClass().getClassLoader().getResource("").getPath();

        AvroSchemaGroup myAvroSchemaGroup = new AvroSchemaGroup(new MySchemaSource(thisPath + "/singleavroschemanocomment/"));
        Map<String, String> actualSchemaComments = new HashMap<String, String>();
        actualSchemaComments = myAvroSchemaGroup.getTablesAndComments();

        Map<String, String> expectedTableComment = new HashMap<String, String>();
        expectedTableComment.put("policy",  null);

        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(1));
    }

    @Test
    public void testNoCommentFieldForOneOfTwoSchemas() throws Exception {
        String thisPath = this.getClass().getClassLoader().getResource("").getPath();

        AvroSchemaGroup myAvroSchemaGroup = new AvroSchemaGroup(new MySchemaSource(thisPath + "/singleavroschemanocommentoneoftwo/"));
        Map<String, String> actualSchemaComments = new HashMap<String, String>();
        actualSchemaComments = myAvroSchemaGroup.getTablesAndComments();

        Map<String, String> expectedTableComment = new HashMap<String, String>();
        expectedTableComment.put("policy",  null);
        expectedTableComment.put("claim", "Centralizes all the information, contacts, and business activities associated with a claimant's loss. The Claim entity is the primary object in the claim data model");


        Assert.assertThat("Failed to return schema comments as expected.", actualSchemaComments, is(expectedTableComment));

        Assert.assertThat("Failed to return expected number of records in map.", actualSchemaComments.size(), is(2));
    }










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