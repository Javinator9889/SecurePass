package javinator9889.securepass.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.concurrent.Future;

/**
 * Created by Javinator9889 on 23/09/2018.
 */
public class ParcelableShared<T> implements Parcelable {
    private ArrayList<Future<T>> mData;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeParcelable(this, 0);
//        dest.writeList(mData);
    }

    public static final Parcelable.Creator<ParcelableShared> CREATOR =
            new Parcelable.Creator<ParcelableShared>() {
                @Override
                public ParcelableShared createFromParcel(Parcel source) {
                    return new ParcelableShared(source);
                }

                @Override
                public ParcelableShared[] newArray(int size) {
                    return new ParcelableShared[size];
                }
            };

    private ParcelableShared(Parcel in) {
        mData = in.readArrayList(getClass().getClassLoader());
    }

    public ParcelableShared(Future<T> data) {
        this.mData = new ArrayList<>(1);
        mData.add(data);
    }

    public ParcelableShared() {
        this.mData = new ArrayList<>(1);
    }

    public Future<T> getData() {
        return mData.get(0);
    }
}
