package com.glolabs.verkundenbot.device.controller;

import com.nahuellofeudo.piplates.InvalidAddressException;
import com.nahuellofeudo.piplates.InvalidParameterException;
import com.nahuellofeudo.piplates.relayplate.RELAYPlate;

import com.pi4j.io.gpio.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.HashMap;


public class DeviceController {

    private static final String RED = "r";
    private static final String GREEN = "g";
    private static final String BLUE = "b";
    private Map<String, GpioPinDigitalOutput> rgbLedPins = new HashMap<>();

    private RELAYPlate relayPlate;
    private Map<String, Integer> plugMap = new HashMap<>();
    private static final String PLUG_A = "A";
    private static final String PLUG_B = "B";
    private static final String PLUG_C = "C";
    private static final String PLUG_D = "D";

    private final GpioController gpio;


    public DeviceController() {

        this.gpio = GpioFactory.getInstance();

        // provision gpio pins for the RGB LED as output pins and turn them off
        rgbLedPins.put(RED, this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, RED, PinState.HIGH));
        rgbLedPins.put(GREEN, this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24, GREEN, PinState.HIGH));
        rgbLedPins.put(BLUE, this.gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, BLUE, PinState.HIGH));

        try {
            // initialize the relay plate and define which plug maps to which relay
            relayPlate = new RELAYPlate(2);
            plugMap.put("A", 1);
            plugMap.put("B", 3);
            plugMap.put("C", 5);
            plugMap.put("D", 7);
        } catch (InvalidAddressException e) {
            System.out.println("InvalidAddressException: " + e.getMessage());
        }

    }

    /**
     * setSuccessState
     *
     * Set LED color to success (which is blue).
     * Turn the green board LED to solid on.
     */
    public void setSuccessState() {
        Map<String, PinState> colors = new HashMap<>();
        colors.put(RED, PinState.HIGH);
        colors.put(GREEN, PinState.HIGH);
        colors.put(BLUE, PinState.LOW);
        setRgbLed(colors);
        turnGreenLedOn();
    }

    /**
     * setInProgressState
     *
     * Set LED color to success (which is green).
     * Flash the green board LED.
     */
    public void setInProgressState() {
        //
        Map<String, PinState> colors = new HashMap<>();
        colors.put(RED, PinState.HIGH);
        colors.put(GREEN, PinState.LOW);
        colors.put(BLUE, PinState.HIGH);
        //
        flashGreenLed();
    }

    public void setInAlarmState() {
        // turn led red
        Map<String, PinState> colors = new HashMap<>();
        colors.put(RED, PinState.LOW);
        colors.put(GREEN, PinState.HIGH);
        colors.put(BLUE, PinState.HIGH);
        setRgbLed(colors);
        //
        turnGreenLedOff();
        // turn on the strobe
        turnPlugDOn();
    }

    private void setRgbLed(Map<String, PinState> desiredColors) {
        for (Map.Entry<String, PinState> entry : desiredColors.entrySet()) {
            // get pin by name and set state
            rgbLedPins.get(entry.getKey()).setState(entry.getValue());
            System.out.println("Set RGB LED Pin '" + entry.getKey() +
                    "' to value = " + entry.getValue());
        }
    }

    public void turnPlugAOn() { plugOn(PLUG_A); }

    public void turnPlugAOff() { plugOff(PLUG_A); }

    public void turnPlugBOn() { plugOn(PLUG_B); }

    public void turnPlugBOff() { plugOff(PLUG_B); }

    public void turnPlugCOn() { plugOn(PLUG_C); }

    public void turnPlugCOff() { plugOff(PLUG_C); }

    /**
     * turnPlugDOn
     *
     * Plug D is reserved for the strobe so we will not expose this publicly.
     * Turn it on for 1 second.
     */
    private void turnPlugDOn() { plugOn(PLUG_D, 1000); }

    /**
     * turnPlugDOff
     *
     * Plug D is reserved for the strobe so we will not expose this publicly
     */
    private void turnPlugDOff() { plugOff(PLUG_D); }

    private void turnGreenLedOn() {
        relayPlate.setLED();
    }

    private void turnGreenLedOff() {
        relayPlate.clearLED();
    }

    private void flashGreenLed() { relayPlate.toggleLED(); }


    private void plugOn(String plugPosition) {
        try {
            relayPlate.relayOn(plugMap.get(plugPosition));
        } catch (InvalidParameterException e) {
            System.out.println("InvalidParameterException: " + e.getMessage());
        }
    }

    /**
     * plugOn
     *
     * Turn a plug on for x number of milliseconds
     *
     * @param plugPosition
     * @param interval
     */
    private synchronized void plugOn(String plugPosition, Integer interval) {
        plugOn(plugPosition);
        try {
            wait(interval);
        } catch (Exception e) {}
        plugOff(plugPosition);
    }

    private void plugOff(String plugPosition) {
        try {
            relayPlate.relayOff(plugMap.get(plugPosition));
        } catch (InvalidParameterException e) {
            System.out.println("InvalidParameterException: " + e.getMessage());
        }
    }

}

