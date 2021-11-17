package com.example.awsecs;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestService {

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String command = "aws ecs create-service " +
                "--cluster KT-20211113-2 " +
                "--service-name KT-20211113-2-SERVICE " +
                "--task-definition Gost_Student_Test_task_definition " +
                "--desired-count 5 " +
                "--launch-type FARGATE " +
                "--platform-version LATEST " +
                "--network-configuration \"awsvpcConfiguration={subnets=subnet-07439ed188223bb3f,securityGroups=sg-01a19961262ae4372,assignPublicIp=ENABLED}\"";

        String getAllContainer = "aws ecs list-tasks --cluster KT-20211113-2 --service-name KT-20211113-2-SERVICE";

        String[] commands = getAllContainer.split(" ");

        ProcessBuilder bash = new ProcessBuilder();
        bash.command(commands);

        Process process = bash.start();

        BufferedReader outBuffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = outBuffer.readLine()) != null) {
            sb.append(line);
        }

    }

    public void createService() {

    }
}
