package io.thingface.examples;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import org.eclipse.paho.client.mqttv3.MqttException;

public class App
{
    public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException, MqttException
    {
        //DeviceExample device = new DeviceExample("xyz.thingface.io", 8883, "your_device_id", "your_device_secret");
        DeviceExample device = new DeviceExample(
            "dev-app.thingface.io", 
            8883, 
            "mydevice", 
            "TpNnrNzeUbx7WD1WwTzmDQYANgz9FP"
        );
        
        device.run();
        
        System.in.read();
        
        device.stop();
    }
}
