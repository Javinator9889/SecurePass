package javinator9889.securepass.views.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import javinator9889.securepass.R;
import ru.noties.markwon.Markwon;

/**
 * Created by Javinator9889 on 17/04/2018.
 */
public class ShowEulaActivity extends AppCompatActivity {
    ActionBar supportActionBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_eula);
        supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        TextView fullEula = findViewById(R.id.eula_contents_full);
        Markwon.setMarkdown(fullEula, getString(R.string.eula_terms));
        super.onStart();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
