package com.deb.geeksforgeeks.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 5/28/14
 * Time: 5:46 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeForm {

    @JsonProperty
    private String org;

    @JsonProperty
    private String namespace;

    @JsonProperty
    private String name;

    @JsonProperty
    private String level;

    @JsonProperty
    private Map<String,TypeAttributeForm> attributes;

    @JsonProperty
    private int schemaVersion;

    @JsonProperty
    private String dartVersion;

    private String label;
    private boolean repeated;
    private boolean required;

    private List<TypeOutputForm> children;


    public TypeForm() {
        children = new ArrayList<TypeOutputForm>();
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Map<String, TypeAttributeForm> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, TypeAttributeForm> attributes) {
        this.attributes = attributes;
    }


    public int getSchemaVersion() {
        return schemaVersion;
    }

    public void setSchemaVersion(int schemaVersion) {
        this.schemaVersion = schemaVersion;
    }

    public String getDartVersion() {
        return dartVersion;
    }

    public void setDartVersion(String dartVersion) {
        this.dartVersion = dartVersion;
    }


    public List<TypeOutputForm> getChildren() {
        return children;
    }

    public void setChildren(List<TypeOutputForm> children) {
        this.children = children;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public TypeOutputForm getOutputNode(Stack<String> labelNames, Stack<Boolean> repeatedStack){
        TypeOutputForm outputNode = new TypeOutputForm();
        TypeTreeDataForm data = new TypeTreeDataForm();
        Stack<String> labelNamesCopy = (Stack) labelNames.clone();
        Stack<Boolean> repeatedStackCopy = (Stack) repeatedStack.clone();

        outputNode.setLabel(labelNames.peek());

        List<String> labelNameBuilder = new ArrayList<String>();
        List<Boolean> repeatedList = new ArrayList<Boolean>();

        while(!labelNamesCopy.isEmpty()){
            labelNameBuilder.add(0,labelNamesCopy.pop());
        }
        while(!repeatedStackCopy.isEmpty()){
            repeatedList.add(0, repeatedStackCopy.pop());
        }

        data.setFullname(Joiner.on(".").join(labelNameBuilder));
        if (this.getOrg() != null){
            data.setFullDataType(this.getOrg() + "/" + this.getNamespace() + "/" + this.getName());
        }
        else {
            data.setFullDataType(this.getName());
        }
        data.setDataType(this.getName());
        data.setRepeated(repeatedList);
        outputNode.setData(data);
        return  outputNode;
    }
}
