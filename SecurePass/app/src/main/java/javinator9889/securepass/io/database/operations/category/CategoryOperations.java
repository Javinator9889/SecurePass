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
 * Created by Javinator9889 on 31/10/2018 - SecurePass.
 */
package javinator9889.securepass.io.database.operations.category;

import android.content.ContentValues;
import android.util.Log;

import net.sqlcipher.Cursor;

import java.util.Map;

import androidx.annotation.NonNull;
import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.operations.CommonOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.values.Constants.SQL.CATEGORY;
import javinator9889.securepass.util.values.database.CategoryFields;

/**
 * All the operations for the Category class.
 */
public class CategoryOperations extends CommonOperations implements ICategorySetOperations,
        ICategoryGetOperations {
    private static final String TAG = "Category Operations";
    private static final String TABLE_NAME = CATEGORY.NAME;
    private static final CategoryFields ID = CategoryFields.ID;
    private static final CategoryFields NAME = CategoryFields.NAME;
    private static final String CATEGORY_WHERE_ID = ID.getFieldName() + "=?";

    /**
     * Available constructor, matching {@link CommonOperations#CommonOperations(DatabaseManager)
     * super} one
     *
     * @param databaseManager instance of the {@link DatabaseManager} object
     *
     * @see DatabaseManager
     */
    public CategoryOperations(@NonNull DatabaseManager databaseManager) {
        super(databaseManager);
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
    @NonNull
    @Override
    public String getWhereId() {
        return CATEGORY_WHERE_ID;
    }

    /**
     * Gets the TABLE NAME for using {@link #scheduleUpdateExecutor(long, ContentValues)} - should
     * be overridden
     *
     * @return {@code String} with the TABLE NAME - null if not defined
     */
    @NonNull
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    /**
     * Obtains the category name by using the given ID
     *
     * @param categoryId the category ID where obtaining the name
     *
     * @return {@code String} with the name
     */
    @Override
    public String getCategoryName(long categoryId) {
        String name = null;
        try (Cursor categoryCursor = get(TABLE_NAME, whereArgs(NAME.getFieldName()),
                CATEGORY_WHERE_ID, whereArgs(categoryId), null, null, ID.getFieldName() + " ASC")) {
            Map<String, Integer> categoriesColumns = constructMapFromCursor(categoryCursor);
            if (categoryCursor.moveToNext())
                name = categoryCursor.getString(categoriesColumns.get(NAME.getFieldName()));
        }
        return name;
    }

    /**
     * Obtains all categories' data and saves it inside a {@link GeneralObjectContainer} of {@link
     * Category}
     *
     * @return {@code GeneralObjectContainer} of categories
     *
     * @see ObjectContainer
     * @see Category
     */
    @Override
    public GeneralObjectContainer<Category> getAllCategories() {
        GeneralObjectContainer<Category> categories = new ObjectContainer<>();
        try (Cursor categoriesCursor = getAll(TABLE_NAME, ID.getFieldName() + " ASC")) {
            Map<String, Integer> categoriesColumns = constructMapFromCursor(categoriesCursor);
            while (categoriesCursor.moveToNext()) {
                long id = categoriesCursor.getLong(categoriesColumns.get(ID.getFieldName()));
                String name = categoriesCursor.getString(categoriesColumns.get(NAME.getFieldName()));
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
     *
     * @return {@code long} with the new category ID
     */
    @Override
    public long registerNewCategory(@NonNull String categoryName) {
        ContentValues params = setParams(categoryName);
        return insertFailOnConflict(TABLE_NAME, params);
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
        scheduleUpdateExecutor(categoryId, params);
    }

    /**
     * Removes the hole category by the given ID
     *
     * @param categoryId category ID to remove
     */
    @Override
    public void removeCategory(long categoryId) {
        int i = delete(TABLE_NAME, ID.getFieldName(), categoryId);
        Log.d(TAG, "Number of rows deleted: " + i);
    }

    /**
     * Generates a map with the provided params
     *
     * @param categoryName category name
     *
     * @return {@code ContentValues} with the params
     */
    private ContentValues setParams(@NonNull String categoryName) {
        ContentValues params = new ContentValues(1);
        params.put(NAME.getFieldName(), categoryName);
        return params;
    }
}
