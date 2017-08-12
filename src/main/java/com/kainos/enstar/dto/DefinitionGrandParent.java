package com.kainos.enstar.dto;

import java.util.List;

/**
 * Created by darragh on 12/08/2017.
 */
public interface DefinitionGrandParent extends DefinitionChild {
    public List<DefinitionParent> getChildDefinitionList();
    //public List<DefinitionParent> getChildDefinitionListSubset();
}
