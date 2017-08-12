package com.kainos.enstar;

import org.apache.avro.Schema;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.kainos.enstar.common.Utils.buildPathToTestInputData;

/**
 * Created by darragh on 12/08/2017.
 */
public final class SchemaUtils {

    public static List<Schema> loadSchemas(File schemaDirectory) throws IOException {
        Schema.Parser schemaParser = new Schema.Parser();
        List<Schema> expectedSchemas = new ArrayList<>();

        for (File schemaFile : schemaDirectory.listFiles()) {
            Schema schema = schemaParser.parse(schemaFile);
            expectedSchemas.add(schema);
        }
        return expectedSchemas;
    }
}
