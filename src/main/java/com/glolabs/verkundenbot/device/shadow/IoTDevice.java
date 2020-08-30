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

import com.amazonaws.services.iot.client.AWSIotDevice;
import com.amazonaws.services.iot.client.AWSIotDeviceProperty;

import com.glolabs.verkundenbot.device.controller.DeviceController;
//import com.pi4j.io.gpio.GpioController;

/**
 * This class encapsulates an actual device. It extends {@link AWSIotDevice} to
 * define properties that are to be kept in sync with the AWS IoT shadow.
 */
public class IoTDevice extends AWSIotDevice {

    private DeviceController deviceController;

    @AWSIotDeviceProperty
    private boolean successState;

    @AWSIotDeviceProperty
    private boolean inProgressState;

    @AWSIotDeviceProperty
    private boolean inAlarmState;

    @AWSIotDeviceProperty
    private boolean plugAState;

    @AWSIotDeviceProperty
    private boolean plugBState;

    @AWSIotDeviceProperty
    private boolean plugCState;

    @AWSIotDeviceProperty
    private boolean plugDState;

//    @AWSIotDeviceProperty
//    private boolean ledGreenOn;
//
//    @AWSIotDeviceProperty
//    private List<Integer> ledRgbColor;

    private IoTDevice(String thingName) {
        super(thingName);
    }

    /**
     * Provide a constructor where we can inject the DeviceController
     *
     * @param thingName
     * @param deviceController
     */
    public IoTDevice(String thingName, DeviceController deviceController) {
        this(thingName);
        this.deviceController = deviceController;

    }


    public boolean getPlugAState() {
        // read the state from the relay
        boolean reportedState = this.plugAState;
        System.out.println(
                System.currentTimeMillis() + " >>> reported plug A state: " + (reportedState ? "on" : "off"));

        // return the current state
        return reportedState;
    }

    public void setPlugAState(boolean desiredState) {
        this.plugAState = desiredState;
        if (this.plugAState) {
            deviceController.turnPlugAOn();
        } else {
            deviceController.turnPlugAOff();
        }
        System.out.println(
                System.currentTimeMillis() + " <<< desired window state to " + (desiredState ? "open" : "closed"));
    }

    public boolean getPlugBState() {
        return plugBState;
    }

    public void setPlugBState(boolean plugBState) {
        this.plugBState = plugBState;
    }

    public boolean getPlugCState() {
        return plugCState;
    }

    public void setPlugCState(boolean plugCState) {
        this.plugCState = plugCState;
    }

    public boolean getPlugDState() {
        return plugDState;
    }

    public void setPlugDState(boolean plugDState) {
        this.plugDState = plugDState;
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

}
