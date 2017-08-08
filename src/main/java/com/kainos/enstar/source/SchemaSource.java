package com.kainos.enstar.source;

import org.apache.avro.Schema;

import java.util.ArrayList;

/**
 * Created by darragh on 03/08/2017.
 */
public interface SchemaSource {

    ArrayList<Schema> getSchemas();

    //public void setPrimaryKeyToken(String token);

    //public String getPrimaryKeyToken();

    int getNumberOfSchemas();
}
