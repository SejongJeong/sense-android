package navelist.com.myapplication;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import sense.full.v1.SenseResultListener;
import sense.full.v1.SenseStreamClient;

public class MainActivity extends AppCompatActivity {
    private static String tag = "Sense";

    private AudioRecord recorder;
    private final String TAG = "SenseAndroidClient";
    private final String apiKey = "<YourApiKeyHere>";
    private final SenseStreamClient csClient = new SenseStreamClient(apiKey);;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtonHandlers();
        enableButtons(false);





    }

    private final int RECORDER_SAMPLERATE = 22050;
    private final int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private final int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_FLOAT;
    private final int BUFFER_ARRAY_MINSIZE = RECORDER_SAMPLERATE/2;
    private final int BUFFER_BYTES_MINSIZE = BUFFER_ARRAY_MINSIZE * 4;


    private boolean isRecording = false;
    private Thread recordingThread = null;


    private void startRecording() {

        /**
         * @Sense Tutorial - 1
         *
         * Set result listener
         *
         */
        csClient.setListener(new SenseResultListener() {
            @Override
            public void onResult(String result) {

                Log.d("CochlearSenseResult", result);
            }

            @Override
            public void onError(String error) {
                Log.d("CochlearSenseResult", error);
            }

            @Override
            public void onComplete() {
                csClient.end();
            }
        });

        /**
         * @Sense Tutorial - 2
         *
         * Initialize stream
         *
         */
        try{
            csClient.senseStream("event", 22050);
        }catch(Exception e){
            Log.d(TAG, e.toString());
            return;
        }

        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,RECORDER_SAMPLERATE, RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING, BUFFER_BYTES_MINSIZE*2);
        recorder.startRecording();
        isRecording = true;

        recordingThread = new Thread(new Runnable() {
            public void run() {
                handleRecordData();
            }
        }, "AudioRecorder Thread");
        recordingThread.start();
    }


    private void handleRecordData(){
        float sData[] = new float[BUFFER_ARRAY_MINSIZE ];

        while(isRecording){
            recorder.read(sData, 0, BUFFER_ARRAY_MINSIZE , AudioRecord.READ_BLOCKING);


            /**
             * @Sense Tutorial - 3
             *
             * push data (0.5 second chunk)
             * In this case, 22050 sample rate. Therefore buffer size is 11025
             *
             * Using AudioRecorder read method as "AudioRecord.READ_BLOCKING" would be convenient
             *
             */
            try{

                csClient.pushFloat32(sData);
            }catch(Exception e){
                Log.d(TAG, csClient.toString());
                Log.d(TAG, e.toString());
                break;
            }

        }
    }



    private void stopRecording() {
        // stops the recording activity
        csClient.end();
        if (null != recorder) {
            isRecording = false;
            recorder.stop();
            recorder.release();
            recorder = null;
            recordingThread = null;
        }
    }




    private void setButtonHandlers() {
        ((Button) findViewById(R.id.btnStart)).setOnClickListener(btnClick);
        ((Button) findViewById(R.id.btnStop)).setOnClickListener(btnClick);
    }

    private void enableButton(int id, boolean isEnable) {
        ((Button) findViewById(id)).setEnabled(isEnable);
    }

    private void enableButtons(boolean isRecording) {
        enableButton(R.id.btnStart, !isRecording);
        enableButton(R.id.btnStop, isRecording);
    }

    private View.OnClickListener btnClick = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnStart: {
                    enableButtons(true);
                    startRecording();
                    break;
                }
                case R.id.btnStop: {
                    enableButtons(false);
                    stopRecording();
                    break;
                }
            }
        }
    };
}
