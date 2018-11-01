package javinator9889.securepass.io.database.operations.category;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.threading.ThreadExceptionListener;
import javinator9889.securepass.util.threading.ThreadingExecutor;
import javinator9889.securepass.util.threading.thread.NotifyingThread;
import javinator9889.securepass.util.values.Constants;
import javinator9889.securepass.util.values.Constants.SQL.CATEGORY;
import javinator9889.securepass.util.values.database.CategoryFields;

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
 * Created by Javinator9889 on 31/10/2018 - APP.
 */
public class CategoryOperations extends CommonOperations implements ICategorySetOperations,
        ICategoryGetOperations {
    private static final String TAG = "Category Operations";
    private static final String TABLE_NAME = CATEGORY.NAME;
    private static final CategoryFields ID = CategoryFields.ID;
    private static final CategoryFields NAME = CategoryFields.NAME;
    private static final String CATEGORY_WHERE_ID = Constants.SQL.DB_UPDATE_CATEGORY_WHERE_CLAUSE;
    private ThreadingExecutor mExecutor;

    public CategoryOperations(@NonNull DatabaseManager databaseManager,
                              @Nullable ThreadExceptionListener onExceptionListener) {
        super(databaseManager);
        mExecutor = onExceptionListener == null ?
                ThreadingExecutor.Builder().build() :
                ThreadingExecutor.Builder().addOnThreadExceptionListener(onExceptionListener)
                        .build();
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
     * Obtains the category name by using the given ID
     *
     * @param categoryId the category ID where obtaining the name
     * @return {@code String} with the name
     */
    @Override
    public String getCategoryName(long categoryId) {
        String name = null;
        try (Cursor categoryCursor = get(TABLE_NAME, whereArgs(NAME.getFieldName()),
                CATEGORY_WHERE_ID, whereArgs(categoryId), null, null, ID.getFieldName() + " ASC")) {
            if (categoryCursor.moveToNext())
                name = categoryCursor.getString(NAME.getFieldIndex());
        }
        return name;
    }

    /**
     * Obtains all categories' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link Category}
     *
     * @return {@code GeneralObjectContainer} of categories
     * @see ObjectContainer
     * @see Category
     */
    @Override
    public GeneralObjectContainer<Category> getAllCategories() {
        GeneralObjectContainer<Category> categories = new ObjectContainer<>();
        try (Cursor categoriesCursor = getAll(TABLE_NAME, ID.getFieldName() + " ASC")) {
            while (categoriesCursor.moveToNext()) {
                long id = categoriesCursor.getLong(ID.getFieldIndex());
                String name = categoriesCursor.getString(NAME.getFieldIndex());
                Category currentCategory = new Category(id, name);
                categories.storeObject(currentCategory);
            }
        }
        return categories;
    }

    /**
     * Registers a new simple category
     *
     * @param categoryName the name of the new category
     * @return {@code long} with the new category ID
     */
    @Override
    public long registerNewCategory(@NonNull String categoryName) {
        ContentValues params = setParams(categoryName);
        return insertReplaceOnConflict(TABLE_NAME, params);
    }

    /**
     * Updates the category name by using the given ID
     *
     * @param categoryId   id of the category to update
     * @param categoryName new name
     */
    @Override
    public void updateCategoryName(long categoryId, @NonNull String categoryName) {
        ContentValues params = setParams(categoryName);
        runUpdateExecutor(categoryId, params);
    }

    /**
     * Removes the hole category by the given ID
     *
     * @param categoryId category ID to remove
     */
    @Override
    public void removeCategory(long categoryId) {
        delete(TABLE_NAME, ID.getFieldName(), categoryId);
    }

    /**
     * Generates a map with the provided params
     *
     * @param categoryName category name
     * @return {@code ContentValues} with the params
     */
    private ContentValues setParams(@NonNull String categoryName) {
        ContentValues params = new ContentValues(1);
        params.put(NAME.getFieldName(), categoryName);
        return params;
    }

    /**
     * Runs an update operation by using the given ID and new values
     *
     * @param categoryId ID where changing values
     * @param params     new values
     */
    private void runUpdateExecutor(long categoryId, @NonNull ContentValues params) {
        mExecutor.add(new NotifyingThread() {
            @Override
            public void doRun() {
                update(TABLE_NAME, params, CATEGORY_WHERE_ID, whereArgs(categoryId));
            }
        });
        mExecutor.run();
    }
}
