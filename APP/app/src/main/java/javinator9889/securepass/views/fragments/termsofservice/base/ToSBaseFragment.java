package javinator9889.securepass.views.fragments.termsofservice.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
 * Created by Javinator9889 on 23/09/2018.
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
