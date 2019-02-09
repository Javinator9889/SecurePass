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
 * Created by Javinator9889 on 03/04/2018 - SecurePass.
 */
package javinator9889.securepass.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javinator9889.securepass.R;
import javinator9889.securepass.SecurePass;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.objects.SingletonFutureContainer;
import javinator9889.securepass.util.resources.ISharedPreferencesManager;
import javinator9889.securepass.util.resources.PreferencesManager;
import ru.noties.markwon.Markwon;

/**
 * TODO
 */
public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ISharedPreferencesManager preferencesManager = PreferencesManager.getInstance();
        preferencesManager.databaseInitialized(false);
        if (!preferencesManager.isApplicationInitialized()) {
            prepareFutures();
            Intent firstSetupLauncher = new Intent(this, FirstSetup.class);
            startActivity(firstSetupLauncher);
            this.finish();
        } else {
            //to-do
        }
    }

    private void prepareFutures() {
        Context fragmentContext = getApplicationContext();
        IOManager ioManager = IOManager.newInstance(fragmentContext);
        ExecutorService service = Executors.newFixedThreadPool(SecurePass.getNumberOfProcessors());
        Future<CharSequence> gnuMarkdownText = service.submit(() -> {
            String gnuLicenseText = getString(R.string.eula_terms);
            return Markwon.markdown(fragmentContext, gnuLicenseText);
        });
        Future<CharSequence> privacyText = service.submit(() -> {
            try {
                String sourceText = ioManager.loadPrivacyTextMD();
                return Markwon.markdown(fragmentContext, sourceText);
            } catch (IOException e) {
                return "Unavailable";
            }
        });
        Future<CharSequence> termsConditionsText = service.submit(() -> {
            try {
                String sourceText = ioManager.loadTermsConditionsTextMD();
                return Markwon.markdown(fragmentContext, sourceText);
            } catch (IOException e) {
                return "Unavailable";
            }
        });
        SingletonFutureContainer futureContainer = SingletonFutureContainer.getInstance();
        futureContainer.setLicenseText(gnuMarkdownText);
        futureContainer.setPrivacyText(privacyText);
        futureContainer.setToSText(termsConditionsText);
    }
}
