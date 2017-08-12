package com.kainos.enstar.schema;

import java.util.Map;

/**
 * Created by darragh on 16/03/2017.
 */
public interface SchemaGroupPrimitive {
    public String getName();

    public String getDescription();

    public Map<String, Integer> getTableNamesAndNumberOfColumnsOnly();

    public Map<String, String> getTableNamesAndDescriptionsOnly();
}
