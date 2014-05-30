package com.deb.geeksforgeeks.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 5/28/14
 * Time: 6:00 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties( ignoreUnknown = true)
public class TypeAttributeForm {

    @JsonProperty
    private String name;


    @JsonProperty
    private String type;

    @JsonProperty
    private String description;

    @JsonProperty
    private boolean required;

    @JsonProperty
    private boolean repeated;


    public TypeAttributeForm() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }
}
