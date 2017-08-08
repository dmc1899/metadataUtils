package com.kainos.enstar.schema;

import com.kainos.enstar.common.Table;

import java.util.List;
import java.util.Map;

/**
 * Created by darragh on 16/03/2017.
 */
public interface SchemaGroup {
    Map<String,String> getTablesAndComments();

    List<Table> getTablesAndPrimaryKeyColumns(String primaryKeyStringIdentifier);
}
