package TOBEREMOVED;

/**
 * Created by darragh on 16/03/2017.
 */
public class Column {

    private boolean isPrimaryKey;
    private String name;
    private String description;

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
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
}
