package com.example.awsecs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class TestServiceTest {

    private TestService testService = new TestService();

    @Test
    @DisplayName("run command")
    void createService() {
        testService.createService();
    }

}