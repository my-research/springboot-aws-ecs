package com.example.awsecs;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.services.ecs.AmazonECSClient;
import com.amazonaws.services.ecs.AmazonECSClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SdkService {
    AmazonECSClientBuilder builder = AmazonECSClientBuilder.standard();
}
