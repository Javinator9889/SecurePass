/*
 * Copyright © 2018 - present | SecurePass by Javinator9889
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
 * Created by Javinator9889 on 16/08/2018 - SecurePass.
 */
package javinator9889.securepass.data.entry.fields;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * Class that contains the parameters available for LongText.
 */
public class LongText extends Text implements Serializable {
    /**
     * Public available constructor that uses
     * {@link Text#Text(long, long, String, String, int) super}
     * constructor
     *
     * @param id               long text ID
     * @param parentEntryId    long parent entry ID
     * @param text             long text text
     * @param fieldDescription description
     */
    public LongText(long id,
                    long parentEntryId,
                    @NonNull String text,
                    @NonNull String fieldDescription,
                    int order) {
        super(id, parentEntryId, text, fieldDescription, order);
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public String toString() {
        return "Text: " + getText() +
                "\nID: " + getTextID() +
                "\nField description: " + getFieldDescription() +
                "\nEntry ID: " + getEntryId() +
                "\nSort order: " + getOrder();
    }
}
