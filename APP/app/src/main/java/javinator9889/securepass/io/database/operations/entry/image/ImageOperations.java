package javinator9889.securepass.io.database.operations.entry.image;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;

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
 * Copyright © 2018 - present | APP by Javinator9889
 * <p>
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see https://www.gnu.org/licenses/.
 * <p>
 * Created by Javinator9889 on 02/11/2018 - APP.
 */
public class ImageOperations extends CommonOperations implements IImageSetOperations,
        IImageGetOperations {
    private static final String TAG = "Image Operations";
    private static final String TABLE_NAME = Constants.SQL.IMAGE.NAME;
    private static final ImageFields ID = ImageFields.ID;
    private static final ImageFields SOURCE = ImageFields.SOURCE;
    private static final ImageFields DESCRIPTION = ImageFields.DESCRIPTION;
    private static final ImageFields ORDER = ImageFields.ORDER;
    private static final ImageFields ENTRY = ImageFields.ENTRY;
    private static final String WHERE_ID = ID.getFieldName() + "=?";

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
        try (Cursor imagesCursor = get(TABLE_NAME, whereArgs(SOURCE.getFieldName()), WHERE_ID,
                whereArgs(imageId), null, null, ID.getFieldName() + " ASC")) {
            if (imagesCursor.moveToNext())
                source = imagesCursor.getString(SOURCE.getFieldIndex());
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
        try (Cursor imagesCursor = get(TABLE_NAME, whereArgs(DESCRIPTION.getFieldName()), WHERE_ID,
                whereArgs(imageId), null, null, ID.getFieldName() + " ASC")) {
            if (imagesCursor.moveToNext())
                description = imagesCursor.getString(DESCRIPTION.getFieldIndex());
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
        try (Cursor imagesCursor = get(TABLE_NAME, whereArgs(ORDER.getFieldName()), WHERE_ID,
                whereArgs(imageId), null, null, ID.getFieldName() + " ASC")) {
            if (imagesCursor.moveToNext())
                order = imagesCursor.getInt(ORDER.getFieldIndex());
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
        try (Cursor imagesCursor = get(TABLE_NAME, whereArgs(ENTRY.getFieldName()), WHERE_ID,
                whereArgs(imageId), null, null, ID.getFieldName() + " ASC")) {
            if (imagesCursor.moveToNext())
                entryId = imagesCursor.getLong(ENTRY.getFieldIndex());
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
        try (Cursor imagesCursor = getAll(TABLE_NAME, ID.getFieldName() + " ASC")) {
            while (imagesCursor.moveToNext()) {
                long id = imagesCursor.getLong(ID.getFieldIndex());
                long entryId = imagesCursor.getLong(ENTRY.getFieldIndex());
                String source = imagesCursor.getString(SOURCE.getFieldIndex());
                String description = imagesCursor.getString(DESCRIPTION.getFieldIndex());
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
        params.put(SOURCE.getFieldName(), newSource);
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
        params.put(DESCRIPTION.getFieldName(), newDescription);
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
        params.put(ORDER.getFieldName(), newOrder);
        scheduleUpdateExecutor(imageId, params);
    }

    /**
     * Removes the image by its ID
     *
     * @param imageId ID of the image to delete
     */
    @Override
    public void removeImage(long imageId) {
        delete(TABLE_NAME, ID.getFieldName(), imageId);
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
        params.put(SOURCE.getFieldName(), source);
        params.put(DESCRIPTION.getFieldName(), description);
        params.put(ORDER.getFieldName(), order);
        params.put(ENTRY.getFieldName(), entryId);
        return params;
    }
}
