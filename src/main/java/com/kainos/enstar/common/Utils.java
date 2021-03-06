package com.kainos.enstar.common;

import java.util.Collections;
import java.util.List;

/**
 * Created by darragh on 09/08/2017.
 */
public class Utils {

    public static <T extends Comparable<T>> void sortListSafely(List<T> listToBeSorted) {
        if (listToBeSorted != null){
            Collections.sort(listToBeSorted);
        }
    }
}
