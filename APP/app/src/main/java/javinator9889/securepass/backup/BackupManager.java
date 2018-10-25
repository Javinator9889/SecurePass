package javinator9889.securepass.backup;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;
import javinator9889.securepass.backup.drive.DriveDownloader;
import javinator9889.securepass.backup.drive.DriveUploader;
import javinator9889.securepass.backup.drive.IDriveDownloader;
import javinator9889.securepass.backup.drive.IDriveUploader;

/**
 * Class for managing backups
 * Created by Javinator9889 on 06/04/2018.
 */
public class BackupManager implements IBackup {
    private IDriveUploader mUploader;
    private IDriveDownloader mDownloader;

    /**
     * Public constructor for managing backups
     *
     * @param backupContext  <code>Context</code> when instantiating this class
     * @param sourceActivity <code>Activity</code> when instantiating this class
     * @see Activity
     * @see Context
     * @see DriveUploader
     * @see DriveDownloader
     */
    public BackupManager(@NonNull Context backupContext, @NonNull Activity sourceActivity) {
        this.mUploader = new DriveUploader(backupContext, sourceActivity);
        this.mDownloader = new DriveDownloader(backupContext, sourceActivity);
    }

    /**
     * Does the backup by using
     * {@link javinator9889.securepass.backup.drive.DriveUploader DriveUploader} class
     *
     * @see javinator9889.securepass.backup.drive.IDriveUploader
     */
    @Override
    public void doBackup() {
        this.mUploader.uploadDatabase();
    }

    /**
     * Restores the backup by using
     * {@link javinator9889.securepass.backup.drive.DriveDownloader DriveDownloader} class
     *
     * @see javinator9889.securepass.backup.drive.IDriveDownloader
     */
    @Override
    public void restoreBackup() {

    }
}
