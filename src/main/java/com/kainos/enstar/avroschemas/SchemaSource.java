package com.kainos.enstar.avroschemas;

import org.apache.avro.Schema;

import java.util.ArrayList;

/**
 * Created by darragh on 03/08/2017.
 */
public interface SchemaSource {

    public ArrayList<Schema> getSchemas();

    public void setPrimaryKeyToken(String token);
}
