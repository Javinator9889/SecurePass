package javinator9889.securepass.data.entry.fields;

import androidx.annotation.NonNull;

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
 * Created by Javinator9889 on 26/10/2018 - APP.
 */
public interface IText extends ICommonMethods {
    /**
     * Sets the new text
     *
     * @param text non null text
     */
    void setText(@NonNull String text);

    /**
     * Obtains the current text
     *
     * @return <code>String</code> with the text
     */
    String getText();

    /**
     * Gets the current text ID
     *
     * @return <code>long</code> with the text
     */
    long getTextID();

    /**
     * Sets a new text ID for any reason - this method should not be called
     *
     * @param id the new ID
     */
    void setTextID(long id);
}
