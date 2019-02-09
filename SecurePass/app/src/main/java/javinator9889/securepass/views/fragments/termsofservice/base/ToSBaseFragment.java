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
package javinator9889.securepass.views.fragments.termsofservice.base;

import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javinator9889.securepass.util.resources.ISharedPreferencesManager;
import javinator9889.securepass.util.resources.PreferencesManager;
import ru.noties.markwon.Markwon;

/**
 * TODO
 */
public abstract class ToSBaseFragment extends Fragment
        implements CompoundButton.OnCheckedChangeListener {
    private Future<CharSequence> mText;
    private @IdRes int mTextId;
    private @IdRes int mCheckboxId;
    private @IdRes int mScrollView;
    private boolean mCheckboxStatus;
    private ISharedPreferencesManager mSharedPreferences;

    @Nullable
    @Override
    public abstract View onCreateView(@NonNull LayoutInflater inflater,
                                      @Nullable ViewGroup container,
                                      @Nullable Bundle savedInstanceState);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = PreferencesManager.getInstance();
        setText();
        setCheckbox();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textToChange = view.findViewById(mTextId);
        final CheckBox checkBox = view.findViewById(mCheckboxId);
        final ScrollView scrollView = view.findViewById(mScrollView);
        try {
            Markwon.setText(textToChange, mText.get());
        } catch (InterruptedException | ExecutionException e) {
            textToChange.setText(String.format("Error | %s", e.getMessage()));
        } finally {
            checkBox.setEnabled(mCheckboxStatus);
            checkBox.setChecked(mCheckboxStatus);
            checkBox.setOnCheckedChangeListener(this);
            scrollView.getViewTreeObserver()
                    .addOnScrollChangedListener(() -> {
                        int totalLength = scrollView.getHeight() + scrollView.getScrollY();
                        if (scrollView.getChildAt(0).getBottom() == totalLength)
                            if (!checkBox.isEnabled())
                                checkBox.setEnabled(true);
                    });
        }
    }

    protected void setSourceText(Future<CharSequence> futureText) {
        mText = futureText;
    }

    protected void setTextId(@IdRes int resourceId) {
        this.mTextId = resourceId;
    }

    protected void setCheckboxId(@IdRes int resourceId) {
        this.mCheckboxId = resourceId;
    }

    protected void setScrollViewId(@IdRes int resourceId) {
        this.mScrollView = resourceId;
    }

    protected void setCheckboxStatus(boolean status) {
        this.mCheckboxStatus = status;
    }

    protected Future<CharSequence> getText() {return mText;}

    @IdRes
    protected int getCheckboxId() {
        return mCheckboxId;
    }

    @IdRes
    protected int getTextId() {
        return mTextId;
    }

    protected ISharedPreferencesManager getSharedPreferences() {
        return mSharedPreferences;
    }

    protected abstract void setText();

    protected abstract void setCheckbox();

    public abstract void onCheckedChanged(CompoundButton buttonView, boolean isChecked);
}
