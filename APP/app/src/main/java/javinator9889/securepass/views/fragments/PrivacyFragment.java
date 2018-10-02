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
public class PrivacyFragment extends ToSBaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setLayoutId(R.id.privacy_policy);
        isHTML(true);
        return inflater.inflate(R.layout.privacy, container, false);
    }

    @Override
    protected void setText() {
        StringContainer container = super.getContainer();
        super.setSourceText(container.privacyText());
    }
}
