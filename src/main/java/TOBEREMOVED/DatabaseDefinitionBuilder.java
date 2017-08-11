package TOBEREMOVED;

import com.kainos.enstar.dto.DatabaseDefinition;
import org.apache.avro.Schema;

import java.util.List;

/**
 * Created by darragh on 10/08/2017.
 */
public class DatabaseDefinitionBuilder {

        /*private String name;
        private String description;
        private List<DatabaseDefinition.TableDefinitionAllColumns> tableDefinitionList;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder tableDefinitionList(List<Schema> schemaList) {
            //TODO this should call the Table Builder
            for (Schema schema : schemaList) {
                DatabaseDefinition.TableDefinitionAllColumns tableDefinition = new DatabaseDefinition.TableDefinitionAllColumns();
                tableDefinition.setName(schema.getName());
                tableDefinition.setDescription(schema.getDoc());
                tableDefinition.setColumnDefinitionList(tableDefinition.schema.getFields());
                this.tableDefinitionList.add(tableDefinition);
            }

            return this;
        }

        public DatabaseDefinition build() {
            return new DatabaseDefinition(this);
        }

    }*/
}
