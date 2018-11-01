package javinator9889.securepass.data.entry.fields;

import java.util.Objects;

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
public abstract class Text implements IText {
    private long mId;
    private long mParentEntryId;
    private String mText;
    private String mFieldDescription;

    /**
     * Constructor only available for inheritance classes
     *
     * @param id               the text ID
     * @param text             the current text
     * @param fieldDescription description
     */
    Text(long id, long parentEntryId, @NonNull String text, @NonNull String fieldDescription) {
        mId = id;
        mParentEntryId = parentEntryId;
        mText = text;
        mFieldDescription = fieldDescription;
    }

    /**
     * Sets the new text
     *
     * @param text non null text
     */
    public void setText(@NonNull String text) {
        mText = text;
    }

    /**
     * Obtains the current text
     *
     * @return <code>String</code> with the text
     */
    public String getText() {
        return mText;
    }

    /**
     * Gets the current text ID
     *
     * @return <code>long</code> with the text
     */
    public long getTextID() {
        return mId;
    }

    /**
     * Sets a new text ID for any reason - this method should not be called
     *
     * @param id the new ID
     */
    public void setTextID(long id) {
        mId = id;
    }

    /**
     * Updates the field description by te given one
     *
     * @param fieldDescription new description
     * @see ICommonMethods
     */
    public void setFieldDescription(@NonNull String fieldDescription) {
        mFieldDescription = fieldDescription;
    }

    /**
     * Obtains the current field description
     *
     * @return <code>String</code> with the description
     * @see ICommonMethods
     */
    public String getFieldDescription() {
        return mFieldDescription;
    }

    /**
     * Gets the parent entry ID for this image
     *
     * @return {@code long} with the ID
     */
    @Override
    public long getEntryId() {
        return mParentEntryId;
    }

    /**
     * Sets a new image parent entry ID
     *
     * @param id new parent entry ID
     */
    @Override
    public void setEntryId(long id) {
        mParentEntryId = id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return mId == text.mId &&
                mParentEntryId == text.mParentEntryId &&
                Objects.equals(mText, text.mText) &&
                Objects.equals(mFieldDescription, text.mFieldDescription);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {

        return Objects.hash(mId, mParentEntryId, mText, mFieldDescription);
    }
}