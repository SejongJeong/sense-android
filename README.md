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



