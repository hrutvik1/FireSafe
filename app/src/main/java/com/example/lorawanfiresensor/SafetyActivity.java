package com.example.lorawanfiresensor;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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


import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;

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

    ImageView fire_a ;
    ImageView fire_b ;
    ImageView fire_c ;
    ImageView fire_d ;
    ImageView fire_e ;
    ImageView fire_f ;
    ImageView fire_g ;
    ImageView fire_h ;
    ImageView fire_i ;
    ImageView fire_j ;
    ImageView fire_k ;

    ImageView marka ;
    ImageView markb;
    ImageView markc ;
    ImageView markd ;
    ImageView marke ;
    ImageView markf ;
    ImageView markg ;
    ImageView markh ;
    ImageView marki ;
    ImageView markj ;
    ImageView markk ;

    ImageView exitk ;
    ImageView exitj ;
    ImageView exite ;


    MediaPlayer mp;



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

         fire_a= (ImageView)findViewById(R.id.fire_a);
        fire_b= (ImageView)findViewById(R.id.fire_b);
        fire_c= (ImageView)findViewById(R.id.fire_c);
        fire_d= (ImageView)findViewById(R.id.fire_d);
        fire_e= (ImageView)findViewById(R.id.fire_e);
        fire_f= (ImageView)findViewById(R.id.fire_f);
        fire_g= (ImageView)findViewById(R.id.fire_g);
        fire_h= (ImageView)findViewById(R.id.fire_h);
        fire_i= (ImageView)findViewById(R.id.fire_i);
        fire_j= (ImageView)findViewById(R.id.fire_j);
        fire_k= (ImageView)findViewById(R.id.fire_k);

        marka= (ImageView)findViewById(R.id.marka);
        markb= (ImageView)findViewById(R.id.markb);
        markc= (ImageView)findViewById(R.id.markc);
        markd= (ImageView)findViewById(R.id.markd);
        marke= (ImageView)findViewById(R.id.marke);
        markf= (ImageView)findViewById(R.id.markf);
        markg= (ImageView)findViewById(R.id.markg);
        markh= (ImageView)findViewById(R.id.markh);
        marki= (ImageView)findViewById(R.id.marki);
        markj= (ImageView)findViewById(R.id.markj);
        markk= (ImageView)findViewById(R.id.markk);

        exite= (ImageView)findViewById(R.id.exite);
        exitk= (ImageView)findViewById(R.id.exitk);
        exitj= (ImageView)findViewById(R.id.exitj);

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

        fire_a.setVisibility(View.INVISIBLE);
        fire_b.setVisibility(View.INVISIBLE);
        fire_c.setVisibility(View.INVISIBLE);
        fire_d.setVisibility(View.INVISIBLE);
        fire_e.setVisibility(View.INVISIBLE);
        fire_f.setVisibility(View.INVISIBLE);
        fire_g.setVisibility(View.INVISIBLE);
        fire_h.setVisibility(View.INVISIBLE);
        fire_i.setVisibility(View.INVISIBLE);
        fire_j.setVisibility(View.INVISIBLE);
        fire_k.setVisibility(View.INVISIBLE);

        marka.setVisibility(View.INVISIBLE);
        markb.setVisibility(View.INVISIBLE);
        markc.setVisibility(View.INVISIBLE);
        markd.setVisibility(View.INVISIBLE);
        marke.setVisibility(View.INVISIBLE);
        markf.setVisibility(View.INVISIBLE);
        markg.setVisibility(View.INVISIBLE);
        markh.setVisibility(View.INVISIBLE);
        marki.setVisibility(View.INVISIBLE);
        markj.setVisibility(View.INVISIBLE);
        markk.setVisibility(View.INVISIBLE);

        exite.setVisibility(View.INVISIBLE);
        exitj.setVisibility(View.INVISIBLE);
        exitk.setVisibility(View.INVISIBLE);


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

                mp=MediaPlayer.create(SafetyActivity.this, R.raw.alarm);

                mp.start();

                String msg = new String(message.getPayload()); // EXAMPLE: A,B,C,D,E
                String[] data = msg.split(",");

                //EXAMPLE INCOMING MSG: A,B,C,D,E String []data= new String[] {"A","B","C","D","E"}; //for testing


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
                fire_a.setVisibility(View.INVISIBLE);
                fire_b.setVisibility(View.INVISIBLE);
                fire_c.setVisibility(View.INVISIBLE);
                fire_d.setVisibility(View.INVISIBLE);
                fire_e.setVisibility(View.INVISIBLE);
                fire_f.setVisibility(View.INVISIBLE);
                fire_g.setVisibility(View.INVISIBLE);
                fire_h.setVisibility(View.INVISIBLE);
                fire_i.setVisibility(View.INVISIBLE);
                fire_j.setVisibility(View.INVISIBLE);
                fire_k.setVisibility(View.INVISIBLE);

                marka.setVisibility(View.INVISIBLE);
                markb.setVisibility(View.INVISIBLE);
                markc.setVisibility(View.INVISIBLE);
                markd.setVisibility(View.INVISIBLE);
                marke.setVisibility(View.INVISIBLE);
                markf.setVisibility(View.INVISIBLE);
                markg.setVisibility(View.INVISIBLE);
                markh.setVisibility(View.INVISIBLE);
                marki.setVisibility(View.INVISIBLE);
                markj.setVisibility(View.INVISIBLE);
                markk.setVisibility(View.INVISIBLE);

                exite.setVisibility(View.INVISIBLE);
                exitj.setVisibility(View.INVISIBLE);
                exitk.setVisibility(View.INVISIBLE);

                //    if(data[data.length].equals())

                if (data[0].equalsIgnoreCase(data[data.length - 1])) { //negative use case where fire is at an exit

                    Toast.makeText(SafetyActivity.this, "FIRE AT EXIT: " + data[data.length - 1], Toast.LENGTH_LONG).show();

                    //          Toast toast = Toast.makeText(SafetyActivity.this, "FIRE AT EXIT: "+data[data.length-1], Toast.LENGTH_LONG);
//the default toast view group is a relativelayout
                    //          RelativeLayout toastLayout = (RelativeLayout) toast.getView();
                    //           TextView toastTV = (TextView) toastLayout.getChildAt(0);
                    //           toastTV.setTextSize(30);
                    //           toast.show();

                }



                for (int i = 0; i < data.length; i++) {


                    if (data[i].equals("a")) {
                        fire_a.setVisibility(View.VISIBLE);
                    }
                    if (data[i].equals("b")) {
                        fire_b.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("c")) {
                        fire_c.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("d")) {
                        fire_d.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("e")) {
                        fire_e.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("f")) {
                        fire_f.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("g")) {
                        fire_g.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("h")) {
                        fire_h.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("i")) {
                        fire_i.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("j")) {
                        fire_j.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("k")) {
                        fire_k.setVisibility(View.VISIBLE);

                    }

                    //////////////

                    if (data[i].equals("A")) {
                        A.setVisibility(View.VISIBLE);
                    }
                    if (data[i].equals("B")) {
                        B.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("C")) {
                        C.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("D")) {
                        D.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("E")) {
                        E.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("F")) {
                        F.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("G")) {
                        G.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("H")) {
                        H.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("I")) {
                        I.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("J")) {
                        J.setVisibility(View.VISIBLE);

                    }
                    if (data[i].equals("K")) {
                        K.setVisibility(View.VISIBLE);

                    }

                    //////////////

                }


                //if (data[data.length - 2].equals("E")) {
                    exite.setVisibility(View.VISIBLE);
                //}
                if (data[data.length - 1].equals("J")) {
                    exitj.setVisibility(View.VISIBLE);
                }
                if (data[data.length - 1].equals("K")) {
                    exitk.setVisibility(View.VISIBLE);
                }

                /////
            //    for(int i=0;i<data.length;i++){
              //      if (!(data[i].equals("A") || data[i].equals("B") ||data[i].equals("C") ||data[i].equals("D") ||data[i].equals("E") ||data[i].equals("F") ||data[i].equals("G") ||data[i].equals("H") ||data[i].equals("I") ||data[i].equals("J") ||data[i].equals("K"))) {



                //    }
                //}

                if (data[1].equals("A")) {
                    marka.setVisibility(View.VISIBLE);
                }
                if (data[1].equals("B")) {
                    markb.setVisibility(View.VISIBLE);
                }
                if (data[1].equals("C")) {
                    markc.setVisibility(View.VISIBLE);
                }
                if (data[1].equals("D")) {
                    markd.setVisibility(View.VISIBLE);
                }
                if (data[1].equals("E")) {
                    marke.setVisibility(View.VISIBLE);
                }
                if (data[1].equals("F")) {
                    markf.setVisibility(View.VISIBLE);
                }
                if (data[1].equals("G")) {
                    markg.setVisibility(View.VISIBLE);
                }
                if (data[1].equals("H")) {
                    markh.setVisibility(View.VISIBLE);
                }
                if (data[1].equals("I")) {
                    marki.setVisibility(View.VISIBLE);
                }
                if (data[1].equals("J")) {
                    markj.setVisibility(View.VISIBLE);
                }
                if (data[1].equals("K")) {
                    markk.setVisibility(View.VISIBLE);
                }

                ////


            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


    }
}

//change iv visibility:
//ivBlueprint.setVisibility(View.VISIBLE);
