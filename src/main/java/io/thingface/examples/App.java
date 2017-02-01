package io.thingface.examples;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class App
{
    public static final String THINGFACE_HOST = "xyz.thingface.io";
    public static final int THINGFACE_PORT = 8883;
    public static final String THINGFACE_DEVICE_ID = "your_device_id";
    public static final String THINGFACE_DEVICE_SECRET = "your_device_secret";

    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException, MqttException
    {
        DeviceExample device = new DeviceExample(
            THINGFACE_HOST,
            THINGFACE_PORT,
            THINGFACE_DEVICE_ID,
            THINGFACE_DEVICE_SECRET
        );

        device.run();

        System.in.read();

        device.stop();
    }
}
