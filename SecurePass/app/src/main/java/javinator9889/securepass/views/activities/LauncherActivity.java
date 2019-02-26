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

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import javinator9889.securepass.R;
import javinator9889.securepass.SecurePass;
import javinator9889.securepass.io.IOManager;
import javinator9889.securepass.objects.SingletonFutureContainer;
import javinator9889.securepass.util.resources.ISharedPreferencesManager;
import javinator9889.securepass.util.resources.PreferencesManager;
import ru.noties.markwon.Markwon;

/**
 * Custom activity that decides which activity must be shown.
 */
public class LauncherActivity extends AppCompatActivity {

    /**
     * {@inheritDoc}
     */
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

    /**
     * The first time the application is executed, the user must always enter inside the
     * confirmation screen, which can take so much time to load (as it contains a considerable
     * amount of text), so those fragments are preloaded with a {@link Future} task in order to
     * be ready when the user needs to access them.
     */
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