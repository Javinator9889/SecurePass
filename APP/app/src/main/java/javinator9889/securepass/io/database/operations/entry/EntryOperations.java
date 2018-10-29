package javinator9889.securepass.io.database.operations.entry;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.configuration.Configuration;
import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.data.entry.fields.IImage;
import javinator9889.securepass.data.entry.fields.IPassword;
import javinator9889.securepass.data.entry.fields.IText;
import javinator9889.securepass.data.entry.fields.LongText;
import javinator9889.securepass.data.entry.fields.SmallText;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.util.values.Constants.SQL.ENTRY;

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
public class EntryOperations extends CommonOperations implements
        IEntrySetOperations, IEntryGetOperations {
    private static final String TAG = "Entry Operations";
    private static final String TABLE_NAME = ENTRY.NAME;
    private static final String NAME = ENTRY.E_NAME;
    private static final String ID = ENTRY.E_ID;
    private static final String ICON = ENTRY.E_ICON;
    private static final String CATEGORY = ENTRY.E_PARENT_CATEGORY;
    private static final String CONFIGURATION = ENTRY.E_PARENT_CONFIGURATION;

    /**
     * Available constructor, matching
     * {@link CommonOperations#CommonOperations(DatabaseManager) super} one
     *
     * @param databaseManager instance of the {@link DatabaseManager} object
     * @see DatabaseManager
     */
    public EntryOperations(@NonNull DatabaseManager databaseManager) {
        super(databaseManager);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTag() {
        return TAG;
    }

    /**
     * Registers a new simple entry
     *
     * @param parentCategoryId category ID
     * @param configId         configuration ID
     * @param entryName        entry name
     * @param icon             entry icon
     * @return <code>long</code> with the new entry ID
     * @see Category
     * @see Configuration
     */
    @Override
    public long registerNewEntry(long parentCategoryId,
                                 long configId,
                                 @NonNull String entryName,
                                 @NonNull String icon) {
        ContentValues params = setParams(entryName, icon, parentCategoryId, configId);
        return insertReplaceOnConflict(TABLE_NAME, params);
    }

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
     * @see Category
     * @see Configuration
     * @see IPassword
     */
    @Override
    public long registerNewEntry(long parentCategoryId,
                                 long configId,
                                 @NonNull String entryName,
                                 @NonNull String icon,
                                 @NonNull IPassword[] passwords) {
        long id = registerNewEntry(parentCategoryId, configId, entryName, icon);
        updatePasswords(id, passwords);
        return id;
    }

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
     * @see Category
     * @see Configuration
     * @see IText
     * @see SmallText
     * @see LongText
     */
    @Override
    public long registerNewEntry(long parentCategoryId,
                                 long configId,
                                 @NonNull String entryName,
                                 @NonNull String icon,
                                 @NonNull IText[] texts) {
        long id = registerNewEntry(parentCategoryId, configId, entryName, icon);
        updateTexts(id, texts);
        return id;
    }

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
     * @see Category
     * @see Configuration
     * @see IImage
     */
    @Override
    public long registerNewEntry(long parentCategoryId,
                                 long configId,
                                 @NonNull String entryName,
                                 @NonNull String icon,
                                 @NonNull IImage[] images) {
        long id = registerNewEntry(parentCategoryId, configId, entryName, icon);
        updateImages(id, images);
        return id;
    }

    /**
     * Determines whether the text is a {@link SmallText} or a {@link LongText} and updates the
     * entry
     *
     * @param entryId entry ID
     * @param texts   {@code IText} array
     */
    private void updateTexts(long entryId,
                             @NonNull IText[] texts) {

    }

    /**
     * Updates entry name
     *
     * @param entryId entry ID
     * @param name    new name
     */
    @Override
    public void updateName(long entryId,
                           @NonNull String name) {

    }

    /**
     * Updates entry icon
     *
     * @param entryId entry ID
     * @param icon    new icon
     */
    @Override
    public void updateIcon(long entryId,
                           @NonNull String icon) {

    }

    /**
     * Updates entry category
     *
     * @param entryId    entry ID
     * @param categoryId new category ID
     */
    @Override
    public void updateCategory(long entryId,
                               long categoryId) {

    }

    /**
     * Updates entry configuration
     *
     * @param entryId         entry ID
     * @param configurationId new configuration ID
     */
    @Override
    public void updateConfiguration(long entryId,
                                    long configurationId) {

    }

    /**
     * Updates entry passwords - if null, removes passwords
     *
     * @param entryId   entry ID
     * @param passwords new passwords
     * @see #removePasswords(long)
     */
    @Override
    public void updatePasswords(long entryId,
                                @Nullable IPassword[] passwords) {

    }

    /**
     * Updates entry small texts - if null, removes texts
     *
     * @param entryId    entry ID
     * @param smallTexts new small texts
     * @see #removeSmallTexts(long)
     */
    @Override
    public void updateSmallTexts(long entryId,
                                 @Nullable IText[] smallTexts) {

    }

    /**
     * Updates entry long texts - if null, removes texts
     *
     * @param entryId   entry ID
     * @param longTexts new long texts
     */
    @Override
    public void updateLongTexts(long entryId,
                                @Nullable IText[] longTexts) {

    }

    /**
     * Updates entry images - if null, removes images
     *
     * @param entryId entry ID
     * @param images  new images
     * @see #removeImages(long)
     */
    @Override
    public void updateImages(long entryId,
                             @Nullable IImage[] images) {

    }

    /**
     * Removes passwords for the given ID
     *
     * @param entryId entry ID
     */
    @Override
    public void removePasswords(long entryId) {

    }

    /**
     * Removes small texts for the given ID
     *
     * @param entryId entry ID
     */
    @Override
    public void removeSmallTexts(long entryId) {

    }

    /**
     * Removes long texts for the given ID
     *
     * @param entryId entry ID
     */
    @Override
    public void removeLongTexts(long entryId) {

    }

    /**
     * Removes images for the given ID
     *
     * @param entryId entry ID
     */
    @Override
    public void removeImages(long entryId) {

    }

    /**
     * Removes the hole entry by the given ID
     *
     * @param entryId entry ID
     */
    @Override
    public void removeEntry(long entryId) {
        delete(TABLE_NAME, ID, entryId);
    }

    private ContentValues setParams(@NonNull String entryName,
                                    @NonNull String entryIcon,
                                    long categoryId,
                                    long configurationId) {
        ContentValues params = new ContentValues(4);
        params.put(NAME, entryName);
        params.put(ICON, entryIcon);
        params.put(CATEGORY, categoryId);
        params.put(CONFIGURATION, configurationId);
        return params;
    }
}
