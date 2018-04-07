package javinator9889.securepass.network.drive;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.widget.DataBufferAdapter;

/**
 * Created by Javinator9889 on 07/04/2018.
 */
public class ResultsAdapter extends DataBufferAdapter<Metadata> {
    public ResultsAdapter(@NonNull Context context) {
        super(context, android.R.layout.simple_list_item_1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(getContext(), android.R.layout.simple_list_item_1, null);
        }
        Metadata metadata = getItem(position);
        TextView titleTextView = convertView.findViewById(android.R.id.text1);
        titleTextView.setText(metadata.getTitle());
        return convertView;
    }
}
