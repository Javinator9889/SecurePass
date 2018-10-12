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
import javinator9889.securepass.util.resources.SharedPreferencesManager;
import javinator9889.securepass.views.fragments.DriveContent;
import ru.noties.markwon.Markwon;

/**
 * Created by Javinator9889 on 04/04/2018.
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
