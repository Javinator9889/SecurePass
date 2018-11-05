package javinator9889.securepass;

import com.github.javinator9889.exporter.FileToBytesExporter;

import java.io.File;
import java.io.IOException;

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
 * Created by Javinator9889 on 05/11/2018 - APP.
 */
public class Generator {
    /**
     * Do not let anyone use this class
     */
    private Generator() {}

    /**
     * Generates a bytes file from given file.
     *
     * @param args {@code String[]} containing, at 0: "filename"; at 1: output file path.
     * @throws IOException when an error occurs (see {@link FileToBytesExporter#readSource()} &
     *                     {@link FileToBytesExporter#writeObject(File)}).
     */
    public static void main(String[] args) throws IOException {
        if (args.length <= 1)
            throw new IllegalArgumentException("At least two arguments must be included " +
                    "(filename & output file)");
        FileToBytesExporter exporter = new FileToBytesExporter(args[0]);
        exporter.readSource(true);
        exporter.writeObject(new File(args[1]));
    }
}
