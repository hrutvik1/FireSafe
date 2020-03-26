package com.example.lorawanfiresensor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    static String MQTTHOST= "tcp://hairdresser.cloudmqtt.com:15955";
    static String USERNAME= "pslgynex";
    static String PASSWORD= "MOCo261u5hxW";
    String topicStr="Algorithm/Data"; //change topic to Algorithm/Data

    MqttAndroidClient client;

    TextView mtvDirection;
    ImageButton mibtnPubLocation;
    ImageButton mibtnPubSafety;

    //ImageView ivBlueprint= (ImageView)findViewById(R.id.ivBlueprint);
    ImageView A ;
    ImageView B ;
    ImageView C ;
    ImageView D ;
    ImageView E ;
    ImageView F ;
    ImageView G ;
    ImageView H ;
    ImageView I ;
    ImageView J ;
    ImageView K ;





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


   //    mibtnPubLocation =(ImageButton)findViewById(R.id.ibtnPubLocation);
   //     mibtnPubSafety =(ImageButton)findViewById(R.id.ibtnPubSafety);
//        mtvDirection= (TextView)findViewById(R.id.tvDirection);

        //ivBlueprint.setVisibility(View.VISIBLE);

         A= (ImageView)findViewById(R.id.A);
         B= (ImageView)findViewById(R.id.B);
         C= (ImageView)findViewById(R.id.C);
         D= (ImageView)findViewById(R.id.D);
         E= (ImageView)findViewById(R.id.E);
         F= (ImageView)findViewById(R.id.F);
         G= (ImageView)findViewById(R.id.G);
         H= (ImageView)findViewById(R.id.H);
         I= (ImageView)findViewById(R.id.I);
         J= (ImageView)findViewById(R.id.J);
         K= (ImageView)findViewById(R.id.K);

        A.setVisibility(View.INVISIBLE);
        B.setVisibility(View.INVISIBLE);
        C.setVisibility(View.INVISIBLE);
        D.setVisibility(View.INVISIBLE);
        E.setVisibility(View.INVISIBLE);
        F.setVisibility(View.INVISIBLE);
        G.setVisibility(View.INVISIBLE);
        H.setVisibility(View.INVISIBLE);
        I.setVisibility(View.INVISIBLE);
        J.setVisibility(View.INVISIBLE);
        K.setVisibility(View.INVISIBLE);

        String clientId = MqttClient.generateClientId();
        client =
                new MqttAndroidClient(this.getApplicationContext(), MQTTHOST,
                        clientId);

        options = new MqttConnectOptions();

        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        deployMqtt();

 //       mibtnPubLocation.setOnClickListener(new View.OnClickListener() {
    //        /**
    //         * This method is done when the user clicks the Node A button
    //         *
    //         * @param v
     //        */
   //         @Override
   //         public void onClick(View v) {
    //            pub("helpMe,location");
    //            Toast.makeText(SafetyActivity.this,"Help is on the way! Your location has been sent", Toast.LENGTH_LONG).show();

    //        }
     //   });


    //    mibtnPubSafety.setOnClickListener(new View.OnClickListener() {
    //        /**
    //         * This method is done when the user clicks the Node A button
    //         *
     //        * @param v
     //        */
     //       @Override
     //       public void onClick(View v) {
      //          pub("imSafe,name");
       //         Toast.makeText(SafetyActivity.this,"You have been marked safe", Toast.LENGTH_LONG).show();

      //      }
      //  });



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

                //String []data= new String[] {"A","B","C","D","E"}; //for testing



                A.setVisibility(View.INVISIBLE);
                B.setVisibility(View.INVISIBLE);
                C.setVisibility(View.INVISIBLE);
                D.setVisibility(View.INVISIBLE);
                E.setVisibility(View.INVISIBLE);
                F.setVisibility(View.INVISIBLE);
                G.setVisibility(View.INVISIBLE);
                H.setVisibility(View.INVISIBLE);
                I.setVisibility(View.INVISIBLE);
                J.setVisibility(View.INVISIBLE);
                K.setVisibility(View.INVISIBLE);

                for(int i=0;i<data.length;i++){
                    if(data[i].equals("A")){
                        A.setVisibility(View.VISIBLE);
                    }
                    if(data[i].equals("B")){
                        B.setVisibility(View.VISIBLE);

                    }
                    if(data[i].equals("C")){
                        C.setVisibility(View.VISIBLE);

                    }
                    if(data[i].equals("D")){
                        D.setVisibility(View.VISIBLE);

                    }
                    if(data[i].equals("E")){
                        E.setVisibility(View.VISIBLE);

                    }
                    if(data[i].equals("F")){
                        F.setVisibility(View.VISIBLE);

                    }
                    if(data[i].equals("G")){
                        G.setVisibility(View.VISIBLE);

                    }
                    if(data[i].equals("H")){
                        H.setVisibility(View.VISIBLE);

                    }
                    if(data[i].equals("I")){
                        I.setVisibility(View.VISIBLE);

                    }
                    if(data[i].equals("J")){
                        J.setVisibility(View.VISIBLE);

                    }
                    if(data[i].equals("K")){
                        K.setVisibility(View.VISIBLE);

                    }
                }

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


    }
}

//change iv visibility:
//ivBlueprint.setVisibility(View.VISIBLE);
