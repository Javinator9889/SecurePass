package javinator9889.securepass.views.fragments;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javinator9889.securepass.util.resources.ISharedPreferencesManager;
import javinator9889.securepass.util.resources.SharedPreferencesManager;
import ru.noties.markwon.Markwon;

/**
 * Created by Javinator9889 on 23/09/2018.
 */
public abstract class ToSBaseFragment extends Fragment
        implements CompoundButton.OnCheckedChangeListener {
    private Future<CharSequence> mText;
    private @IdRes int mTextId;
    private @IdRes int mCheckboxId;
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
        mSharedPreferences = SharedPreferencesManager.newInstance();
        setText();
        setCheckbox();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textToChange = view.findViewById(mTextId);
        CheckBox checkBox = view.findViewById(mCheckboxId);
        try {
            Markwon.setText(textToChange, mText.get());
        } catch (InterruptedException | ExecutionException e) {
            textToChange.setText("Error | " + e.getMessage());
        } finally {
            checkBox.setChecked(mCheckboxStatus);
            checkBox.setOnCheckedChangeListener(this);
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

    protected void setCheckboxStatus(boolean status) {
        this.mCheckboxStatus = status;
    }

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
