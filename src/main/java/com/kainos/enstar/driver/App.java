package com.kainos.enstar.driver;


import org.apache.avro.Schema;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * Metadata Utility for HCatalogue
 *
 */
public class App
{
    //private static Logger log = LoggerFactory.getLogger(App.class);
    private final static Logger logger = Logger.getLogger(App.class);



    public static void main( String[] args )
    {

        int cmpAtoB = "a".compareTo("b");
        int cmpBtoA = "b".compareTo("a");


        ArrayList<Schema> schemas = new ArrayList<Schema>();
        Schema.Parser s = new Schema.Parser();

        try{
            File directory = new File("/tmp/avrodir/");
            if (directory.exists() && directory.isDirectory()){
                int numFiles = directory.listFiles().length;
                if (numFiles > 0){
                    for (File schemaFile: directory.listFiles()){
                        Schema schema = s.parse(schemaFile);
                        schemas.add(schema);
                    }
                }
            }
        }

        catch(Exception e){
System.out.println(e.getMessage());
        }




        if(logger.isDebugEnabled()){
            logger.debug("This is debug : " + "test");
        }



        String json = "{\"type\": \"record\", \"name\": \"r\", \"doc\": \"this is documentation!\", \"fields\": ["
            + "{ \"name\": \"f1\", \"type\": \"long\" }"
            + "]}";
        String json2 = "{\"type\": \"record\", \"name\": \"s\", \"doc\": \"this is documentation for s!\", \"fields\": ["
                + "{ \"name\": \"field1\", \"type\": \"string\" }"
                + "]}";

        Schema schema = s.parse(json);
        schemas.add(schema);

        schema = s.parse(json2);
        schemas.add(schema);

        System.out.println(schema.getDoc());



        //log.debug("hive conf file:"+"gg".toString());
        try {
            //testReadAndWriteAvroSchemaDocumentation();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
       // updateHiveMetastore();

    }

    // Utility method

    public void testSort() throws Exception{
        //AvroSchemaGroup myAvroSchemaGroup = getAvroSchemaGroup("/singlevalidavroschema/");
       // List<TableDefinition> actualTableList = myAvroSchemaGroup.getTablesAndColumns();

        /*System.out.println(actualTableList.get(0).getColumnDefinitionList());
        sortListSafely(actualTableList.get(0).getColumnDefinitionList());
        System.out.println(actualTableList.get(0).getColumnDefinitionList());*/

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
