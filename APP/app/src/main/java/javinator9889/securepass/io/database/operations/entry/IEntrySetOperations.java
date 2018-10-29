package javinator9889.securepass.io.database.operations.entry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.entry.fields.IImage;
import javinator9889.securepass.data.entry.fields.IPassword;
import javinator9889.securepass.data.entry.fields.IText;

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
 * Created by Javinator9889 on 28/10/2018 - APP.
 */
public interface IEntrySetOperations {
    /**
     * Registers a new simple entry
     *
     * @param parentCategoryId category ID
     * @param configId         configuration ID
     * @param entryName        entry name
     * @param icon             entry icon
     * @return <code>long</code> with the new entry ID
     * @see javinator9889.securepass.data.entry.Category
     * @see javinator9889.securepass.data.configuration.Configuration
     */
    long registerNewEntry(long parentCategoryId, long configId, @NonNull String entryName,
                          @NonNull String icon);

    /**
     * Registers a new entry with passwords - uses
     * {@link #registerNewEntry(long, long, String, String)} and
     * {@link #updatePasswords(long, IPassword[])}
     *
     * @param parentCategoryId category ID
     * @param configId         configuration ID
     * @param entryName        entry name
     * @param icon             entry icon
     * @param passwords        array of passwords
     * @return <code>long</code> with the new entry ID
     * @see javinator9889.securepass.data.entry.Category
     * @see javinator9889.securepass.data.configuration.Configuration
     * @see IPassword
     */
    long registerNewEntry(long parentCategoryId, long configId, @NonNull String entryName,
                          @NonNull String icon, @NonNull IPassword[] passwords);

    /**
     * Registers a new entry with long or small texts - uses
     * {@link #registerNewEntry(long, long, String, String)} and
     * {@link #updateLongTexts(long, IText[])} or {@link #updateSmallTexts(long, IText[])}
     *
     * @param parentCategoryId category ID
     * @param configId         configuration ID
     * @param entryName        entry name
     * @param icon             entry icon
     * @param texts            array of texts
     * @return <code>long</code> with the new entry ID
     * @see javinator9889.securepass.data.entry.Category
     * @see javinator9889.securepass.data.configuration.Configuration
     * @see IText
     * @see javinator9889.securepass.data.entry.fields.SmallText
     * @see javinator9889.securepass.data.entry.fields.LongText
     */
    long registerNewEntry(long parentCategoryId, long configId, @NonNull String entryName,
                          @NonNull String icon, @NonNull IText[] texts);

    /**
     * Registers a new entry with iamges - uses
     * {@link #registerNewEntry(long, long, String, String)} and
     * {@link #updateImages(long, IImage[])}
     *
     * @param parentCategoryId category ID
     * @param configId         configuration ID
     * @param entryName        entry name
     * @param icon             entry icon
     * @param images           array of images
     * @return <code>long</code> with the new entry ID
     * @see javinator9889.securepass.data.entry.Category
     * @see javinator9889.securepass.data.configuration.Configuration
     * @see IImage
     */
    long registerNewEntry(long parentCategoryId, long configId, @NonNull String entryName,
                          @NonNull String icon, @NonNull IImage[] images);

    /**
     * Updates entry name
     *
     * @param entryId entry ID
     * @param name    new name
     */
    void updateName(long entryId, @NonNull String name);

    /**
     * Updates entry icon
     *
     * @param entryId entry ID
     * @param icon    new icon
     */
    void updateIcon(long entryId, @NonNull String icon);

    /**
     * Updates entry category
     *
     * @param entryId    entry ID
     * @param categoryId new category ID
     */
    void updateCategory(long entryId, long categoryId);

    /**
     * Updates entry configuration
     *
     * @param entryId         entry ID
     * @param configurationId new configuration ID
     */
    void updateConfiguration(long entryId, long configurationId);

    /**
     * Updates entry passwords - if null, removes passwords
     *
     * @param entryId   entry ID
     * @param passwords new passwords
     * @see #removePasswords(long)
     */
    void updatePasswords(long entryId, @Nullable IPassword[] passwords);

    /**
     * Updates entry small texts - if null, removes texts
     *
     * @param entryId    entry ID
     * @param smallTexts new small texts
     * @see #removeSmallTexts(long)
     */
    void updateSmallTexts(long entryId, @Nullable IText[] smallTexts);

    /**
     * Updates entry long texts - if null, removes texts
     *
     * @param entryId   entry ID
     * @param longTexts new long texts
     */
    void updateLongTexts(long entryId, @Nullable IText[] longTexts);

    /**
     * Updates entry images - if null, removes images
     *
     * @param entryId entry ID
     * @param images  new images
     * @see #removeImages(long)
     */
    void updateImages(long entryId, @Nullable IImage[] images);

    /**
     * Removes passwords for the given ID
     *
     * @param entryId entry ID
     */
    void removePasswords(long entryId);

    /**
     * Removes small texts for the given ID
     *
     * @param entryId entry ID
     */
    void removeSmallTexts(long entryId);

    /**
     * Removes long texts for the given ID
     *
     * @param entryId entry ID
     */
    void removeLongTexts(long entryId);

    /**
     * Removes images for the given ID
     *
     * @param entryId entry ID
     */
    void removeImages(long entryId);

    /**
     * Removes the hole entry by the given ID
     *
     * @param entryId entry ID
     */
    void removeEntry(long entryId);
}
