package com.glolabs.verkundenbot.device;

import com.glolabs.verkundenbot.device.gpio.DeviceGpioController;
import com.glolabs.verkundenbot.device.mqtt.MqttClient;
import com.glolabs.verkundenbot.device.mqtt.MqttProperties;

import com.glolabs.verkundenbot.device.shadow.NonBlockingPublishListener;
import com.glolabs.verkundenbot.device.shadow.TestTopicListener;
import com.glolabs.verkundenbot.device.shadow.VerkundenbotDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.iot.client.AWSIotConnectionStatus;
import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotQos;

import com.amazonaws.services.iot.client.AWSIotTopic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.beans.factory.annotation.Autowired;

import com.pi4j.io.gpio.GpioController;


/**
 * 1. Act as main class for spring boot application
 * 2. Also implements CommandLineRunner, so that code within run method
 * is executed before application startup but after all beans are effectively created
 * @author Jason Kelly
 *
 * @todo add license to all classes
 * @todo add logging to all classes
 * @todo determine where to subscribe to all topics
 * @todo publish to topics upon a failure to update the device
 * @todo subscribe to all topics
 * @todo write test cases using moquito (ref: https://github.com/Pi4J/pi4j/blob/master/pi4j-core/src/test/java/com/pi4j/io/gpio/impl/GpioControllerImplTest.java)
 */
@SpringBootApplication
public class VerkundenbotDeviceCli implements CommandLineRunner {

    private static Logger LOG = LoggerFactory.getLogger(VerkundenbotDeviceCli.class);

    private static final String UpdateAcceptedTopic = "$aws/things/verkundenbot/shadow/update/accepted";
    private static final String UpdateRejectedTopic = "$aws/things/verkundenbot/shadow/update/rejected";
    private static final String GetTopic = "$aws/things/verkundenbot/shadow/get";
    private static final AWSIotQos TestTopicQos = AWSIotQos.QOS0;

    @Autowired
    private MqttProperties properties;

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(VerkundenbotDeviceCli.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    /**
     * This method will be executed after the application context is loaded and
     * right before the Spring Application main method is completed.
     */
    @Override
    public void run(String... args) throws Exception {
        LOG.info("EXECUTING : command line runner");
        for (int i = 0; i < args.length; ++i) {
            LOG.info("args[{}]: {}", i, args[i]);
        }

        //
        // create the object that replresent the device
        String thingName = "verkundenbot";
        GpioController gpioController = new DeviceGpioController();
        VerkundenbotDevice verkundenbotDevice = new VerkundenbotDevice(thingName, gpioController);

        //
        // create the mqtt client so that our device object can
        // communicate with the device shadow in AWS
        MqttClient awsIotClient = MqttClient.getInstance(properties);
        AWSIotMqttClient client = awsIotClient.connect();
        client.setWillMessage(new AWSIotMessage("client/disconnect", AWSIotQos.QOS0, client.getClientId()));
        client.attach(verkundenbotDevice);
        client.connect();

        AWSIotTopic topic = new TestTopicListener(UpdateRejectedTopic, TestTopicQos);
        client.subscribe(topic, true);

//        Thread nonBlockingPublishThread = new Thread(new NonBlockingPublisher(client));
//        nonBlockingPublishThread.start();
//        nonBlockingPublishThread.join();

//        Thread blockingPublishThread = new Thread(new BlockingPublisher(client));
//        blockingPublishThread.start();
//        blockingPublishThread.join();

        // Delete existing document if any
        verkundenbotDevice.delete();

        AWSIotConnectionStatus status = AWSIotConnectionStatus.DISCONNECTED;
        while (true) {
            AWSIotConnectionStatus newStatus = client.getConnectionStatus();
            if (!status.equals(newStatus)) {
                System.out.println(System.currentTimeMillis() + " Connection status changed to " + newStatus);
                status = newStatus;
            }

            Thread.sleep(1000);
        }
    }

//    public static class BlockingPublisher implements Runnable {
//        private final AWSIotMqttClient awsIotClient;
//
//        public BlockingPublisher(AWSIotMqttClient awsIotClient) {
//            this.awsIotClient = awsIotClient;
//        }
//
//        @Override
//        public void run() {
//            long counter = 1;
//
//            while (true) {
//                String payload = "hello from blocking publisher - " + (counter++);
//                try {
//                    awsIotClient.publish(TestTopic, payload);
//                } catch (AWSIotException e) {
//                    System.out.println(System.currentTimeMillis() + ": publish failed for " + payload);
//                }
//                System.out.println(System.currentTimeMillis() + ": >>> " + payload);
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    System.out.println(System.currentTimeMillis() + ": BlockingPublisher was interrupted");
//                    return;
//                }
//            }
//        }
//    }


//    public static class NonBlockingPublisher implements Runnable {
//        private final AWSIotMqttClient awsIotClient;
//
//        public NonBlockingPublisher(AWSIotMqttClient awsIotClient) {
//            this.awsIotClient = awsIotClient;
//        }
//
//        @Override
//        public void run() {
//            long counter = 1;
//
//            while (true) {
//                String payload = "hello from non-blocking publisher - " + (counter++);
//                AWSIotMessage message = new NonBlockingPublishListener(UpdateRejectedTopic, TestTopicQos, payload);
//                try {
//                    awsIotClient.publish(message);
//                } catch (AWSIotException e) {
//                    System.out.println(System.currentTimeMillis() + ": publish failed for " + payload);
//                }
//
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    System.out.println(System.currentTimeMillis() + ": NonBlockingPublisher was interrupted");
//                    return;
//                }
//            }
//        }
//    }
}

