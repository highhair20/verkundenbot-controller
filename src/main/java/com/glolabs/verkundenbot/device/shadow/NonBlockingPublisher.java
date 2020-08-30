package com.glolabs.verkundenbot.device.shadow;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;

public class NonBlockingPublisher implements Runnable {
    private final AWSIotMqttClient awsIotClient;

    public NonBlockingPublisher(AWSIotMqttClient awsIotClient) {
        this.awsIotClient = awsIotClient;
    }

    @Override
    public void run() {
        long counter = 1;

        while (true) {
            String payload = "hello from non-blocking publisher - " + (counter++);
//            AWSIotMessage message = new NonBlockingPublishListener(UpdateRejectedTopic, TestTopicQos, payload);
//            try {
//                awsIotClient.publish(message);
//            } catch (AWSIotException e) {
//                System.out.println(System.currentTimeMillis() + ": publish failed for " + payload);
//            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(System.currentTimeMillis() + ": NonBlockingPublisher was interrupted");
                return;
            }
        }
    }
}