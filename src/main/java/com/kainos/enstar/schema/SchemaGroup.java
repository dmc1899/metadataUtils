package com.kainos.enstar.schema;

import com.kainos.enstar.dto.TableDefinition;
import com.kainos.enstar.dto.TableDefinitionAllColumns;

import java.util.List;
import java.util.Map;

/**
 * Created by darragh on 16/03/2017.
 */
public interface SchemaGroup {
    Map<String, String> getTableNamesAndDescriptionsOnly();

    Map<String, Integer> getTableNamesAndNumberOfColumnsOnly();

    List<TableDefinition> getTablesAndPrimaryKeyColumns();

    List<TableDefinition> getTablesAndAllColumns();

}
