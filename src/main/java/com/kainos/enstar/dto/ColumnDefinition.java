package com.kainos.enstar.dto;

/**
 * Created by darragh on 07/08/2017.
 */
public class ColumnDefinition {

    private String name;
    private String comment;
    private boolean isPrimaryKey;

    public String getName(){return name;}

    public void setName(String name){
        this.name = name;
    }

    public boolean isPrimaryKey(){return isPrimaryKey;}

    public void setAsPrimaryKey(boolean keyIndicator){
        this.isPrimaryKey = keyIndicator;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
