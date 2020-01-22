package com.glolabs.verkundenbot.device.mqtt;


import com.glolabs.verkundenbot.device.util.PrivateKeyReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.amazonaws.services.iot.client.AWSIotConnectionStatus;
//import com.amazonaws.services.iot.client.AWSIotDevice;
//import com.amazonaws.services.iot.client.AWSIotException;
//import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
//import com.amazonaws.services.iot.client.AWSIotQos;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.*;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.List;

public class MqttClient {

    private static Logger LOG = LoggerFactory.getLogger(MqttClient.class);
    private MqttProperties properties;
    private AWSIotMqttClient awsIotClient;

    private static MqttClient INSTANCE;

    public static class KeyStorePasswordPair {
        public KeyStore keyStore;
        public String keyPassword;

        public KeyStorePasswordPair(KeyStore keyStore, String keyPassword) {
            this.keyStore = keyStore;
            this.keyPassword = keyPassword;
        }
    }

    private MqttClient (MqttProperties properties) {
        this.properties = properties;

        LOG.info("iot endpoint: " + properties.getClientEndpoint());
        LOG.info("iot client id: " + properties.getClientId());
        LOG.info("iot certificate file: " + properties.getCertificateFile());
        LOG.info("iot private key file: " + properties.getPrivateKeyFile());
        LOG.info("iot thing name: " + properties.getThingName());

    }

    public synchronized static MqttClient getInstance(MqttProperties properties) {
        if(INSTANCE == null) {
            INSTANCE = new MqttClient(properties);
        }
        return INSTANCE;
    }


    public AWSIotMqttClient connect () {
        String certificateFile = properties.getCertificateFile();
        String privateKeyFile = properties.getPrivateKeyFile();

        if (certificateFile != null && privateKeyFile != null) {
            KeyStorePasswordPair pair = this.getKeyStorePasswordPair(certificateFile, privateKeyFile, "RSA");

            System.out.println("key store: " + pair.keyStore);

            awsIotClient = new AWSIotMqttClient(
                    properties.getClientEndpoint(),
                    properties.getClientId(),
                    pair.keyStore,
                    pair.keyPassword);
        }

//        if (awsIotClient == null) {
//            String awsAccessKeyId = arguments.get("awsAccessKeyId", SampleUtil.getConfig("awsAccessKeyId"));
//            String awsSecretAccessKey = arguments.get("awsSecretAccessKey", SampleUtil.getConfig("awsSecretAccessKey"));
//            String sessionToken = arguments.get("sessionToken", SampleUtil.getConfig("sessionToken"));
//
//            if (awsAccessKeyId != null && awsSecretAccessKey != null) {
//                awsIotClient = new AWSIotMqttClient(clientEndpoint, clientId, awsAccessKeyId, awsSecretAccessKey,
//                        sessionToken);
//            }
//        }

        if (awsIotClient == null) {
            throw new IllegalArgumentException("Failed to construct client due to missing certificate or credentials.");
        } else {
            return this.awsIotClient;
        }
    }


    private KeyStorePasswordPair getKeyStorePasswordPair(final String certificateFile, final String privateKeyFile) {
        return getKeyStorePasswordPair(certificateFile, privateKeyFile, null);
    }

    private KeyStorePasswordPair getKeyStorePasswordPair(final String certificateFile, final String privateKeyFile,
                                                               String keyAlgorithm) {
        if (certificateFile == null || privateKeyFile == null) {
            System.out.println("Certificate or private key file missing");
            return null;
        }
        System.out.println("Cert file:" +certificateFile + " Private key: "+ privateKeyFile);

        final PrivateKey privateKey = loadPrivateKeyFromFile(privateKeyFile, keyAlgorithm);

        final List<Certificate> certChain = loadCertificatesFromFile(certificateFile);

        if (certChain == null || privateKey == null) return null;

        return getKeyStorePasswordPair(certChain, privateKey);
    }

    private KeyStorePasswordPair getKeyStorePasswordPair(final List<Certificate> certificates, final PrivateKey privateKey) {
        KeyStore keyStore;
        String keyPassword;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);

            // randomly generated key password for the key in the KeyStore
            keyPassword = new BigInteger(128, new SecureRandom()).toString(32);

            Certificate[] certChain = new Certificate[certificates.size()];
            certChain = certificates.toArray(certChain);
            keyStore.setKeyEntry("alias", privateKey, keyPassword.toCharArray(), certChain);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            System.out.println("Failed to create key store");
            return null;
        }

        return new KeyStorePasswordPair(keyStore, keyPassword);
    }

    private List<Certificate> loadCertificatesFromFile(final String filename) {

        try {
            InputStream is = getClass().getResourceAsStream(filename);
            BufferedInputStream stream = new BufferedInputStream(is);
            final CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            return (List<Certificate>) certFactory.generateCertificates(stream);
        } catch (CertificateException e) {
            System.out.println("Failed to load certificate file " + filename);
        }
        return null;
    }


    private PrivateKey loadPrivateKeyFromFile(final String filename, final String algorithm) {
        PrivateKey privateKey = null;

        try {
            InputStream is = getClass().getResourceAsStream(filename);
            DataInputStream stream = new DataInputStream(is);
            privateKey = PrivateKeyReader.getPrivateKey(stream, algorithm);
        } catch (IOException | GeneralSecurityException e) {
            System.out.println("Failed to load private key from file " + filename);
        }

        return privateKey;
    }


}
