package javinator9889.securepass.backup.drive;

import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.widget.DataBufferAdapter;
import com.google.android.gms.tasks.Task;

/**
 * Interface for accessing {@link DriveDownloader}
 * Created by Javinator9889 on 03/09/2018.
 */
public interface IDriveDownloader {
    /**
     * Restores the data and saves it into a <code>ResultsAdapter</code>
     *
     * @see DataBufferAdapter
     * @see DriveFile
     * @see Task
     */
    void restoreData();
}
