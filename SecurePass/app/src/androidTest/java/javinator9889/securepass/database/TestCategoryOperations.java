/*
 * Copyright © 2018 - present | SecurePass by Javinator9889

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see https://www.gnu.org/licenses/.

 * Created by Javinator9889 on 29/12/18 - SecurePass.
 */
package javinator9889.securepass.database;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javinator9889.securepass.io.database.operations.category.CategoryOperations;
import javinator9889.securepass.io.database.operations.category.ICategoryGetOperations;
import javinator9889.securepass.io.database.operations.category.ICategorySetOperations;

import static java.lang.Thread.sleep;

public class TestCategoryOperations extends DatabaseTests {
    private ICategoryGetOperations mGetOperations;
    private ICategorySetOperations mSetOperations;
    private CategoryOperations mOperations;
    private int mCategorySuffix = 1;
    public static final String TAG = "CategoryOperations";

    public TestCategoryOperations() {
//        super();
    }

    private TestCategoryOperations(boolean removePrevious) {
        super(removePrevious);
    }

    @Before
    public void preload() {
//        new TestCategoryOperations();
        new TestCategoryOperations(false);
        CategoryOperations operations = new CategoryOperations(super.generateInstance(PASSWORD));
        mGetOperations = operations;
        mSetOperations = operations;
        mOperations = operations;
        registerNewCategory();
    }

    public void registerNewCategory() {
        String categoryName = "newCategory" + mCategorySuffix;
        long id = mSetOperations.registerNewCategory(categoryName);
        Log.d(TAG, "ID: " + id);
        getAll();
        Log.d(TAG, "Recently created category name: " + mGetOperations.getCategoryName(id));
        mCategorySuffix = ((int) id) + 1;
    }

    @Test
    public void updateCategoryName() throws InterruptedException {
        String newCategoryName = "updatedCategory";
        mSetOperations.updateCategoryName(mCategorySuffix - 1, newCategoryName);
        mSetOperations.apply();
        sleep(1); // Wait until the update is done
        Log.d(TAG, "New name: " + newCategoryName);
        Log.d(TAG, "Category: " + mGetOperations.getCategoryName(mCategorySuffix - 1));
        getAll();
    }

    @Test
    public void deleteCategory() {
        Log.d(TAG, "Deleting ID: " + (mCategorySuffix -1));
        mSetOperations.removeCategory(mCategorySuffix - 1);
        Log.d(TAG, "Deleted: " + (mCategorySuffix - 1));
        getAll();
    }

    @Test
    public void getAll() {
        Log.d(TAG, mGetOperations.getAllCategories().toString());
    }

    @Test
    public void getCategoryName() {
        Log.d(TAG, mGetOperations.getCategoryName(mCategorySuffix - 1));
    }

    @After
    public void finish() {
        mOperations.finishConnection();
    }
}
