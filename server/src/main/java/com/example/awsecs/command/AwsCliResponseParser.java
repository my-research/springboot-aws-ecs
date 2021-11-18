package com.example.awsecs.command;

import com.example.awsecs.TestService;
import com.example.awsecs.dto.TaskDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class AwsCliResponseParser {

    private ObjectMapper objectMapper = new ObjectMapper();

    public String findTaskArn(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json).at("/tasks/0/containers/0/taskArn");
            return jsonNode.toString().replaceAll("\"", "");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String findEid(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json).findValue("details");

            List<TaskDetails> taskDetails = objectMapper
                    .readValue(jsonNode.toString(), new TypeReference<List<TaskDetails>>() {});

            for (TaskDetails taskDetail : taskDetails) {
                if(taskDetail.getName().equals("networkInterfaceId")) {
                    return taskDetail.getValue();
                }
            }
            return null;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String findIp(String json) {
        try {
            JsonNode jsonNode = objectMapper.readTree(json).at("/NetworkInterfaces/0/Association/PublicIp");
            return jsonNode.toString().replaceAll("\"", "");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
