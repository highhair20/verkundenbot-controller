package com.glolabs.verkundenbot.device.service;

import com.amazonaws.services.iot.client.*;

import com.glolabs.verkundenbot.device.controller.DeviceController;
import com.glolabs.verkundenbot.device.mqtt.MqttClient;
import com.glolabs.verkundenbot.device.mqtt.MqttProperties;
import com.glolabs.verkundenbot.device.shadow.BlockingPublisher;
import com.glolabs.verkundenbot.device.shadow.TopicListener;
import com.glolabs.verkundenbot.device.shadow.IoTDevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class ListenerService {

    private static Logger logger = LoggerFactory.getLogger(ListenerService.class);

    private static final String UpdateAcceptedTopic = "$aws/things/verkundenbot/shadow/update/accepted";
    private static final String UpdateRejectedTopic = "$aws/things/verkundenbot/shadow/update/rejected";
    private static final String GetTopic = "$aws/things/verkundenbot/shadow/get";
    private static final AWSIotQos TopicQos = AWSIotQos.QOS0;

    @Autowired
    private MqttProperties properties;


    public void execute(String task) {
        logger.info("do " + task);

        //
        // create the object that represents the device
        String thingName = "verkundenbot";

//        DeviceController deviceController = new DeviceController();
//        IoTDevice device = new IoTDevice(thingName, deviceController);
//
//        //
//        // create the mqtt client so that our device object can
//        // communicate with the device shadow in AWS
//        MqttClient awsIotClient = MqttClient.getInstance(properties);
//        AWSIotMqttClient client = awsIotClient.connect();
//        client.setWillMessage(new AWSIotMessage("client/disconnect", AWSIotQos.QOS0, client.getClientId()));
//        try {
//            client.attach(device);
//            client.connect();
//        } catch (AWSIotException e) {
//            logger.error(e.getMessage());
//        }
//
//        //
//        // @todo  set up listeners for all topics here
//        AWSIotTopic topic = new TopicListener(UpdateRejectedTopic, TopicQos);
//        try {
//            client.subscribe(topic, true);
//        } catch (AWSIotException e) {
//            logger.error(e.getMessage());
//        }
//
//        // Delete existing document if any
//        try {
//            device.delete();
//        } catch (AWSIotException e) {
//            logger.error(e.getMessage());
//        }
//
//
//        AWSIotConnectionStatus status = AWSIotConnectionStatus.DISCONNECTED;
//        while (true) {
//            AWSIotConnectionStatus newStatus = client.getConnectionStatus();
//            if (!status.equals(newStatus)) {
//                System.out.println(System.currentTimeMillis() + " Connection status changed to " + newStatus);
//                status = newStatus;
//            }
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                logger.error(e.getMessage());
//            }
//        }
    }

}