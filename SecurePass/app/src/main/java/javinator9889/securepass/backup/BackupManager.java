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
