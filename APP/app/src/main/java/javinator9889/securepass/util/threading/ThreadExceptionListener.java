package javinator9889.securepass.util.threading;

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
 * Created by Javinator9889 on 30/10/2018 - APP.
 * Interface that must be implemented in order to handle exceptions
 */
public interface ThreadExceptionListener {
    /**
     * Method that is called when a exception occurs
     *
     * @param exception the exception that has been thrown
     * @param thread    the thread that threw the exception
     * @see Thread#getUncaughtExceptionHandler()
     */
    void onThreadExceptionListener(@NonNull Throwable exception, @NonNull Thread thread);
}
