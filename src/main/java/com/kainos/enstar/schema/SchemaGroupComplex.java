package com.kainos.enstar.schema;

import com.kainos.enstar.dto.DefinitionParent;

import java.util.List;

/**
 * Created by darragh on 16/03/2017.
 */
public interface SchemaGroupComplex extends SchemaGroupPrimitive {
    List<DefinitionParent> getTablesAndPrimaryKeyColumns();

    List<DefinitionParent> getTablesAndAllColumns();
}
