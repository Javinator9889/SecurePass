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
package javinator9889.securepass.io.database.operations.entry.image;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;

import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.entry.fields.Image;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.database.ImageFields;

/**
 * All the operations for the Image class.
 */
public class ImageOperations extends CommonOperations implements IImageSetOperations,
        IImageGetOperations {
    private static final String TAG = "Image Operations";
    private static final String TABLE_NAME = Constants.SQL.IMAGE.NAME;
    private static final String ID = ImageFields.ID;
    private static final String SOURCE = ImageFields.SOURCE;
    private static final String DESCRIPTION = ImageFields.DESCRIPTION;
    private static final String ORDER = ImageFields.ORDER;
    private static final String ENTRY = ImageFields.ENTRY;
    private static final String WHERE_ID = ID + "=?";

    /**
     * Available constructor, matching {@link CommonOperations#CommonOperations(DatabaseManager)
     * super} one
     *
     * @param databaseInstance instance of the {@link DatabaseManager} object
     *
     * @see DatabaseManager
     */
    public ImageOperations(@NonNull DatabaseManager databaseInstance) {
        super(databaseInstance);
    }

    /**
     * Gets the tag for {@link Log} output - should be overridden
     *
     * @return <code>String</code> with the tag name
     */
    @Override
    public String getTag() {
        return TAG;
    }

    /**
     * Gets the WHERE ID clause for using {@link #scheduleUpdateExecutor(long, ContentValues)} -
     * should be overridden
     *
     * @return {@code String} with the WHERE clause - null if not defined
     */
    @Nullable
    @Override
    public String getWhereId() {
        return WHERE_ID;
    }

    /**
     * Gets the TABLE NAME for using {@link #scheduleUpdateExecutor(long, ContentValues)} - should
     * be overridden
     *
     * @return {@code String} with the TABLE NAME - null if not defined
     */
    @Nullable
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    /**
     * Obtains the image source by its ID
     *
     * @param imageId ID of the image to get the source
     *
     * @return {@code String} with the source
     */
    @Override
    public String getImageSource(long imageId) {
        String source = null;
        try (Cursor imagesCursor = get(TABLE_NAME, whereArgs(SOURCE), WHERE_ID,
                whereArgs(imageId), null, null, ID + " ASC")) {
            Map<String, Integer> imagesColumns = constructMapFromCursor(imagesCursor);
            if (imagesCursor.moveToNext())
                source = imagesCursor.getString(imagesColumns.get(SOURCE));
        }
        return source;
    }

    /**
     * Obtains the image description by its ID
     *
     * @param imageId ID of the image to get the description
     *
     * @return {@code String} with the description
     */
    @Override
    public String getImageDescription(long imageId) {
        String description = null;
        try (Cursor imagesCursor = get(TABLE_NAME, whereArgs(DESCRIPTION), WHERE_ID,
                whereArgs(imageId), null, null, ID + " ASC")) {
            Map<String, Integer> imagesColumns = constructMapFromCursor(imagesCursor);
            if (imagesCursor.moveToNext())
                description = imagesCursor.getString(imagesColumns.get(DESCRIPTION));
        }
        return description;
    }

    /**
     * Obtains the image order by its ID
     *
     * @param imageId ID of the image to get the order
     *
     * @return {@code int} with the ordinal order
     */
    @Override
    public int getImageOrder(long imageId) {
        int order = -1;
        try (Cursor imagesCursor = get(TABLE_NAME, whereArgs(ORDER), WHERE_ID,
                whereArgs(imageId), null, null, ID + " ASC")) {
            Map<String, Integer> imagesColumns = constructMapFromCursor(imagesCursor);
            if (imagesCursor.moveToNext())
                order = imagesCursor.getInt(imagesColumns.get(ORDER));
        }
        return order;
    }

    /**
     * Obtains the image parent entry ID
     *
     * @param imageId ID of the image to get the parent entry ID
     *
     * @return {@code long} with the entry ID
     */
    @Override
    public long getImageEntryId(long imageId) {
        long entryId = -1;
        try (Cursor imagesCursor = get(TABLE_NAME, whereArgs(ENTRY), WHERE_ID,
                whereArgs(imageId), null, null, ID + " ASC")) {
            Map<String, Integer> imagesColumns = constructMapFromCursor(imagesCursor);
            if (imagesCursor.moveToNext())
                entryId = imagesCursor.getLong(imagesColumns.get(ENTRY));
        }
        return entryId;
    }

    /**
     * Obtains all images' data and saves it inside a {@link GeneralObjectContainer} of {@link
     * Image}
     *
     * @return {@code GeneralObjectContainer} of entries
     *
     * @see ObjectContainer
     * @see Image
     */
    @Override
    public GeneralObjectContainer<Image> getAllImages() {
        GeneralObjectContainer<Image> images = new ObjectContainer<>();
        try (Cursor imagesCursor = getAll(TABLE_NAME, ID + " ASC")) {
            Map<String, Integer> imagesColumns = constructMapFromCursor(imagesCursor);
            while (imagesCursor.moveToNext()) {
                long id = imagesCursor.getLong(imagesColumns.get(ID));
                long entryId = imagesCursor.getLong(imagesColumns.get(ENTRY));
                String source = imagesCursor.getString(imagesColumns.get(SOURCE));
                String description = imagesCursor.getString(imagesColumns.get(DESCRIPTION));
                Image currentImage = new Image(id, entryId, source, description);
                images.storeObject(currentImage);
            }
        }
        return images;
    }

    /**
     * Registers a new simple image
     *
     * @param source      image source
     * @param description image description
     * @param order       ordinal order when showing on UI
     * @param entryId     parent entry ID
     *
     * @return {@code long} with the new image ID
     */
    @Override
    public long registerNewImage(@NonNull String source,
                                 @NonNull String description,
                                 int order,
                                 long entryId) {
        ContentValues params = setParams(source, description, order, entryId);
        return insertReplaceOnConflict(TABLE_NAME, params);
    }

    /**
     * Updates the image source of the provided ID
     *
     * @param imageId   ID of the image to change source
     * @param newSource new source
     */
    @Override
    public void updateImageSource(long imageId, @NonNull String newSource) {
        ContentValues params = new ContentValues(1);
        params.put(SOURCE, newSource);
        scheduleUpdateExecutor(imageId, params);
    }

    /**
     * Updates the image description of the provided ID
     *
     * @param imageId        ID of the image to change description
     * @param newDescription new description
     */
    @Override
    public void updateImageDescription(long imageId, @NonNull String newDescription) {
        ContentValues params = new ContentValues(1);
        params.put(DESCRIPTION, newDescription);
        scheduleUpdateExecutor(imageId, params);
    }

    /**
     * Updates the image order of the provided ID
     *
     * @param imageId  ID of the image to change order
     * @param newOrder new ordinal order
     */
    @Override
    public void updateImageOrder(long imageId, int newOrder) {
        ContentValues params = new ContentValues(1);
        params.put(ORDER, newOrder);
        scheduleUpdateExecutor(imageId, params);
    }

    /**
     * Removes the image by its ID
     *
     * @param imageId ID of the image to delete
     */
    @Override
    public void removeImage(long imageId) {
        delete(TABLE_NAME, ID, imageId);
    }

    /**
     * Generates a map with the provided params
     *
     * @param source      image source
     * @param description image description
     * @param order       ordinal order
     * @param entryId     entry ID
     *
     * @return {@code ContentValues} with the params
     *
     * @see ContentValues
     */
    private ContentValues setParams(@NonNull String source,
                                    @NonNull String description,
                                    int order,
                                    long entryId) {
        ContentValues params = new ContentValues(4);
        params.put(SOURCE, source);
        params.put(DESCRIPTION, description);
        params.put(ORDER, order);
        params.put(ENTRY, entryId);
        return params;
    }
}
