package com.example.awsecs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TestService {

    public static void main(String[] args) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        // String getAllContainer = "aws ecs list-tasks --cluster KT-20211113-2 --service-name KT-20211113-2-SERVICE";
        String getContainerDetail = "aws ecs describe-tasks --cluster KT-20211113-2 --tasks arn:aws:ecs:ap-northeast-2:075534638877:task/KT-20211113-2/5f243ecf10da49fe8b607b0946af487a";

        String[] commands = getContainerDetail.split(" "); // command args 를 배열로 만듬

        ProcessBuilder bash = new ProcessBuilder(); // 터미널 오픈
        bash.command(commands); // 터미널에 날릴 명령어 추가

        Process process = bash.start(); // 터미널에 명령어 날림

        // response 값 받아오기
        BufferedReader outBuffer = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;

        while((line = outBuffer.readLine()) != null) {
            sb.append(line);
        }

        // response 의 details 부분 파싱
        JsonNode details = objectMapper.readTree(sb.toString()).findValue("details");

        List<Details> details1 = objectMapper.readValue(details.toString(), new TypeReference<List<Details>>() {});

        for (Details details2 : details1) {
            if(details2.name.equals("networkInterfaceId")) {
                String[] command2 = ("aws ec2 describe-network-interfaces --network-interface-ids " + details2.value).split(" ");
                bash = new ProcessBuilder();
                bash.command(command2);
                Process process2 = bash.start();

                BufferedReader outBuffer2 = new BufferedReader(new InputStreamReader(process2.getInputStream()));
                StringBuilder sb2 = new StringBuilder();
                String line2;

                while((line2 = outBuffer2.readLine()) != null) {
                    sb2.append(line2);
                }
                System.out.println("sb2 = " + sb2);
            }
        }
        // TODO
        // details 의 name 과 value 를 포함한 json 배열에서 name 이 networkInterface 의 value 가져오기
        // 해당 value 로 network interface 조회하는 새로운 명령어 build 후 response 파싱
    }

    @ToString
    static class Details {
        @JsonProperty("name")
        String name;
        @JsonProperty("value")
        String value;
    }
}
