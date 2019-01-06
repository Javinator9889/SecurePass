package javinator9889.securepass.backup.drive;

import com.google.android.gms.drive.widget.DataBufferAdapter;

import javinator9889.securepass.backup.drive.base.GoogleDriveBase;

/**
 * Interface for accessing {@link DriveUploader}
 * Created by Javinator9889 on 24/08/2018.
 */
public interface IDriveUploader {
    /**
     * General public method which invokes in order the following ones:
     * <ul>
     * <li>
     * {@link DriveUploader#isAbleToSignIn()} ()}
     * </li>
     * <li>
     * {@link DriveUploader#queryFiles(DataBufferAdapter)}
     * </li>
     * <li>
     * {@link DriveUploader#createFileInAppFolder()}
     * </li>
     * <li>
     * {@link DriveUploader#deleteOldFiles(DataBufferAdapter)}
     * </li>
     * </ul>
     *
     * @see DataBufferAdapter
     * @see ResultsAdapter
     * @see GoogleDriveBase
     */
    void uploadDatabase();
}
