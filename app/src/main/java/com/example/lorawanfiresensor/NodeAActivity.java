package com.example.lorawanfiresensor;

        import android.app.Activity;
        import android.media.MediaPlayer;
        import android.os.Bundle;
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

        import java.io.BufferedWriter;
        import java.io.FileWriter;
        import java.io.IOException;

public class NodeAActivity extends Activity {

    // message comes in the form {"temperature":XX,"humidity":XX}
    //with algo future msgs will be {"temperature":XX,"humidity":XX, "direction":XX}


    static String MQTTHOST= "tcp://hairdresser.cloudmqtt.com:15955";
    static String USERNAME= "pslgynex";
    static String PASSWORD= "MOCo261u5hxW";
    String topicStr="Sensor/Data";

    MqttAndroidClient client;


    TextView mtvTemperatureA;
    TextView mtvHumidityA;

    ImageButton mibtnTemperatureLogA;
    ImageButton mibtnHumidityLogA;

    ImageView mivTemperature;
    ImageView mivHumidity;



    MqttConnectOptions options;

    IMqttToken token ;


     MediaPlayer alarmMP;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_node_a); //first XML


 //       alarmMP= MediaPlayer.create(this,R.r);



        mibtnTemperatureLogA =(ImageButton)findViewById(R.id.ibtnTemperatureLogA);
        mibtnHumidityLogA =(ImageButton)findViewById(R.id.ibtnTemperatureLogA);

        mivTemperature =(ImageView)findViewById(R.id.ivTemperature);
        mivHumidity =(ImageView)findViewById(R.id.ivHumidity);

        mtvTemperatureA= (TextView)findViewById(R.id.tvTemperatureA);
        mtvHumidityA=(TextView)findViewById(R.id.tvHumidityA);

        //mtvPressureA

        String clientId = MqttClient.generateClientId();
        client =
                new MqttAndroidClient(this.getApplicationContext(), MQTTHOST,
                        clientId);

        options = new MqttConnectOptions();

        options.setUserName(USERNAME);
        options.setPassword(PASSWORD.toCharArray());

        deployMqtt();


    }

    public void deployMqtt(){

        try {


            token = client.connect(options);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    alarmMP.start();

                    // We are connected
                    Toast.makeText(NodeAActivity.this,"successfully connected", Toast.LENGTH_LONG).show();

                    try{
                        client.subscribe(topicStr,1); //subscribing
                    }
                    catch (MqttException e){
                        e.printStackTrace();
                    }                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    Toast.makeText(NodeAActivity.this,"connection failed", Toast.LENGTH_LONG).show();

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




                // message comes in the form {"temperature":XX,"humidity":XX}
                //with algo future msgs will be {"temperature":XX,"humidity":XX, "direction":XX}
                String msg=new String(message.getPayload());
                String [] data=  msg.split(",");
                String []temperatureData=data[0].split(":");
                String temperature=temperatureData[2];

                mtvTemperatureA.setText(temperature);

                String []humidityData=data[1].split(":");
                String humidity=humidityData[1];
                mtvHumidityA.setText(humidity);


                String []pressureData=data[2].split(":");
                String pressure=pressureData[1];
             //   mtvPressureA.setText(pressure);


                String []deviceID=data[3].split(":");
                String device=deviceID[1];
                device = device.substring(0, device.length() - 1); //delete this when directions is implemented
//                mDeviceA.setText(device);


                int intTemp=71;

                //int intTemp= Integer.valueOf(temperature);

                if(intTemp>70){
                    alarmMP.start();
                }


                for (int i=0;i<data.length;i++) {
                    try {
                        FileWriter fw = new
                                FileWriter("C:\\Temp\\sensorAvalues.Txt");
                        BufferedWriter WriteFileBuffer = new
                                BufferedWriter(fw);

                        //Sample 02: Write Some Text to File
                        // Using Buffered Writer)
                        WriteFileBuffer.write(data[i]);
                        WriteFileBuffer.newLine();
                        //Sample 03: Close both the Writers
                        WriteFileBuffer.close();

                    } catch (IOException Ex) {

                        System.out.println(Ex.getMessage());
                    }

                }

                /*
                direction equiv:
                String []directionData=data[2].split(":");
                String direction=directionData[1];
                direction = direction.substring(0, direction.length() - 1);
                mtvTemperature.setText(direction);
                 */
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });


    }
}
