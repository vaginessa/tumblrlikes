package nl.acidcats.tumblrlikes.data.repo.app;

import android.support.annotation.NonNull;

/**
 * Created by stephan on 29/04/2017.
 */

public interface AppRepo {
    boolean isSetupComplete();

    void setTumblrApiKey(String tumblrApiKey);

    String getTumblrApiKey();

    void setTumblrBlog(String tumblrBlog);

    String getTumblrBlog();

    void setPincode(String pinCode);

    void clearPincode();

    boolean hasPincode();

    boolean isPincodeCorrect(@NonNull String pinCode);
}
