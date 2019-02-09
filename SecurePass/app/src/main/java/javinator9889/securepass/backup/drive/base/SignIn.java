/*
 * Copyright © 2019 - present | SecurePass by Javinator9889
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
 * Created by Javinator9889 on 23/08/2018 - SecurePass.
 */
package javinator9889.securepass.backup.drive.base;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.drive.Drive;

import androidx.annotation.NonNull;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;

/**
 * Singleton class for singing-in into Google Drive
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

    /**
     * Generates the <code>SignIn</code> instance the first time it is executed
     * <p>
     * Stores <code>Activity</code> and <code>Context</code> into
     * <code>ObjectContainer</code> for avoiding memory leaks
     * </p>
     *
     * @param mainActivity <code>Activity</code> when the class was launched
     * @param driveContext <code>Context</code> when the class was launched
     * @see Activity
     * @see Context
     * @see ObjectContainer
     * @see GeneralObjectContainer
     */
    private SignIn(@NonNull Activity mainActivity, @NonNull Context driveContext) {
        INSTANCE = this;
        this.mActivityContainer = new ObjectContainer<>(mainActivity);
        this.mContextContainer = new ObjectContainer<>(driveContext);
        this.mIsSignedIn = false;
        this.mIsSignInClientBuilt = false;
    }

    /**
     * Accessible method for obtaining a <code>SignIn</code> class instance
     * <p>
     * Stores <code>Activity</code> and <code>Context</code> into
     * <code>ObjectContainer</code> for avoiding memory leaks
     * </p>
     *
     * @param mainActivity <code>Activity</code> when the class was launched
     * @param driveContext <code>Context</code> when the class was launched
     * @return SignIn instance if available, else new instance
     * @see Activity
     * @see Context
     * @see ObjectContainer
     * @see GeneralObjectContainer
     */
    public static SignIn getInstance(@NonNull Activity mainActivity,
                                     @NonNull Context driveContext) {
        return INSTANCE != null ? INSTANCE : new SignIn(mainActivity, driveContext);
    }

    /**
     * Signs-in into Google Drive by requesting the user some credentials
     *
     * @see GoogleSignInClient
     * @see #buildGoogleSignInClient()
     */
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

    /**
     * Generates the Google Drive sign in client, necessary for accessing to the Google Drive API
     * at the app folder with the chosen account
     *
     * @see GoogleSignIn
     * @see GoogleSignInOptions
     */
    private void buildGoogleSignInClient() {
        Context driveContext = mContextContainer.getLatestStoredObject();
        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(Drive.SCOPE_FILE, Drive.SCOPE_APPFOLDER)
                        .build();
        this.mGoogleSignInClient = GoogleSignIn.getClient(driveContext, signInOptions);
        mIsSignInClientBuilt = true;
    }

    /**
     * Sets whether the user is or not signed in
     *
     * @param isSignedIn <code>boolean</code> 'true' if already signed-in, else 'false'
     */
    public void setSignedIn(boolean isSignedIn) {
        this.mIsSignedIn = isSignedIn;
    }

    /**
     * Checks if user is already signed-in
     *
     * @return boolean 'true' when signed-in, else 'false'
     */
    public boolean isSignedIn() {
        return mIsSignedIn;
    }

    /**
     * Finish this class when a new instance is required for some reason
     */
    public void finish() {
        mIsSignedIn = false;
        mIsSignInClientBuilt = false;
        mContextContainer.removeAllObjects();
        mActivityContainer.removeAllObjects();
        mGoogleSignInClient = null;
        INSTANCE = null;
    }
}
