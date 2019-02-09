/*
 * Copyright © 2018 - present | SecurePass by Javinator9889
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see https://www.gnu.org/licenses/.
 *
 * Created by Javinator9889 on 25/03/2018 - SecurePass.
 */
package javinator9889.securepass;

import android.app.Application;

import com.chamber.java.library.SharedChamber;

import org.jetbrains.annotations.Contract;

import androidx.annotation.NonNull;
import javinator9889.securepass.util.resources.PreferencesManager;

/**
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
