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
     * {@link #isAbleToSignIn()}
     * </li>
     * <li>
     * {@link #queryFiles(DataBufferAdapter)}
     * </li>
     * <li>
     * {@link #createFileInAppFolder()}
     * </li>
     * <li>
     * {@link #deleteOldFiles(DataBufferAdapter)}
     * </li>
     * </ul>
     *
     * @see DataBufferAdapter
     * @see ResultsAdapter
     * @see GoogleDriveBase
     */
    void uploadDatabase();
}
