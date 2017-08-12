package com.kainos.enstar.common;

import java.util.Collections;
import java.util.List;

/**
 * Created by darragh on 09/08/2017.
 */
public final class Utils {

    public static final String EMPTY_STRING="";
    public static final List<?> EMPTY_LIST = Collections.EMPTY_LIST;

    public static <T extends Comparable<T>> void sortListSafely(List<T> listToBeSorted) {
        if (listToBeSorted != null){
            Collections.sort(listToBeSorted);
        }
    }

    public static String buildPathToTestInputData(String relativePathToInputFiles) {
        return Utils.class.getClassLoader().getResource("").getPath() + relativePathToInputFiles;
    }
}
