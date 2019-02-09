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

import androidx.annotation.Nullable;
import javinator9889.securepass.data.entry.QRCode;
import javinator9889.securepass.objects.GeneralObjectContainer;

public interface IQRCodeGetOperations {
    /**
     * Obtains the QRCode name by using the given ID.
     * @param id the QRCode ID.
     * @return {@code String} with the name.
     */
    String getQRCodeName(long id);

    /**
     * Obtains the QRCode description by using the given ID - can be {@code null}.
     * @param id the QRCode ID.
     * @return {@code String} with the description.
     */
    @Nullable
    String getQRCodeDescription(long id);

    /**
     * Obtains the QRCode data by using the given ID.
     * @param id the QRCode ID.
     * @return {@code String} with the data.
     */
    String getQRCodeData(long id);

    /**
     * Obtains the QRCode entry ID by using the QRCode ID.
     * @param id the QRCode ID.
     * @return {@code long} with the entry ID.
     */
    long getQRCodeEntryID(long id);

    /**
     * Obtains all entries' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link QRCode}
     *
     * @return {@code GeneralObjectContainer} of entries
     * @see javinator9889.securepass.objects.ObjectContainer
     * @see QRCode
     */
    GeneralObjectContainer<QRCode> getAllQRCodes();
}
