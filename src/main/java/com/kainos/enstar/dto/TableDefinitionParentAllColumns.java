package com.kainos.enstar.dto;

import org.apache.avro.Schema;

import java.util.*;

import static com.kainos.enstar.common.Utils.EMPTY_STRING;

/**
 * Created by darragh on 10/08/2017.
 */
public class TableDefinitionParentAllColumns extends TableDefinitionParent {

    public TableDefinitionParentAllColumns(Schema schema, String tokenUsedToIdentifyPrimaryKeyColumns) {
        super();
        this.name = schema.getName();
        this.description = schema.getDoc() != null ? schema.getDoc() : EMPTY_STRING;
        this.tokenUsedToIdentifyPrimaryKeyColumns = tokenUsedToIdentifyPrimaryKeyColumns;
        populateColumnDefinitionLists(schema);
    }

    private void populateColumnDefinitionLists(Schema schema) {

        this.columnDefinitionList = new ArrayList<DefinitionChild>();

        for (Schema.Field field : schema.getFields()) {
            boolean isPrimaryKeyColumn = false;
            String columnDescription = field.doc() != null ? field.doc() : EMPTY_STRING;

            if (columnDescription.contains(this.tokenUsedToIdentifyPrimaryKeyColumns)) {
                isPrimaryKeyColumn = true;
            }

            ColumnDefinitionChild columnDefinition = new ColumnDefinitionChild(field, isPrimaryKeyColumn);
            this.columnDefinitionList.add(columnDefinition);
        }
    }
}
