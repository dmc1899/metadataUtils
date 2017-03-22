package TOBEREMOVED;

import java.util.ArrayList;

/**
 * Created by darragh on 16/03/2017.
 */
public class Database {
    private String name;
    private String description;
    private ArrayList<Table> tables;

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

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Table> tables) {
        this.tables = tables;
    }
}
