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

import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.objects.GeneralObjectContainer;

/**
 * Interface for accessing the GET operations for the Category class.
 */
public interface ICategoryGetOperations {
    /**
     * Obtains the category name by using the given ID
     *
     * @param categoryId the category ID where obtaining the name
     * @return {@code String} with the name
     */
    String getCategoryName(long categoryId);

    /**
     * Obtains all categories' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link Category}
     *
     * @return {@code GeneralObjectContainer} of categories
     * @see javinator9889.securepass.objects.ObjectContainer
     * @see Category
     */
    GeneralObjectContainer<Category> getAllCategories();
}
