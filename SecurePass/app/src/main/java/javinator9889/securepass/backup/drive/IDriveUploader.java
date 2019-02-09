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
