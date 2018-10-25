package javinator9889.securepass.backup;

/**
 * Interface for accessing {@link BackupManager BackupManager class}
 * Created by Javinator9889 on 20/09/2018.
 */
public interface IBackup {
    /**
     * Does the backup by using
     * {@link javinator9889.securepass.backup.drive.DriveUploader DriveUploader} class
     *
     * @see javinator9889.securepass.backup.drive.IDriveUploader
     */
    void doBackup();

    /**
     * Restores the backup by using
     * {@link javinator9889.securepass.backup.drive.DriveDownloader DriveDownloader} class
     *
     * @see javinator9889.securepass.backup.drive.IDriveDownloader
     */
    void restoreBackup();
}
