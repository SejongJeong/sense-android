package sense.full.v1;

public interface SenseResultListener {
    void onResult(String result);
    void onError(String error);
    void onComplete();
}