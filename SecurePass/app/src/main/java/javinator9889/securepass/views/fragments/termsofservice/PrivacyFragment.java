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
 * Created by Javinator9889 on 03/11/2018 - SecurePass.
 */
package javinator9889.securepass.views.fragments.termsofservice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.R;
import javinator9889.securepass.objects.SingletonFutureContainer;
import javinator9889.securepass.util.resources.ISharedPreferencesManager;
import javinator9889.securepass.views.fragments.termsofservice.base.ToSBaseFragment;

/**
 * Fragment that shows the Privacy text.
 */
public class PrivacyFragment extends ToSBaseFragment {
    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setTextId(R.id.privacy_policy);
        setCheckboxId(R.id.checkBoxPrivacy);
        setScrollViewId(R.id.privacy_scroll);
        return inflater.inflate(R.layout.privacy, container, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setText() {
        SingletonFutureContainer futureContainer = SingletonFutureContainer.getInstance();
        super.setSourceText(futureContainer.getPrivacyText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void setCheckbox() {
        ISharedPreferencesManager sharedPreferences = getSharedPreferences();
        super.setCheckboxStatus(sharedPreferences.isPrivacyAccepted());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        super.setCheckboxStatus(isChecked);
        getSharedPreferences().setPrivacyAccepted(isChecked);
    }
}
