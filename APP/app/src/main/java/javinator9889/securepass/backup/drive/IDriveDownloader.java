package javinator9889.securepass.backup.drive;

import android.support.annotation.NonNull;

import com.google.android.gms.drive.DriveFile;

/**
 * Created by Javinator9889 on 03/09/2018.
 */
public interface IDriveDownloader {
    void retrieveContents(@NonNull DriveFile contents);
}
