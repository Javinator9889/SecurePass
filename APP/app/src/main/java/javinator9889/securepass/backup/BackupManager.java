package javinator9889.securepass.backup;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveResourceClient;

import javinator9889.securepass.backup.drive.DriveDownloader;
import javinator9889.securepass.backup.drive.DriveUploader;
import javinator9889.securepass.backup.drive.IDriveDownloader;
import javinator9889.securepass.backup.drive.IDriveUploader;
import javinator9889.securepass.backup.drive.base.GoogleDriveBase;
import javinator9889.securepass.backup.drive.base.IDriveBase;

/**
 * Created by Javinator9889 on 06/04/2018.
 */
public class BackupManager implements IBackup{
    private IDriveUploader uploader;
    private IDriveDownloader downloader;

    public BackupManager(@NonNull Context backupContext, @NonNull Activity sourceActivity) {
        this.uploader = new DriveUploader(backupContext, sourceActivity);
        this.downloader = new DriveDownloader(backupContext, sourceActivity);
    }

    @Override
    public void doBackup() {
        this.uploader.uploadDatabase();
    }

    @Override
    public void restoreBackup() {

    }
}
