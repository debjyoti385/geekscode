package com.deb.geeksforgeeks.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 5/28/14
 * Time: 6:51 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties (ignoreUnknown = true)
public class TypeOutputForm {

    @JsonProperty
    private String label;

    @JsonProperty
    private TypeTreeDataForm data;

    @JsonProperty
    private List<TypeOutputForm> children;

    public TypeOutputForm() {
        children = new ArrayList<TypeOutputForm>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public TypeTreeDataForm getData() {
        return data;
    }

    public void setData(TypeTreeDataForm data) {
        this.data = data;
    }

    public List<TypeOutputForm> getChildren() {
        return children;
    }

    public void setChildren(List<TypeOutputForm> children) {
        this.children = children;
    }


}


