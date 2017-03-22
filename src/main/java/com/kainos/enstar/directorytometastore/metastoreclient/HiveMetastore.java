package com.kainos.enstar.directorytometastore.metastoreclient;

import org.apache.thrift.TException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Table;
import org.apache.hadoop.hive.metastore.api.InvalidOperationException;
import org.apache.hadoop.hive.metastore.api.MetaException;

import TOBEREMOVED.Database;
//import com.kainos.enstar.common.model.Table;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by darragh on 16/03/2017.
 */
public class HiveMetastore implements Metastore {

    private String hiveConfFile = "/etc/hive/conf";
    private static final String HIVECONF = "hive-site.xml";
    private static final String HADOOPCONFIG = "hadoop_config.xml";
    private static final String METASTOREURL = "http://localhost";
    private static final String HIVEDBCOMMENTKEY = "comment";
    private static final String HIVEDBAVRODOCKEY = "avro.schema.doc";

    public HiveMetastore(String configPath){
        this.hiveConfFile = configPath;
    }

    public void updateDatabaseTableDescriptions(Database database){

    };

    private void updateTableDescription(Table table){};

    private void updateHiveMetastore() {
        // Create Hive Metastore client
        HiveMetaStoreClient hiveMetastoreClient = null;
        try {
            hiveMetastoreClient = createMetastoreClient();
        }
        catch (Exception ie){
            System.out.println("Exception " + ie.getMessage());
        }

        // Get table details.
        Table thisTable = new Table();
        try {
            thisTable = hiveMetastoreClient.getTable("default", "hive1");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Get the current parameters of the table
        Map<String, String> currentParams = new HashMap<String, String>();
        currentParams = thisTable.getParameters();

        // Set the parameters of the table for "comment" and "avro.doc"
        //Map<String, String> newMap = new HashMap<String, String>();
        currentParams.put(HIVEDBCOMMENTKEY, "woo hoo !! hive table comment");
        currentParams.put(HIVEDBAVRODOCKEY, "woo hoo !! avro table comment");

        thisTable.setParameters(currentParams);

        try {
            hiveMetastoreClient.alter_table("default", "hive1", thisTable);
        } catch (InvalidOperationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MetaException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private  HiveMetaStoreClient createMetastoreClient() throws Exception {
        HiveConf conf=new HiveConf();
        File f=new File(hiveConfFile+File.separator+HIVECONF);
        //log.debug("hive conf file:"+f.toString());
        if(f.exists()){
            conf.addResource(f.toURI().toURL());
        }
        // Where do I set credentials/Kerberos Principal?
        return(new HiveMetaStoreClient(conf));
    }

    // Load Hadoop configuration from a flat file.
    private  Configuration initConfig() throws IOException {
        Configuration conf = new Configuration();
        String configFile = System.getenv("HADOOPCONFIGFILE");
        if (configFile == null) {
            String configDir = System.getProperty("user.dir");
            if (configDir == null) {
                configDir = ".";
            }
            configFile = configDir + "/" + HADOOPCONFIG;
        }
        try {
            Path fileResource = new Path(configFile);
            conf.addResource(fileResource);
        } catch (Exception e) {
            System.err.println("Error reading config file " + configFile + ":" +
                    e.getMessage());
            return null;
        }
        return conf;
    }

    // Create Hive configuration
    public static HiveConf createHiveConf(String metastoreUrl) throws IOException {
        return new HiveConf();
    }

    // Create Hive configuration from Hadoop configuration.
    public  HiveConf createHiveConf(Configuration conf,
                                          String metastoreUrl) throws IOException {
        HiveConf hcatConf = new HiveConf(conf, HiveConf.class);
        hcatConf.set("hive.metastore.local", "false");
        hcatConf.setVar(HiveConf.ConfVars.METASTOREURIS, metastoreUrl);
        hcatConf.setIntVar(HiveConf.ConfVars.METASTORETHRIFTCONNECTIONRETRIES, 3);
        //hcatConf.set(HiveConf.ConfVars.SEMANTIC_ANALYZER_HOOK.varname,
        //      HCatSemanticAnalyzer.class.getName());
        hcatConf.set(HiveConf.ConfVars.HIVE_SUPPORT_CONCURRENCY.varname, "false");

        hcatConf.set(HiveConf.ConfVars.PREEXECHOOKS.varname, "");
        hcatConf.set(HiveConf.ConfVars.POSTEXECHOOKS.varname, "");
        return hcatConf;
    }



    private  void debug (){

        Configuration hadoopConfig = new Configuration();
        HiveConf hiveConfig = new HiveConf();
        System.out.println(File.separator+File.pathSeparator+File.separatorChar);

        //hadoopConfig = initConfig();
        //hiveConfig = createHiveConf(hadoopConfig, METASTOREURL);

        // We don't want an HCat client, we want a Hive Metastore client.
        try {
            HiveMetaStoreClient hmsClient = new HiveMetaStoreClient(hiveConfig);

        }
        catch (MetaException me){
            System.out.println("Exception " + me.getMessage());
        }
    }
    /*Hadoop configuration
    org.apache.hadoop.conf.Configuration dmc = new org.apache.hadoop.conf.Configuration();


    HiveConf hiveConf = new HiveConf();
        hiveConf.verifyAndSet(HiveConf.ConfVars.HIVE_AUTHORIZATION_ENABLED.varname,"true");
        hiveConf.setVar(HiveConf.ConfVars.ALLOWPARTIALCONSUMP, "fat");

    HiveMetaStoreClient hmsClient = new HiveMetaStoreClient(hiveConf)*/
}
