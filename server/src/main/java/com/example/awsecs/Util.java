package com.example.awsecs;

import com.example.awsecs.command.AwsCliCommand;
import com.example.awsecs.command.AwsCliResponseParser;
import com.example.awsecs.command.BashExecutor;

public class Util {
    public static void main(String[] args) {
        BashExecutor executor = new BashExecutor();
        AwsCliCommand awsCliCommand = new AwsCliCommand();
        AwsCliResponseParser parser = new AwsCliResponseParser();

        String clusterName = "KT-20211113-2";
        String taskName = "arn:aws:ecs:ap-northeast-2:075534638877:task/KT-20211113-2/5f243ecf10da49fe8b607b0946af487a";
        String taskName2 = "arn:aws:ecs:ap-northeast-2:075534638877:task/KT-20211113-2/757dc2c61fb94210b40f9063d0ad2b87";


        // String command = awsCliCommand.getTaskDetailCommand(clusterName, taskName2);
        String command = awsCliCommand.createTaskCommand(clusterName);
        String response = executor.execute(command, false);
        String taskArn = parser.findTaskArn(response);

        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }

        command = awsCliCommand.getTaskDetailCommand(clusterName, taskArn);
        response = executor.execute(command, false);
        String eid = parser.findEid(response);

        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }

        command = awsCliCommand.getNetworkInterfaceCommand(eid);
        response = executor.execute(command, false);
        String ip = parser.findIp(response);

        System.out.println("taskArn = " + taskArn);
        System.out.println("eid = " + eid);
        System.out.println("ip = " + ip);
    }
}
