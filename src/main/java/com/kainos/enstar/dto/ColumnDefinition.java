package com.kainos.enstar.dto;

import org.apache.avro.Schema;

import static com.kainos.enstar.common.Utils.EMPTY_STRING;

/**
 * Created by darragh on 10/08/2017.
 */
public class ColumnDefinition {
    private String name;
    private String description;
    private boolean isPrimaryKey = false;

    public ColumnDefinition(Schema.Field field, boolean isPrimaryKey) {
        this.name = field.name();
        this.description = field.doc() != null ? field.doc() : EMPTY_STRING;
        this.isPrimaryKey = isPrimaryKey;
    }

    public String getName(){return name;}

    public void setName(String name){
        this.name = name;
    }

    public boolean isPrimaryKey(){return isPrimaryKey;}

    public void setAsPrimaryKey(boolean keyIndicator){
        this.isPrimaryKey = keyIndicator;
    }

    public String getComment() {
        return description;
    }

    public void setComment(String comment) {
        this.description = comment;
    }
}

