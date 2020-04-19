package org.example.pojo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.Serializable;
import java.util.Map;


public class StudentObjectNode extends ObjectNode implements Serializable {



    public StudentObjectNode(JsonNodeFactory nc) {
        super(nc);
    }

    public StudentObjectNode(JsonNodeFactory nc, Map<String, JsonNode> kids) {
        super(nc, kids);
    }
}
