package com.kainos.enstar.schema;

import com.kainos.enstar.model.Database;

/**
 * Created by darragh on 16/03/2017.
 */
public interface SchemaSource {
    public Database getDatabaseDefinition();
}
