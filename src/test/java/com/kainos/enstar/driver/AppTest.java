package com.kainos.enstar.driver;

import com.kainos.enstar.directorytometastore.directory.AvroSchemasDirectory;
import org.junit.Test;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

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
            AvroSchemasDirectory avroSchemasDirectory = new AvroSchemasDirectory("/Users/darragh/Documents/kainos/enstar/myutils/metadataUtils/target/test-classes/duplicateavroschemas/");
            System.out.println("next");

            actualTableComment = avroSchemasDirectory.getTableComments();

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
        AvroSchemasDirectory avroSchemasDirectory = new AvroSchemasDirectory("/singlevalidavroschema/");
        System.out.println("next");

        actualTableComment = avroSchemasDirectory.getTableComments();

        System.out.println("next");

    }
    catch(Exception e) {
        System.out.println(e.getMessage());
    }
    Assert.assertNotSame("mismatch",false, true);

    Assert.assertThat(expectedTableComment, is(actualTableComment));


    System.out.println("next");

}
}