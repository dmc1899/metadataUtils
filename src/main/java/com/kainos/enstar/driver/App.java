package com.kainos.enstar.driver;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.InvalidOperationException;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.thrift.TException;
import org.apache.avro.Schema;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
        if(logger.isDebugEnabled()){
            logger.debug("This is debug : " + "test");
        }
        //log.debug("hive conf file:"+"gg".toString());
        try {
            testReadAndWriteAvroSchemaDocumentation();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        updateHiveMetastore();

    }
}
