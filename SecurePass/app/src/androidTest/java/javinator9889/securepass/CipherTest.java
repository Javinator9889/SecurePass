package javinator9889.securepass;

import android.content.Context;

import com.google.common.io.ByteStreams;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import androidx.test.InstrumentationRegistry;
import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.data.entry.Entry;
import javinator9889.securepass.data.entry.QRCode;
import javinator9889.securepass.data.secret.Field;
import javinator9889.securepass.data.secret.SecurityCode;

/**
 * Created by Javinator9889 on 20/04/2018. - test must be updated
 */
@Deprecated
public class CipherTest {
    private Context context = InstrumentationRegistry.getTargetContext();
    private String password = "random_password";
    private String filename = context.getFilesDir().getAbsolutePath() + "/class.bck";
    private String iv_filename = context.getFilesDir().getAbsolutePath() + "/iv";
    private byte[] iv;

//    @Test
//    public void storeAClassInFile() throws Exception {
//        FileCipherOld cipher = FileCipherOld.getInstance(password, null);
//        iv = cipher.getIv();
//        System.out.println(Arrays.toString(iv));
//        storeIv();
//        ClassContainer container = DataClassForTests.CONTAINER_TEST_CLASS();
//
//        OutputStream stream = new FileOutputStream(filename);
//        Map<SealedObject, CipherOutputStream> encrypted = cipher.encrypt(container, stream);
//        CipherOutputStream createdStream = encrypted.values().iterator().next();
//        SealedObject createdObject = encrypted.keySet().iterator().next();
//
//        ObjectOutputStream outputStream = new ObjectOutputStream(createdStream);
//        outputStream.writeObject(createdObject);
//        outputStream.close();
//        stream.close();
//    }

    @Test
    public void readContentsFromFile() throws Exception {
        return;
    }

    private void storeIv() throws Exception {
        OutputStream ivStream = new FileOutputStream(iv_filename);
        ivStream.write(iv);
        ivStream.close();
    }

    private void readIv() throws Exception {
        InputStream ivStream = new FileInputStream(iv_filename);
        this.iv = ByteStreams.toByteArray(ivStream);
    }
}
