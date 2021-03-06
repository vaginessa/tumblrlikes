package nl.acidcats.tumblrlikes;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.pixplicity.easyprefs.library.Prefs;

import io.fabric.sdk.android.Fabric;
import nl.acidcats.tumblrlikes.di.DaggerMyComponent;
import nl.acidcats.tumblrlikes.di.MyComponent;
import nl.acidcats.tumblrlikes.di.MyModule;

/**
 * Created by stephan on 28/03/2017.
 */

public class LikesApplication extends Application {
    private static final String TAG = LikesApplication.class.getSimpleName();

    private MyComponent _myComponent;
    private boolean _isFreshRun = true;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        Prefs.initPrefs(this);

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);

        _myComponent = DaggerMyComponent.builder()
                .myModule(new MyModule(this, analytics))
                .build();

    }

    public MyComponent getMyComponent() {
        return _myComponent;
    }

    public boolean isFreshRun() {
        return _isFreshRun;
    }

    public void setFreshRun(boolean freshRun) {
        _isFreshRun = freshRun;
    }
}
