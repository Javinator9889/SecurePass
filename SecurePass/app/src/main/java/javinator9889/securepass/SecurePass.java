package javinator9889.securepass;

import android.app.Application;

import com.chamber.java.library.SharedChamber;

import org.jetbrains.annotations.Contract;

import androidx.annotation.NonNull;
import javinator9889.securepass.util.resources.PreferencesManager;

/**
 * Created by Javinator9889 on 25/03/2018.
 * Main application - class with different parameters useful for app packages
 */

public class SecurePass extends Application {
    private static SecurePass APPLICATION_INSTANCE;
    //private Context applicationContext;

    @Contract(pure = true)
    @NonNull
    public static SecurePass getApplicationInstance() {
        return APPLICATION_INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        APPLICATION_INSTANCE = this;
        PreferencesManager.instantiate(this);
//        SharedChamber.initChamber(this);
    }

    public static int getNumberOfProcessors() {
        return Runtime.getRuntime().availableProcessors();
    }
}