package com.deb.geeksforgeeks.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.base.Joiner;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
/**
 * Created with IntelliJ IDEA.
 * User: debjyoti.paul
 * Date: 5/28/14
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class TypeTree {

    private TypeForm[] types;
    private Map<String,TypeForm> typeMap;
    private TypeForm rootNode;
    private String rootLabel;

    private Stack<String> labelStack;
    private Stack<Boolean> repeatedStack;
    private Stack<TypeOutputForm> outputNodeStack;

    private Map<String,Boolean> visitedMap;

    TypeTree(TypeForm[] types){
        this.types = types;
        this.typeMap = new HashMap<String, TypeForm>();
        this.visitedMap = new HashMap<String, Boolean>();
        labelStack = new Stack();
        repeatedStack = new Stack<Boolean>();
        outputNodeStack = new Stack<TypeOutputForm>();
    }

    TypeTree(Map<String,TypeForm> typeMap, String rootLabel, TypeForm rootNode){
        this.typeMap = typeMap;
        this.visitedMap = new HashMap<String, Boolean>();

        this.rootLabel = rootLabel;
        this.rootNode = rootNode;

        labelStack = new Stack();
        repeatedStack = new Stack<Boolean>();
        outputNodeStack = new Stack<TypeOutputForm>();
    }


    public String getTree() {

        TypeOutputForm output = null;
        Stack typeStack = new Stack();


        typeStack.push(rootNode);
        labelStack.push(rootLabel);
        repeatedStack.push(false);

        while(!typeStack.isEmpty()) {
            TypeForm node = (TypeForm) typeStack.peek();

            Stack labelStackCopy = (Stack) labelStack.clone();
            String fullLabel = Joiner.on("/").join(labelStackCopy);
            if ( visitedMap.get(fullLabel) == null || visitedMap.get(fullLabel) == false){
                visitedMap.put(fullLabel,true);
                if (outputNodeStack.isEmpty()){
                        TypeOutputForm temp = node.getOutputNode(labelStack, repeatedStack);
                        output = temp;
                        outputNodeStack.push(output);
                }
                else {
                        TypeOutputForm temp = node.getOutputNode(labelStack, repeatedStack);
                        outputNodeStack.peek().getChildren().add(temp);
                        outputNodeStack.push(temp);
                }
            }
            TypeForm child = getUnvisitedChildNode(node);

            if(child != null) {
                typeStack.push(child);
                labelStack.push(child.getLabel());
                repeatedStack.push(child.isRepeated());
            }
            else {
                typeStack.pop();
                labelStack.pop();
                outputNodeStack.pop();
                repeatedStack.pop();
            }
        }
        ObjectMapper mapper =  new ObjectMapper();

        try {
            return mapper.writeValueAsString(output);
        } catch (JsonProcessingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }


    public Map<String,TypeForm> getTypeNameTypeMap(TypeForm[] type){
        for(int i=0; i< types.length; i++){
            typeMap.put(types[i].getName(),types[i]);
        }
        return typeMap;
    }

    private TypeForm getUnvisitedChildNode(TypeForm typeNode){

        for ( String name: typeNode.getAttributes().keySet()){
            TypeAttributeForm attribute = typeNode.getAttributes().get(name);

            Stack labelStackCopy = (Stack) labelStack.clone();
            labelStackCopy.push(name);
            String fullLabel = Joiner.on("/").join(labelStackCopy);
            labelStackCopy.pop();
            if (attribute == null ){
                continue;
            }
            if(visitedMap.get(fullLabel) !=null && visitedMap.get(fullLabel) == true){
                continue;
            }

            String typeName = attribute.getType();
            int index =  typeName.lastIndexOf("/");
            if(index >= 0 ){
                TypeForm type = typeMap.get(typeName.substring(index+1));
                    type.setLabel(name);
                    type.setRepeated(attribute.isRepeated());
                    type.setRequired(attribute.isRequired());
                    return type;

            }
            else{
                TypeForm type = new TypeForm();
                    type.setAttributes(new HashMap<String, TypeAttributeForm>());
                    type.setLabel(name);
                    type.setName(attribute.getType());
                    type.setRepeated(attribute.isRepeated());
                    type.setRequired(attribute.isRequired());
                    return type;
            }
        }
        return null;
    }


    private static JsonNode sendGet(String url) throws Exception{

        URL urlObj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode result = mapper.readValue(inputStream, TypeFactory.defaultInstance().constructType(JsonNode.class));
        inputStream.close();

        return result;
    }


    public static void main(String args[]) throws Exception {
        Map <String,TypeForm> typeMap = new HashMap<String, TypeForm>();
        ObjectMapper mapper = new ObjectMapper();


        JsonParser jsonParser = new JsonFactory().createParser( sendGet("http://stage-pf1.stage.ch.flipkart.com:25005/types").toString());
        jsonParser.nextToken();
        while (jsonParser.nextToken() == JsonToken.START_OBJECT) {
            TypeForm type = mapper.readValue(jsonParser, TypeForm.class);
            typeMap.put(type.getName(),type);
            System.out.println(type.getName());
        }
        TypeTree tree = new TypeTree(typeMap, "productGroup",typeMap.get("ProductGroup"));
        System.out.println(tree.getTree());
        //
        return;
    }
}


