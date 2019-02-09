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
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.query.Filters;
import com.google.android.gms.drive.query.Query;
import com.google.android.gms.drive.query.SearchableField;
import com.google.android.gms.drive.query.SortOrder;
import com.google.android.gms.drive.query.SortableField;
import com.google.android.gms.drive.widget.DataBufferAdapter;
import com.google.android.gms.tasks.Task;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import javinator9889.securepass.R;
import javinator9889.securepass.backup.drive.ResultsAdapter;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.Constants.DRIVE;

/**
 * Created by Javinator9889 on 24/04/2018.
 * Base class for managing Google Drive connections
 */
public class GoogleDriveBase implements IDriveBase {
    private static final String TAG = "drive-base";
    private Context mDriveContext;
    private Activity mMainActivity;
    private DriveResourceClient mDriveResourceClient;
    private DataBufferAdapter<Metadata> mResultsAdapter;
    private SignIn mSignInClient;

    /**
     * Public constructor for base class
     *
     * @param driveContext <code>Context</code> when initializing this class
     * @param mainActivity <code>Activity</code> when initializing this class
     * @see Context
     * @see Activity
     */
    public GoogleDriveBase(@NonNull Context driveContext, @NonNull Activity mainActivity) {
        this.mDriveContext = driveContext;
        this.mMainActivity = mainActivity;
        mSignInClient = SignIn.getInstance(mainActivity, driveContext);
        mResultsAdapter = new ResultsAdapter(driveContext);
    }

    /**
     * Obtains <code>Context</code>
     *
     * @return <code>Context</code> object
     * @see Context
     */
    public Context getDriveContext() {
        return mDriveContext;
    }

    /**
     * Obtains <code>Activity</code>
     *
     * @return <code>Activity</code> object
     * @see Activity
     */
    public Activity getMainActivity() {
        return mMainActivity;
    }

    /**
     * Obtains the <code>ResultsAdapter</code>
     *
     * @return <code>DataBufferAdapter of Metadata</code> object
     * @see DataBufferAdapter
     * @see Metadata
     */
    public DataBufferAdapter<Metadata> getResultsAdapter() {
        return mResultsAdapter;
    }

    /**
     * Obtains the DriveResourceClient
     *
     * @return <code>DriveResourceClient</code> object
     * @see DriveResourceClient
     */
    public DriveResourceClient getDriveResourceClient() {
        return mDriveResourceClient;
    }

    /**
     * Obtains the SignInClient
     *
     * @return <code>SignIn</code> object
     * @see SignIn
     */
    public SignIn getSignInClient() {
        return mSignInClient;
    }

    /**
     * Signs-in into Google Drive by using the SignIn class
     *
     * @see SignIn
     */
    @Override
    public void signIn() {
        mSignInClient.signIn();
    }

    /**
     * Determines whether is able to sign-in
     *
     * @return <code>boolean</code>: 'true' if is user is signed-in, else 'false'
     */
    protected boolean isAbleToSignIn() {
        if (!mSignInClient.isSignedIn()) {
            GoogleSignInAccount latestSignedInAccount = GoogleSignIn
                    .getLastSignedInAccount(mDriveContext);
            if (latestSignedInAccount != null) {
                mDriveResourceClient = Drive
                        .getDriveResourceClient(mDriveContext, latestSignedInAccount);
                return true;
            } else {
                showMessage(R.string.sign_in_first);
                return false;
            }
        } else
            return true;
    }

    /**
     * Sets the <code>DriveResourceClient</code> for classes that inherit from this
     *
     * @param resourceClient <code>DriveResourceClient</code> when the user has signed-in
     * @see DriveResourceClient
     */
    @Override
    public void setDriveResourceClient(DriveResourceClient resourceClient) {
        this.mDriveResourceClient = resourceClient;
    }

    /**
     * Sets whether the user is or not logged in
     *
     * @param isLoggedIn <code>boolean</code>, 'true' if signed-in, else 'false'
     */
    @Override
    public void setLoggedIn(boolean isLoggedIn) {
        mSignInClient.setSignedIn(isLoggedIn);
    }

    /**
     * Method for not repeating code when showing a Toast
     *
     * @param message <code>String</code> which contains the message to be shown
     * @see #showMessage(int)
     * @deprecated use {@link #showMessage(int)} instead
     */
    @Override
    @Deprecated
    public void showMessage(@NonNull String message) {
        Toast.makeText(mDriveContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Method for not repeating code when showing a Toast
     *
     * @param message <code>@StringRes int</code> which contains the resource id (R.string
     *                .string_name)
     * @see StringRes
     * @see R.string
     */
    @Override
    public void showMessage(@StringRes int message) {
        Toast.makeText(mDriveContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * Query the files that applies to some filters, saving them to a DataBufferAdapter object
     *
     * @param resultsAdapter <code>DataBufferAdapter of Metadata</code> which will store the results
     * @see Constants.DRIVE
     * @see Constants.SQL
     * @see DataBufferAdapter
     * @see Metadata
     */
    protected void queryFiles(DataBufferAdapter<Metadata> resultsAdapter) {
        SortOrder sortOrder = new SortOrder.Builder().addSortAscending(SortableField.CREATED_DATE)
                .build();
        Query query = new Query.Builder()
                .addFilter(Filters.and(Filters.eq(SearchableField.TITLE, Constants.SQL.DB_FILENAME),
                        Filters.eq(SearchableField.MIME_TYPE, Constants.DRIVE.MIME_TYPE)))
                .setSortOrder(sortOrder)
                .build();
        retrieveContents(query, resultsAdapter);
    }

    /**
     * Query only the files that are an IV Vector (for encryption), saving them to a
     * DataBufferAdapter object
     *
     * @param resultsAdapter <code>DataBufferAdapter of Metadata</code> which will store the results
     * @see Constants.SQL
     * @see Constants.DRIVE
     * @see javax.crypto.Cipher#init(int, Key, AlgorithmParameterSpec)
     * @see java.security.SecureRandom#nextBytes(byte[])
     */
    protected void getIVVector(DataBufferAdapter<Metadata> resultsAdapter) {
        SortOrder sortOrder = new SortOrder.Builder().addSortAscending(SortableField.CREATED_DATE)
                .build();
        Query query = new Query.Builder()
                .addFilter(Filters.and(Filters.eq(SearchableField.TITLE, DRIVE.IV_FILE),
                        Filters.eq(SearchableField.MIME_TYPE, DRIVE.IV_MIME_TYPE)))
                .setSortOrder(sortOrder)
                .build();
        retrieveContents(query, resultsAdapter);
    }

    /**
     * By providing a query, retrieve the correspondent Google Drive files into an adapter
     *
     * @param query          determines which files will be downloaded
     * @param resultsAdapter stores files inside a <code>BufferAdapter</code>
     * @see Query
     * @see DataBufferAdapter
     * @see Metadata
     */
    private void retrieveContents(@NonNull Query query, DataBufferAdapter<Metadata> resultsAdapter) {
        Task<DriveFolder> appFolderTask = getDriveResourceClient().getAppFolder();
        appFolderTask.addOnSuccessListener(getMainActivity(), driveFolder -> {
            Task<MetadataBuffer> queryTask = getDriveResourceClient()
                    .queryChildren(driveFolder, query);
            queryTask.addOnSuccessListener(getMainActivity(), resultsAdapter::append);
        });
    }
}
