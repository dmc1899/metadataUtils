package com.kainos.enstar.source;

import org.apache.avro.Schema;

import java.util.List;

/**
 * Created by darragh on 03/08/2017.
 */
public interface SchemaSourcePrimitive {
    public String getName();
    public String getDescription();
    public String getSchemaFieldPrimaryKeyIdentifier();
}
