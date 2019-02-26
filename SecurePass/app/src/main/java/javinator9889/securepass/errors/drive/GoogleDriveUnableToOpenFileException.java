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
 * Created by Javinator9889 on 07/04/2018 - SecurePass.
 */
package javinator9889.securepass.errors.drive;

/**
 * Custom exception that is thrown when Google Drive cannot open a file.
 */
public class GoogleDriveUnableToOpenFileException extends RuntimeException {
    /**
     * {@inheritDoc}
     */
    public GoogleDriveUnableToOpenFileException(String message) {
        super(message);
    }
}