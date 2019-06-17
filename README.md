# sense-android


## Adding client and dependencies

Copy files in src directory to your AndroidStudio project->package(sense.full.v1)

add dependencies in your build.gradle (For the full detail of the tutorial, refer example project)
```
  implementation group: 'javax.annotation', name: 'javax.annotation-api', version: '1.3.2'
  implementation 'io.grpc:grpc-all:1.15.1'
```



## Using client

Initialize client
```
SenseStreamClient csClient = new SenseStreamClient(apiKey);
```

Add listener
```
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
```

Initialize client stream

```
csClient.senseStream("event", 22050);
```

senseStream(String task, int sr)

* Important : sample rate must always be 22050


When pcm data is ready, push it into client
```
csClient.pushFloat32(sData)
```
array size must always be 0.5 second. For example in this case, as sampling rate is 22050, each block of size 11025 must be pushed


When finish, destroy connection
```
csClient.end();
```


## Example project

- requirement : minSdkVersion >= 23 (build.gradle:app -> android -> defaultConfig -> minSdkVersion)
- AudioRecorder requires sdk version of 23 or higher

- build.gradle(Module:app)
```
android{
  ..
  packagingOptions {
    exclude 'META-INF/io.netty.versions.properties'
    exclude 'META-INF/INDEX.LIST'
  }
}

dependencies{
  implementation group: 'javax.annotation', name: 'javax.annotation-api', version: '1.3.2'
  implementation 'io.grpc:grpc-all:1.15.1'
  ..
}
```
- AndroidManifest.xml
```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.RECORD_AUDIO" />
```


- Manually give mic permission (for Marshmallow or higher)

https://stackoverflow.com/questions/45650076/audioflinger-could-not-create-record-track-status-1-error-creating-audiorecor



### Code Review


```java
private void startRecording() {
        ...

        csClient.setListener(new SenseResultListener() { // Set listener here
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


        try{
            csClient.senseStream("event", 22050); // <-- init stream client, set task and sr (Currently it must be 22050)
            
        }catch(Exception e){
        
            Log.d(TAG, e.toString());
            return;
            
        }

        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,RECORDER_SAMPLERATE, RECORDER_CHANNELS,RECORDER_AUDIO_ENCODING, BUFFER_BYTES_MINSIZE*2); // <--Audio recorder is one example of sound input
        recorder.startRecording();
        
        ...
    }
    
    
    private void handleRecordData(){ // <-- This method is the place where actually puts data into the client
    
        float sData[] = new float[BUFFER_ARRAY_MINSIZE ];

        while(isRecording){
            recorder.read(sData, 0, BUFFER_ARRAY_MINSIZE , AudioRecord.READ_BLOCKING);
            // Using 'AudioRecord.READ_BLOCKING' argument, you can always get the same size of data

            try{
                csClient.pushFloat32(sData); // <-- AudioRecorder.read returns float array, so use pushFloat32 method in the client
            }
            ...

        }
    }
    
```

Several interfaces other than pushFloat32 is prepared

```
	public void pushShort(short[] shorts) throws Exception

	public void pushFloat32(float[] floats) throws Exception

	public void pushByte(byte[] bytes) throws Exception
```
Keep in mind that, in any of the above method, always put 0.5 second data

- In case of pushShort
  
  short[11025] (because sr's always 22050)

- In case of pushFloat32
  
  float[11025] (same reason)

- In case of pushByte
  
  byte array of float32 pcm little-endian

