package javinator9889.securepass;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Javinator9889 on 25/03/2018.
 * Main application - class with different parameters useful for app packages
 */

public class SecurePass extends Application {
    private static SecurePass APPLICATION_INSTANCE;
    //private Context applicationContext;

    @NonNull
    public static SecurePass getApplicationInstance() {
        return APPLICATION_INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION_INSTANCE = this;
        //this.applicationContext = getApplicationContext();
    }
}
