package com.kainos.enstar.dto;

import java.util.List;

/**
 * Created by darragh on 12/08/2017.
 */
public interface DefinitionParent extends DefinitionChild {
    public List<DefinitionChild> getChildDefinitionList();
}
