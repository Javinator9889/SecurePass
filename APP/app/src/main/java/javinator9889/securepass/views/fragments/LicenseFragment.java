package javinator9889.securepass.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import javinator9889.securepass.R;
import javinator9889.securepass.objects.ParcelableShared;

/**
 * Created by Javinator9889 on 23/09/2018.
 */
public class LicenseFragment extends ToSBaseFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setContentView(R.layout.license);
    }

    @Override
    protected void setText() {
        CharSequence text = null;
        try {
            text = super.getFutureText().get();
        } catch (InterruptedException | ExecutionException e) {
            text = getString(R.string.eula_terms);
        } finally {
            TextView gnuTextView = getActivity().findViewById(R.id.eula_contents_full);
            gnuTextView.setText(text);
        }
    }

    @Override
    protected void setParcelable() {
        ParcelableShared<CharSequence> markdownTextContainer = getExtras()
                .getParcelable("markdown");
        setFutureText(markdownTextContainer.getData());
    }
}
