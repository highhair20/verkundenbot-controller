package com.glolabs.verkundenbot.device.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Configuration
@ConfigurationProperties
public class MqttProperties {

    @Value("${aws.iot.client.endpoint}")
    private String clientEndpoint;

    @Value("${aws.iot.client.id}")
    private String clientId;

    @Value("${aws.iot.client.certificateFile}")
    private String certificateFile;

    @Value("${aws.iot.client.privateKeyFile}")
    private String privateKeyFile;

    @Value("${aws.iot.thingName}")
    private String thingName;


    public String getClientEndpoint() {
        return clientEndpoint;
    }

    public void setClientEndpoint(String clientEndpoint) {
        this.clientEndpoint = clientEndpoint;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCertificateFile() {
        return certificateFile;
    }

    public void setCertificateFile(String certificateFile) {
        this.certificateFile = certificateFile;
    }

    public String getPrivateKeyFile() {
        return privateKeyFile;
    }

    public void setPrivateKeyFile(String privateKeyFile) {
        this.privateKeyFile = privateKeyFile;
    }

    public String getThingName() {
        return thingName;
    }

    public void setThingName(String thingName) {
        this.thingName = thingName;
    }
}
