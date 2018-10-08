package javinator9889.securepass.views.fragments.termsOfService;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import javinator9889.securepass.R;
import javinator9889.securepass.objects.SingletonFutureContainer;
import javinator9889.securepass.util.resources.ISharedPreferencesManager;
import javinator9889.securepass.views.fragments.termsOfService.base.ToSBaseFragment;

/**
 * Created by Javinator9889 on 23/09/2018.
 */
public class LicenseFragment extends ToSBaseFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setTextId(R.id.license_text);
        setCheckboxId(R.id.checkBoxLicense);
        setScrollViewId(R.id.license_scroll);
        return inflater.inflate(R.layout.license, container, false);
    }

    @Override
    protected void setText() {
        SingletonFutureContainer futureContainer = SingletonFutureContainer.getInstance();
        super.setSourceText(futureContainer.getLicenseText());
    }

    @Override
    protected void setCheckbox() {
        ISharedPreferencesManager sharedPreferences = getSharedPreferences();
        super.setCheckboxStatus(sharedPreferences.isSoftwareLicenseAccepted());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        super.setCheckboxStatus(isChecked);
        getSharedPreferences().setSoftwareLicenseAccepted(isChecked);
    }
}
