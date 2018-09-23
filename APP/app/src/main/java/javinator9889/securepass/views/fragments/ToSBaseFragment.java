package javinator9889.securepass.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.concurrent.Future;

/**
 * Created by Javinator9889 on 23/09/2018.
 */
public abstract class ToSBaseFragment extends Fragment {
    private Future<CharSequence> mFutureText;
    private Bundle mExtras;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExtras = getActivity().getIntent().getBundleExtra("bundle");
        setParcelable();
    }

    protected Future<CharSequence> getFutureText() {
        return mFutureText;
    }

    protected void setFutureText(Future<CharSequence> text) {
        this.mFutureText = text;
    }

    protected Bundle getExtras() {
        return mExtras;
    }

    @Override
    public void onStart() {
        setText();
        super.onStart();
    }

    protected abstract void setText();

    protected abstract void setParcelable();
}
