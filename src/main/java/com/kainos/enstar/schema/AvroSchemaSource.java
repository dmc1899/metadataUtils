package com.kainos.enstar.schema;

import org.apache.log4j.Logger;
import com.kainos.enstar.model.Database;

import java.io.IOException;
import java.nio.file.FileSystem;
import com.kainos.enstar.model.*;

/**
 * Created by darragh on 16/03/2017.
 */
public class AvroSchemaSource implements SchemaSource {

    Schema.Parser schemaParser = null;
    String directoryLocation = null;
    Database database = null;

    public AvroSchemaSource(String directoryLocation){
        this.schemaParser = new Schema.Parser();
        this.directoryLocation = directoryLocation;
        this.database = new Database();
    }

    public Database getDatabaseDefinition(){
        Database thisDb = new Database();
        return thisDb;
    };


    /** This class should expect to read from a single directory which will contain zero or more
     * Avro schema files.  It is expected that that all Avro schema files which exist in the given
     * directory relate to one schema collection which we model as a database.  The namespace
     * attribute in each Avro schema file should reflect the name of the database.  This should be identical in
     * each Avro schema file.  If not, an exception is thrown.
     * @throws IOException
     */
    private boolean isOnlyOneNamespacePresent(){

    }

    private void testReadAndWriteAvroSchemaDocumentation() throws IOException {
        String json = "{\"type\": \"record\", \"name\": \"r\", \"doc\": \"this is documentation!\", \"fields\": ["
                + "{ \"name\": \"f1\", \"type\": \"long\" }"
                + "]}";
        Schema.Parser s = new Schema.Parser();
        Schema schema = s.parse(json);
        System.out.println(schema.getDoc());
    }

}
