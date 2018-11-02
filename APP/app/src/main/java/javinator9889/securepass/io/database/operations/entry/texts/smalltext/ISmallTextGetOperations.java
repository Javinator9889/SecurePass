package javinator9889.securepass.io.database.operations.entry.texts.smalltext;

import javinator9889.securepass.data.entry.fields.SmallText;
import javinator9889.securepass.io.database.operations.entry.texts.ITextGetOperations;
import javinator9889.securepass.objects.GeneralObjectContainer;

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
 * Created by Javinator9889 on 02/11/2018 - APP.
 */
public interface ISmallTextGetOperations extends ITextGetOperations {
    /**
     * Obtains all small texts' data and saves it inside a {@link GeneralObjectContainer} of
     * {@link SmallText}
     *
     * @return {@code GeneralObjectContainer} of entries
     * @see javinator9889.securepass.objects.ObjectContainer
     * @see SmallText
     */
    GeneralObjectContainer<SmallText> getAllSmallTexts();
}
