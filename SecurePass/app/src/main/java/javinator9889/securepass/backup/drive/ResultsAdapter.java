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
