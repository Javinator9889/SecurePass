package javinator9889.securepass.views.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javinator9889.securepass.R;
import javinator9889.securepass.objects.StringContainer;

/**
 * Created by Javinator9889 on 23/09/2018.
 */
public class TermsFragment extends ToSBaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setLayoutId(R.id.terms_conditions_text);
        isHTML(true);
        return inflater.inflate(R.layout.terms_conditions, container, false);
    }

    @Override
    protected void setText() {
        StringContainer container = super.getContainer();
        super.setSourceText(container.termsText());
    }
}
