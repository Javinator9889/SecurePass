package javinator9889.securepass.backup.drive.base;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;

import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;

/**
 * Created by Javinator9889 on 25/04/2018.
 */
public class SignIn {
    private static final int REQUEST_CODE_SIGN_IN = 0;

    private static SignIn INSTANCE = null;
    private GeneralObjectContainer<Activity> mActivityContainer;
    private GeneralObjectContainer<Context> mContextContainer;
    private boolean mIsSignedIn;
    private boolean mIsSignInClientBuilt;
    private GoogleSignInClient mGoogleSignInClient;

    private SignIn(@NonNull Activity mainActivity, @NonNull Context driveContext) {
        INSTANCE = this;
        this.mActivityContainer = new ObjectContainer<>(mainActivity);
        this.mContextContainer = new ObjectContainer<>(driveContext);
        this.mIsSignedIn = false;
        this.mIsSignInClientBuilt = false;
    }

    public static SignIn getInstance(@NonNull Activity mainActivity,
                                     @NonNull Context driveContext) {
        return INSTANCE != null ? INSTANCE : new SignIn(mainActivity, driveContext);
    }

    public void signIn() {
        Activity mainActivity = mActivityContainer.getLatestStoredObject();
        if (mIsSignInClientBuilt)
            mainActivity.startActivityForResult(mGoogleSignInClient.getSignInIntent(),
                    REQUEST_CODE_SIGN_IN);
        else {
            buildGoogleSignInClient();
            mainActivity.startActivityForResult(mGoogleSignInClient.getSignInIntent(),
                    REQUEST_CODE_SIGN_IN);
        }
    }

    private void buildGoogleSignInClient() {
        Context driveContext = mContextContainer.getLatestStoredObject();
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE, Drive.SCOPE_APPFOLDER)
                        .build();
        this.mGoogleSignInClient = GoogleSignIn.getClient(driveContext, signInOptions);
        mIsSignInClientBuilt = true;
    }

    public void setSignedIn(boolean isSignedIn) {
        this.mIsSignedIn = isSignedIn;
    }

    public boolean isSignedIn() {
        return mIsSignedIn;
    }

    public void finish() {
        mIsSignedIn = false;
        mIsSignInClientBuilt = false;
        mContextContainer.removeAllObjects();
        mActivityContainer.removeAllObjects();
        mGoogleSignInClient = null;
        INSTANCE = null;
    }
}
