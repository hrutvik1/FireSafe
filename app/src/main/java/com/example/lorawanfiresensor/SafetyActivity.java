package com.example.lorawanfiresensor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;

public class SafetyActivity extends Activity {

    // message comes in the form {"temperature":XX,"humidity":XX}
    //with algo future msgs will be {"temperature":XX,"humidity":XX, "direction":XX}


    static String MQTTHOST= "XXXXXXX";
    static String USERNAME= "XXXXXX";
    static String PASSWORD= "XXXXXXX";
    String topicStr="Sensor/Data";

    MqttAndroidClient client;

    TextView mtvDirection;
    ImageButton mibtnPubLocation;
    ImageButton mibtnPubSafety;

    MqttConnectOptions options;

    IMqttToken token ;


    public void pub(String payload) {
        String topic = "User/Status";
        byte[] encodedPayload = new byte[0];
        try {
            encodedPayload = payload.getBytes("UTF-8");
            MqttMessage message = new MqttMessage(encodedPayload);
            client.publish(topic, message);
        } catch (UnsupportedEncodingException | MqttException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety); //first XML


       mibtnPubLocation =(ImageButton)findViewById(R.id.ibtnPubLocation);
        mibtnPubSafety =(ImageButton)findViewById(R.id.ibtnPubSafety);
//        mtvDirection= (TextView)findViewById(R.id.tvDirection);


        String clientId = MqttClient.generateClientId();
        client =
                new MqttAndroidClient(this.getApplicationContext(), MQTTHOST,
                        clientId);

        options = new MqttConnectOptions();

        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        deployMqtt();

        mibtnPubLocation.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is done when the user clicks the Node A button
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                pub("helpMe,location");
                Toast.makeText(SafetyActivity.this,"Help is on the way! Your location has been sent", Toast.LENGTH_LONG).show();

            }
        });

        mibtnPubSafety.setOnClickListener(new View.OnClickListener() {
            /**
             * This method is done when the user clicks the Node A button
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                pub("imSafe,name");
                Toast.makeText(SafetyActivity.this,"You have been marked safe", Toast.LENGTH_LONG).show();

            }
        });



    }

    public void deployMqtt(){

        try {
            token = client.connect(options);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    Toast.makeText(SafetyActivity.this,"successfully connected", Toast.LENGTH_LONG).show();

                    try{
                        client.subscribe(topicStr,1); //subscribing
                    }
                    catch (MqttException e){
                        e.printStackTrace();
                    }                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(SafetyActivity.this,"connection failed", Toast.LENGTH_LONG).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }


        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                String msg=new String(message.getPayload());
                String [] data=  msg.split(",");
                String []temperatureData=data[0].split(":");
                String temperature=temperatureData[1];


                String []humidityData=data[1].split(":");
                String humidity=humidityData[1];
                humidity = humidity.substring(0, humidity.length() - 1); //delete this when directions is implemented
                 }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


    }
}
