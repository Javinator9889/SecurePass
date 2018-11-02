package javinator9889.securepass.io.database.operations.entry.texts.longtext;

import androidx.annotation.NonNull;
import javinator9889.securepass.io.database.operations.entry.texts.ITextSetOperations;

/**
 * Copyright © 2018 - present | APP by Javinator9889
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see https://www.gnu.org/licenses/.
 *
 * Created by Javinator9889 on 02/11/2018 - APP.
 */
public interface ILongTextSetOperations extends ITextSetOperations {
    /**
     * Registers a new simple long text
     *
     * @param text        text to store in the DB
     * @param description long text description
     * @param order       ordinal order
     * @param entryId     parent entry ID
     * @return {@code long} with the new long text ID
     */
    long registerNewLongText(@NonNull String text, @NonNull String description, int order,
                             long entryId);
}
