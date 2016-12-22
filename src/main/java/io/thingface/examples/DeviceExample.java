package io.thingface.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class DeviceExample {

    private final String host;
    private final int port;
    private final String deviceId;
    private final String deviceSecret;

    final MqttClient client;
    private Timer timer;

    public DeviceExample(String host, int port, final String deviceId, String deviceSecret) throws MqttException {
        this.host = host;
        this.port = port;
        this.deviceId = deviceId;
        this.deviceSecret = deviceSecret;

        // setup mqtt client
        client = new MqttClient("ssl://" + host + ":" + port, deviceId, new MemoryPersistence());
        client.setCallback(new MqttCallback() {

            @Override
            public void connectionLost(Throwable cause) {
                //Called when the client lost the connection to the broker                
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                if (topic.startsWith("tf/c/")) { // is command                        
                    // parse command
                    DeviceCommand cmd = Utils.toCommand(message.getPayload());

                    System.out.println("Received:");
                    System.out.print("command " + cmd.getName());
                    System.out.print(" with arguments " + cmd.getArguments().toString());
                    System.out.println();

                    if ("shutdown".equals(cmd.getName())) {
                        System.out.println(deviceId + " is shutting down");
                        
                        timer.cancel();
                        client.disconnect();
                        System.exit(0);
                    }
                    
                    if ("say".equals(cmd.getName())) {                        
                        System.out.println(cmd.getArguments().get(0));
                    }
                    // .. handle next commands
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                //Called when a outgoing publish is complete
            }
        });
    }

    public void run() throws KeyManagementException, NoSuchAlgorithmException, MqttException {
        // setup connection
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1_1);
        options.setUserName(deviceId);
        options.setPassword(deviceSecret.toCharArray());
        options.setSocketFactory(Utils.getSocketFactory());

        client.connect(options);
        System.out.println(deviceId + " is connected to thingface server");
        
        // subscribe commands from all users
        client.subscribe("tf/c/+/"+deviceId);
        
        // subscribe commands from user with username "me"
        //client.subscribe("tf/c/me/"+deviceId);

        // simple timer for sending random sensor value 
        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                double sensorValue = (Math.random() * 50);
                sendSensorValue("sensor1", sensorValue);
            }

        }, 5000, 5000);
    }

    public void stop() throws MqttException {
        if (timer != null) {
            timer.cancel();
        }
        if(client.isConnected()){
            client.disconnect();
        }        
    }

    private void sendSensorValue(String sensorId, double value) {
        SensorValue sensor = new SensorValue();
        sensor.setValue(value);
        try {
            client.publish("tf/d/" + deviceId + "/" + sensorId, Utils.toPayload(sensor), 0, false);
        } catch (MqttException ex) {
            ex.printStackTrace();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
    }
}
