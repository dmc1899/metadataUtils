package TOBEREMOVED;

import java.util.ArrayList;

/**
 * Created by darragh on 16/03/2017.
 */
public class Table {
    private String namespace;
    private String name;
    private String description;
    private ArrayList<Column> columns;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Column> getColumns() {
        return columns;
    }

    public void setColumns(ArrayList<Column> columns) {
        this.columns = columns;
    }

    public ArrayList<Column> getPrimaryKeyColumns(){
        ArrayList<Column> primaryKeys = new ArrayList<Column>();

        for (Column column: this.columns){
            if (column.isPrimaryKey()){
                primaryKeys.add(column);
            }
        }
        return primaryKeys;
    }

}
