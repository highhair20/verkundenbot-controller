/*
 * Copyright 2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.glolabs.verkundenbot.device.shadow;

import java.util.*;
import java.io.IOException;

import com.amazonaws.services.iot.client.AWSIotDevice;
import com.amazonaws.services.iot.client.AWSIotDeviceProperty;

import com.amazonaws.services.iot.client.shadow.AwsIotJsonDeserializer;

import com.pi4j.io.gpio.GpioController;

/**
 * This class encapsulates an actual device. It extends {@link AWSIotDevice} to
 * define properties that are to be kept in sync with the AWS IoT shadow.
 */
public class VerkundenbotDevice extends AWSIotDevice {

    private GpioController gpioController;

    @AWSIotDeviceProperty
    private boolean plugAOn;

    @AWSIotDeviceProperty
    private boolean plugBOn;

    @AWSIotDeviceProperty
    private boolean plugCOn;

    @AWSIotDeviceProperty
    private boolean plugDOn;

//    @AWSIotDeviceProperty
//    private boolean ledGreenOn;
//
//    @AWSIotDeviceProperty
//    private List<Integer> ledRgbColor;

    private VerkundenbotDevice(String thingName) {
        super(thingName);
    }

    /**
     * Provide a constructor where we can inject the GpioConontroller
     *
     * @param thingName
     * @param gpioController
     */
    public VerkundenbotDevice(String thingName, GpioController gpioController) {
        this(thingName);
        this.gpioController = gpioController;

    }


    public boolean isPlugAOn() {
        // read the state from the relay
        boolean reportedState = this.plugAOn;
        System.out.println(
                System.currentTimeMillis() + " >>> reported plug A state: " + (reportedState ? "on" : "off"));

        // return the current state
        return reportedState;
    }

    public void setPlugAOn(boolean desiredState) {
        // 1. update the window actuator with the desired state
        this.plugAOn = desiredState;

        if (this.plugAOn) {
            gpioController.turnOn();
        } else {
            gpioController.turnOff();
        }

        System.out.println(
                System.currentTimeMillis() + " <<< desired window state to " + (desiredState ? "open" : "closed"));
    }

    public boolean isPlugBOn() {
        return plugBOn;
    }

    public void setPlugBOn(boolean plugBOn) {
        this.plugBOn = plugBOn;
    }

    public boolean isPlugCOn() {
        return plugCOn;
    }

    public void setPlugCOn(boolean plugCOn) {
        this.plugCOn = plugCOn;
    }

    public boolean isPlugDOn() {
        return plugDOn;
    }

    public void setPlugDOn(boolean plugDOn) {
        this.plugDOn = plugDOn;
    }

//    public boolean isLedGreenOn() {
//        return ledGreenOn;
//    }
//
//    public void setLedGreenOn(boolean ledGreenOn) {
//        this.ledGreenOn = ledGreenOn;
//    }
//
//    public List<Integer> getLedRgbColor() {
//        return ledRgbColor;
//    }
//
//    public void setLedRgbColor(List<Integer> ledRgbColor) {
//        this.ledRgbColor = ledRgbColor;
//    }

//    @Override
//    public void onShadowUpdate(String jsonState) {
//        // synchronized block to serialize device accesses
//        synchronized (this) {
//            try {
//                AwsIotJsonDeserializer.deserialize(this, jsonState);
//            } catch (IOException e) {
//                System.out.println("Failed to update device");
//            }
//        }
//    }
}
