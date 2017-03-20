package com.kainos.enstar.directorytometastore.metastore;

import com.kainos.enstar.common.model.Database;

/**
 * Created by darragh on 16/03/2017.
 */
public interface Metastore {
    public void updateDatabaseTableDescriptions(Database database);
}
