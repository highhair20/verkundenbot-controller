package com.glolabs.verkundenbot.device.gpio;


import com.nahuellofeudo.piplates.InvalidAddressException;
import com.nahuellofeudo.piplates.InvalidParameterException;
import com.pi4j.io.gpio.*;
import java.util.ArrayList;
import com.nahuellofeudo.piplates.relayplate.RELAYPlate;

public class DeviceGpioController {

    private GpioController gpio;
    private GpioPinDigitalOutput pinRed;
    private GpioPinDigitalOutput pinGreen;
    private GpioPinDigitalOutput pinBlue;
    private RELAYPlate relayPlate;


    public DeviceGpioController() {
        gpio        = GpioFactory.getInstance();
        pinRed      = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23, "PinRed", PinState.HIGH);
        pinGreen    = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24, "pinGreen", PinState.HIGH);
        pinBlue     = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25, "pinBlue", PinState.HIGH);
        try {
            relayPlate  = new RELAYPlate(2);
        } catch (InvalidAddressException e) {

        }

    }

    public void setStatusLedBlue() {
        ArrayList<Boolean> colors = new ArrayList<Boolean>();
        colors.add(false);
        colors.add(false);
        colors.add(true);
        setRgbLed(colors);
    }

    public void setStatusLedGreen() {
        ArrayList<Boolean> colors = new ArrayList<Boolean>();
        colors.add(false);
        colors.add(true);
        colors.add(false);
        setRgbLed(colors);
    }

    public void setStatusLedRed() {
        ArrayList<Boolean> colors = new ArrayList<Boolean>();
        colors.add(true);
        colors.add(false);
        colors.add(false);
        setRgbLed(colors);
    }

    public void setStatusLedWhite() {
        ArrayList<Boolean> colors = new ArrayList<Boolean>();
        colors.add(true);
        colors.add(true);
        colors.add(true);
        setRgbLed(colors);
    }

    public void setStatusLedOff() {
        ArrayList<Boolean> colors = new ArrayList<Boolean>();
        colors.add(false);
        colors.add(false);
        colors.add(false);
        setRgbLed(colors);
    }


    public void turnGreenLedOn() {
        relayPlate.setLED();
    }

    public void turnGreenLedOff() {
        relayPlate.clearLED();
    }

    public void plugAOn() {
        try {
            relayPlate.relayOn(1);
        } catch (InvalidParameterException e) {

        }
    }

    public void plugAOff() {
        try {
            relayPlate.relayOff(1);
        } catch (InvalidParameterException e) {

        }
    }

    public void plugBOn() {
        try {
            relayPlate.relayOn(3);
        } catch (InvalidParameterException e) {

        }
    }

    public void plugBOff() {
        try {
            relayPlate.relayOff(3);
        } catch (InvalidParameterException e) {

        }
    }

    public void plugCOn() {
        try {
            relayPlate.relayOn(5);
        } catch (InvalidParameterException e) {

        }
    }

    public void plugCOff() {
        try {
            relayPlate.relayOff(5);
        } catch (InvalidParameterException e) {

        }
    }

    public void plugDOn() {
        try {
            relayPlate.relayOn(7);
        } catch (InvalidParameterException e) {

        }
    }

    public void plugDOff() {
        try {
            relayPlate.relayOff(7);
        } catch (InvalidParameterException e) {

        }
    }

    private void setRgbLed(ArrayList<Boolean> colors) {
        // TODO add pin up down commands here
        return;
    }

}

