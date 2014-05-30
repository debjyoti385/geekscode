package com.deb.geeksforgeeks.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 5/28/14
 * Time: 6:53 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeTreeDataForm {

    @JsonProperty
    private String fullname;

    @JsonProperty
    private String dataType;

    @JsonProperty
    private String fullDataType;

    @JsonProperty
    private List<Boolean> repeated;

    public TypeTreeDataForm() {
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String datatype) {
        this.dataType = datatype;
    }

    public String getFullDataType() {
        return fullDataType;
    }

    public void setFullDataType(String fullDatatype) {
        this.fullDataType = fullDatatype;
    }

    public List<Boolean> getRepeated() {
        return repeated;
    }

    public void setRepeated(List<Boolean> repeated) {
        this.repeated = repeated;
    }
}
