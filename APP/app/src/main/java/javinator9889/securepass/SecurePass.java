package javinator9889.securepass;

import android.app.Application;
import android.support.annotation.NonNull;

import com.chamber.java.library.SharedChamber;

import javinator9889.securepass.util.resources.SharedPreferencesManager;

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
        SharedPreferencesManager sharedPreferencesInstantiation =
                SharedPreferencesManager.newInstance();
        sharedPreferencesInstantiation.initSharedPreferences();
        SharedChamber.initChamber(this);
    }

    public static int getNumberOfProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }
}
