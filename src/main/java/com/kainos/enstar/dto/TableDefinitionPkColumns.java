package com.kainos.enstar.dto;

import org.apache.avro.Schema;

import java.util.ArrayList;
import java.util.List;

import static com.kainos.enstar.common.Utils.EMPTY_STRING;

/**
 * Created by darragh on 10/08/2017.
 */
public class TableDefinitionPkColumns extends TableDefinition  {

    public TableDefinitionPkColumns(Schema schema, String tokenUsedToIdentifyPrimaryKeyColumns) {
        super();
        this.name = schema.getName();
        this.description = schema.getDoc() != null ? schema.getDoc() : EMPTY_STRING;
        this.tokenUsedToIdentifyPrimaryKeyColumns = tokenUsedToIdentifyPrimaryKeyColumns;
        populateColumnDefinitionList(schema);
    }

    private void populateColumnDefinitionList(Schema schema) {

        this.columnDefinitionList = new ArrayList<ColumnDefinition>();

        for (Schema.Field field : schema.getFields()) {
            boolean isPrimaryKeyColumn = false;
            String columnDescription = field.doc() != null ? field.doc() : EMPTY_STRING;

            if (columnDescription.contains(this.tokenUsedToIdentifyPrimaryKeyColumns)) {
                isPrimaryKeyColumn = true;
                ColumnDefinition columnDefinition = new ColumnDefinition(field, isPrimaryKeyColumn);
                this.columnDefinitionList.add(columnDefinition);
            }
        }
    }
}
