package javinator9889.securepass.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javinator9889.securepass.objects.StringContainer;
import ru.noties.markwon.Markwon;
import ru.noties.markwon.SpannableConfiguration;
import ru.noties.markwon.renderer.html.SpannableHtmlParser;

/**
 * Created by Javinator9889 on 23/09/2018.
 */
public abstract class ToSBaseFragment extends Fragment {
    private String mText;
    private StringContainer mContainer;
    private boolean mIsHTML;
    private int mResourceId;

    @Nullable
    @Override
    public abstract View onCreateView(@NonNull LayoutInflater inflater,
                                      @Nullable ViewGroup container,
                                      @Nullable Bundle savedInstanceState);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContainer = getActivity().getIntent()
                .getBundleExtra("bundle")
                .getParcelable("markwon");
        setText();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        String text = null;
//        try {
//            text = mFutureText.get().toString();
//        } catch (InterruptedException | ExecutionException e) {
//            text = "Unavailable";
//        } finally {
        Context fragmentContext = view.getContext();
        TextView textToChange = view.findViewById(mResourceId);
        SpannableConfiguration config;
        if (mIsHTML) {
            config = SpannableConfiguration.builder(fragmentContext)
                    .htmlParser(SpannableHtmlParser.builder().build())
                    .build();
        } else {
            config = SpannableConfiguration.builder(fragmentContext)
                    .build();
        }
        Markwon.setMarkdown(textToChange, config, mText);
//        Markwon.setMarkdown(textToChange, text);
//            textToChange.setText(text);
        }

//    protected Future<CharSequence> getFutureText() {
//        return mFutureText;
//    }
//
//    protected void setFutureText(Future<CharSequence> text) {
//        this.mFutureText = text;
//    }

    protected void setSourceText(String text) {
        mText = text;
    }

    protected void isHTML(boolean isHTML) {
        this.mIsHTML = isHTML;
    }

    protected StringContainer getContainer() {
        return mContainer;
    }

    protected void setLayoutId(@IdRes int resourceId) {
        this.mResourceId = resourceId;
    }

    @LayoutRes
    protected int getResourceId() {
        return mResourceId;
    }

//    @Override
//    public void onStart() {
//        setText();
//        super.onStart();
//    }

    protected abstract void setText();
}
