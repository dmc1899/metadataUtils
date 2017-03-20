package com.kainos.enstar.directorytometastore.metastore;

import java.util.List;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.hive.conf.HiveConf;

import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hive.hcatalog.api.HCatClient;
import org.apache.hive.hcatalog.api.HCatTable;
import org.apache.hive.hcatalog.common.HCatConstants;
import org.apache.hive.hcatalog.common.HCatException;
import org.apache.hive.hcatalog.data.schema.HCatFieldSchema;
import org.apache.hive.hcatalog.data.schema.HCatSchema;

public class SampleAuth {
    public static void main(String[] args) {
        HCatClient hcatClient = null;
        try {
            String principal ="hive/quickstart.cloudera@XXX.COM";
            String keytab = "E:\\apps\\metacenter_home\\hadoop\\hive.keytab";
            System.setProperty("sun.security.krb5.debug", "true");
            System.setProperty("java.security.krb5.conf", "E:\\apps\\hadoop\\krb5.conf");
            System.setProperty("java.security.auth.login.config", "E:\\apps\\hadoop\\jaas.conf");
            HiveConf hcatConf = new HiveConf();
            hcatConf.setVar(HiveConf.ConfVars.METASTOREURIS, "thrift://server:9083");
            hcatConf.set("hadoop.security.authentication", "kerberos");
            hcatConf.set(HCatConstants.HCAT_HIVE_CLIENT_DISABLE_CACHE, "true");
            hcatConf.setVar(HiveConf.ConfVars.METASTORE_KERBEROS_PRINCIPAL, principal);
            hcatConf.setVar(HiveConf.ConfVars.METASTORE_KERBEROS_KEYTAB_FILE, keytab);
            hcatConf.setVar(HiveConf.ConfVars.METASTORE_USE_THRIFT_SASL, "true");
            UserGroupInformation.setConfiguration(hcatConf);
            UserGroupInformation.loginUserFromKeytab(principal, keytab);
            hcatClient = HCatClient.create(new Configuration(hcatConf));
            HiveMetaStoreClient hiveMetastoreClient = new HiveMetaStoreClient(hcatConf);
            list(hcatClient,hiveMetastoreClient);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (hcatClient != null)
                try {
                    hcatClient.close();
                } catch (HCatException e) {
                }
        }
    }

    private static void list(HCatClient hcatClient, HiveMetaStoreClient hiveMetastoreClient) throws Exception {
        List<String> dbs = hcatClient.listDatabaseNamesByPattern("*");
        for (String db : dbs) {
            System.out.println(db);
            List<String> tables = hcatClient.listTableNamesByPattern(db, "*");
            for (String tableString: tables) {
                HCatTable tbl = hcatClient.getTable(db, tableString);
                String tableType = tbl.getTabletype();
                String tableName = tbl.getTableName();
                System.out.println(tableType + " - " + tableName);
                System.out.println("Table Name is: " + tableName);
                System.out.println("Table Type is: " + tbl.getTabletype());
                System.out.println("Table Props are: " + tbl.getTblProps());
                List<HCatFieldSchema> fields = tbl.getCols();
                for (HCatFieldSchema f: fields) {
                    System.out.println("Field Name is: " + f.getName());
                    System.out.println("Field Type String is: " + f.getTypeString());
                    System.out.println("Field Type Category is: " + f.getTypeString());
                    if (f.getCategory().equals(HCatFieldSchema.Category.STRUCT)) {
                        HCatSchema schema = f.getStructSubSchema();
                        List<String> structFields = schema.getFieldNames();
                        for (String fieldName: structFields) {
                            System.out.println("Struct Field Name is: " + fieldName);
                        }
                    }
                }

                if (tableType.equalsIgnoreCase("View") || tableType.equalsIgnoreCase("VIRTUAL_VIEW")) {
                    org.apache.hadoop.hive.metastore.api.Table viewMetastoreObject = hiveMetastoreClient.getTable(db, tableName);
                    String sql = viewMetastoreObject.getViewOriginalText();
                    System.out.println(sql);
                }
            }
        }
    }
}