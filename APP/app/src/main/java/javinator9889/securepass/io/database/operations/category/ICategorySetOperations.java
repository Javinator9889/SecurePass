package javinator9889.securepass.io.database.operations.category;

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
 * Created by Javinator9889 on 31/10/2018 - APP.
 */
public interface ICategorySetOperations {
    /**
     * Registers a new simple category
     *
     * @param categoryName the name of the new category
     * @return {@code long} with the new category ID
     */
    long registerNewCategory(@NonNull String categoryName);

    /**
     * Updates the category name by using the given ID
     *
     * @param categoryId   id of the category to update
     * @param categoryName new name
     */
    void updateCategoryName(long categoryId, @NonNull String categoryName);

    /**
     * Removes the hole category by the given ID
     *
     * @param categoryId category ID to remove
     */
    void removeCategory(long categoryId);
}
