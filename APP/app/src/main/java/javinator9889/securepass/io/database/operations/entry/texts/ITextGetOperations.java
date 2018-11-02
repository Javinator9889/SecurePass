package javinator9889.securepass.io.database.operations.entry.texts;

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
public interface ITextGetOperations {
    /**
     * Obtains the text's field text
     *
     * @param textId ID of the text to obtain its data
     * @return {@code String} with the text
     */
    String getTextText(long textId);

    /**
     * Obtains the text's description
     *
     * @param textId ID of the text to obtain its data
     * @return {@code String} with the description
     */
    String getTextDescription(long textId);

    /**
     * Obtains the text's order
     *
     * @param textId ID of the text to obtain its data
     * @return {@code int} with the ordinal order
     */
    int getTextOrder(long textId);

    /**
     * Obtains the text's entry ID
     *
     * @param textId ID of the text to obtain its data
     * @return {@code long} with the entry ID
     */
    long getTextEntryId(long textId);
}
