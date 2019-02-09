/*
 * Copyright © 2019 - present | SecurePass by Javinator9889
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
 * Created by Javinator9889 on 23/08/2018 - SecurePass.
 */
package javinator9889.securepass.backup.drive;

import android.content.Context;

import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.widget.DataBufferAdapter;

import androidx.annotation.NonNull;

/**
 * Store and save recovered documents from Google Drive
 *
 * @see DataBufferAdapter
 * @see Metadata
 * Created by Javinator9889 on 07/04/2018.
 */
public class ResultsAdapter extends DataBufferAdapter<Metadata> {
    /**
     * Required public constructor that invokes
     * {@link DataBufferAdapter#DataBufferAdapter(Context, int) super method} super ones with a
     * {@link android.R.layout#simple_list_item_1 simple list item} by default
     *
     * @param context <code>Context</code> when instantiating this class
     */
    public ResultsAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }
}
