package devanboy.gowarrior;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.gowarrior.GPIO;


import java.util.Locale;

public class MainActivity extends ActionBarActivity {
    private TextToSpeech myTTS;
    GPIO gpio = new GPIO();

    int count = 0;
    int led_bcm = 44;
    int hello_btn_bcm = 64;
    int light_btn_bcm = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //GPIO gpio = new GPIO();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Set up Text-To-Speech
        myTTS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener(){
            public void onInit (int status){
                if (status != TextToSpeech.ERROR) myTTS.setLanguage(Locale.CHINESE);
            }
        });

        //Set Output PIN of LED
        gpio.setmode(GPIO.BCM);
        gpio.setup(led_bcm, GPIO.OUTPUT);
        gpio.output(led_bcm, 1);

        //Set Input Button of SayHello and LightSwitch
        //gpio.setup(hello_btn_bcm, GPIO.INPUT);
        //gpio.setup(light_btn_bcm, GPIO.INPUT);
        /*
        while (true) {
            if (gpio.input(hello_btn_bcm) == 1){sayhi(null);}
            if (gpio.input(light_btn_bcm) == 1){light(null);}
        }
        */

    }

    //TTS
    public void saying(String input) {
        myTTS.speak(input, TextToSpeech.QUEUE_FLUSH, null);
    }
    public void sayhi(View v) {
        TextView indicate = (TextView) findViewById(R.id.maintext);
        String answer = "";
        count++;
        switch (count){
            case 4:{
                answer = "老娘要生氣嘍！";
                count = 0;
                break;
            }
            case 3:{
                answer = "你煩不煩呀？";
                break;
            }
            default:{
                answer = "我很好，謝謝";
                break;
            }
        }
        indicate.setText(answer);
        saying(answer);
    }
    public void light (View v){
        TextView indicate = (TextView) findViewById(R.id.maintext);
        ToggleButton lightsw = (ToggleButton) findViewById(R.id.lightswitch);
        String answer = "";
        if (lightsw.isChecked()) {
            answer = "好呀，我幫你開燈";
            gpio.output(led_bcm, 1);
        }
        else {
            answer = "好呀，我幫你關燈";
            gpio.output(led_bcm, 0);
        }
        indicate.setText(answer);
        saying(answer);
    }

}
