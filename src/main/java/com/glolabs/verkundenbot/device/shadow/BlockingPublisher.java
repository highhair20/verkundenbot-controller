package com.glolabs.verkundenbot.device.shadow;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;

public class BlockingPublisher implements Runnable {

    private final AWSIotMqttClient client;
    private String topic;

    public BlockingPublisher(AWSIotMqttClient client, String topic) {
        this.client = client;
        this.topic = topic;
    }

    @Override
    public void run() {
        long counter = 1;

        while (true) {
            String payload = "hello from blocking publisher - " + (counter++);
            try {
                this.client.publish(this.topic, payload);
            } catch (AWSIotException e) {
                System.out.println(System.currentTimeMillis() + ": publish failed for " + payload);
            }
            System.out.println(System.currentTimeMillis() + ": >>> " + payload);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(System.currentTimeMillis() + ": BlockingPublisher was interrupted");
                return;
            }
        }
    }

}
