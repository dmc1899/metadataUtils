package com.kainos.enstar.driver;

import com.kainos.enstar.avroschemas.LocalFilesystemSchemaSource;
import com.kainos.enstar.avroschemas.SchemaSource;
import com.kainos.enstar.directorytometastore.directory.AvroSchemaGroup;
import org.apache.avro.Schema;
import org.junit.Test;
import org.junit.Assert;

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

@Test
public void testSimpleSchema() throws Exception{
    String thisPath = this.getClass().getClassLoader().getResource("").getPath();

    MySchemaSource mySchemaSource = new MySchemaSource();
    AvroSchemaGroup myAvroSchemaGroup = new AvroSchemaGroup(mySchemaSource);

    Map<String, String> schemaComments = new HashMap<String, String>();
    schemaComments = myAvroSchemaGroup.getSchemaComments();

    List<Schema> schemas = new ArrayList<Schema>();
    schemas = myAvroSchemaGroup.getSchemaPrimaryKeyColumns("PK - ");

    System.out.println("here");


}

    private class MySchemaSource implements SchemaSource{

        private int numberSchemas = 0;
        String thisPath = this.getClass().getClassLoader().getResource("").getPath() + "/singlevalidavroschema/";

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
}