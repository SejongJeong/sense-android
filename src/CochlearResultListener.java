package sense.full.v1;

public interface CochlearResultListener {
    void onResult(String result);
    void onError(String error);
    void onComplete();
}