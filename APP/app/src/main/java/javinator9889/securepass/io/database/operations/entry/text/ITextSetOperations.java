package javinator9889.securepass.io.database.operations.entry.text;

import androidx.annotation.NonNull;
import javinator9889.securepass.errors.database.NoJobsEnqueuedError;
import javinator9889.securepass.util.threading.ThreadingExecutor;

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
public interface ITextSetOperations {
    /**
     * Updates the current text by the provided one
     *
     * @param textId ID of the text field to change
     * @param text   new text
     */
    void updateTextText(long textId, @NonNull String text);

    /**
     * Updates the current text description
     *
     * @param textId      ID of the text to change
     * @param description new description
     */
    void updateTextDescription(long textId, @NonNull String description);

    /**
     * Updates the current text order
     *
     * @param textId ID of the text to change
     * @param order  new order
     */
    void updateTextOrder(long textId, int order);

    /**
     * Removes the text by using its ID
     *
     * @param textId ID of the text to remove
     */
    void removeText(long textId);

    /**
     * Registers a new simple small text
     *
     * @param text        text to store in the DB
     * @param description small text description
     * @param order       ordinal order
     * @param entryId     parent entry ID
     * @return {@code long} with the new small text ID
     */
    long registerNewText(@NonNull String text, @NonNull String description, int order,
                         long entryId);

    /**
     * Runs the {@link ThreadingExecutor} - only necessary when doing UPDATE operations
     *
     * @throws NoJobsEnqueuedError when there is no job added to the queue
     */
    void apply();
}
