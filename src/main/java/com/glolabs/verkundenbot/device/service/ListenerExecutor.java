package com.glolabs.verkundenbot.device.service;

//import com.glolabs.verkundenbot.device.gpio.DeviceController;
//import com.glolabs.verkundenbot.device.mqtt.MqttClient;
//import com.glolabs.verkundenbot.device.mqtt.MqttProperties;
//import com.glolabs.verkundenbot.device.shadow.TopicListener;
//import com.glolabs.verkundenbot.device.shadow.IoTDevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.amazonaws.services.iot.client.AWSIotConnectionStatus;
//import com.amazonaws.services.iot.client.AWSIotMessage;
//import com.amazonaws.services.iot.client.AWSIotMqttClient;
//import com.amazonaws.services.iot.client.AWSIotQos;
//import com.amazonaws.services.iot.client.AWSIotTopic;

import org.springframework.boot.CommandLineRunner;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Act as main class for spring boot application
 *
 * Implements CommandLineRunner, so that code within run method
 * is executed before application startup but after all beans are effectively created
 *
 * @author Jason Kelly
 *
 * @todo add license to all classes
 * @todo add logging to all classes
 * @todo determine where to subscribe to all topics
 * @todo publish to topics upon a failure to update the device
 * @todo subscribe to all topics
 * @todo write test cases using moquito (ref: https://github.com/Pi4J/pi4j/blob/master/pi4j-core/src/test/java/com/pi4j/io/gpio/impl/GpioControllerImplTest.java)
 */
//@Profile("!test")
@Component
public class ListenerExecutor implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(ListenerExecutor.class);

    private ListenerService listenerService;

    public ListenerExecutor(ListenerService listenerService) {
        this.listenerService = listenerService;
    }

    /**
     * Executed after the application context is loaded and
     * right before the Spring Application main method is completed.
     */
    @Override
    public void run(String... args) throws Exception {
        listenerService.execute("command line runner task");
    }

}

