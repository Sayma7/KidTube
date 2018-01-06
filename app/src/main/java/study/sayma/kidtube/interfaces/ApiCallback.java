package study.sayma.kidtube.interfaces;

/**
 * Created by Sayma on 12/26/2017.
 */

public interface ApiCallback {
    void onStart();
    void onSuccess(String response);
    void onError(String message);
}
