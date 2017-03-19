package com.kainos.enstar.metastore;

import com.kainos.enstar.model.Database;

/**
 * Created by darragh on 16/03/2017.
 */
public interface MetastoreDestination {
    public void updateDatabaseTableDescriptions(Database database);
}
