package javinator9889.securepass.backup.drive;

import android.support.annotation.NonNull;

import javinator9889.securepass.data.container.ClassContainer;

/**
 * Created by Javinator9889 on 24/04/2018.
 */
public interface IDriveUploadOperations {
    void createFileInAppFolder(@NonNull final ClassContainer dataToBackup);
    void createIvSaveFile();
}
