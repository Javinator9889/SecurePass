package javinator9889.securepass.objects;

import android.content.Context;
import android.os.Parcel;

import com.ryanharter.auto.value.parcel.TypeAdapter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javinator9889.securepass.SecurePass;
import ru.noties.markwon.Markwon;

/**
 * Created by Javinator9889 on 02/10/2018.
 */
public class FutureTypeAdapter implements TypeAdapter<Future<CharSequence>> {
    private ExecutorService mService = Executors
            .newFixedThreadPool(SecurePass.getNumberOfProcessors());

    @Override
    public Future<CharSequence> fromParcel(Parcel in) {
        return mService.submit(() -> {
            String sourceText = in.readString();
            Context appContext = SecurePass.getApplicationInstance().getApplicationContext();
            return Markwon.markdown(appContext, sourceText);
        });
    }

    @Override
    public void toParcel(Future<CharSequence> value, Parcel dest) {
        try {
            dest.writeString(value.get().toString());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
//        dest.writeValue(value);
    }
}
