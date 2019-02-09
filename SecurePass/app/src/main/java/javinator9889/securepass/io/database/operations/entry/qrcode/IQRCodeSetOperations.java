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
 * Created by Javinator9889 on 02/02/2019 - SecurePass.
 */
package javinator9889.securepass.io.database.operations.entry.qrcode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * All SET operations for the QRCode class.
 */
public interface IQRCodeSetOperations {
    /**
     * Registers a new QRCode on the database.
     * @param entryId parent entry ID.
     * @param name the name for the QRCode.
     * @param description the optional description for the QRCode - can be {@code null}.
     * @param qrCodeData the data of the QR.
     * @return {@code long} with the created row ID.
     */
    long registerNewQRCode(long entryId,
                           @NonNull String name,
                           @Nullable String description,
                           @NonNull String qrCodeData);

    /**
     * Updates the QRCode name by using the given ID.
     * @param id the QRCode ID.
     * @param name the new name.
     */
    void updateName(long id, @NonNull String name);

    /**
     * Updates the QRCode description by using the given ID.
     * @param id the QRCode ID.
     * @param description the new description - can be {@code null}.
     */
    void updateDescription(long id, @Nullable String description);

    /**
     * Updates the QRCode data by using the given ID.
     * @param id the QRCode ID.
     * @param qrCodeData the new QRCode data.
     */
    void updateQrCodeData(long id, @NonNull String qrCodeData);

    /**
     * Removes the QRCode by using the given ID.
     * @param id the QRCode row to delete.
     */
    void removeQRCode(long id);

    /**
     * Applies pending changes to the database - only necessary when doing UPDATE
     * operations.
     */
    void apply();
}
