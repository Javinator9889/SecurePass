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
import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.data.entry.Entry;
import javinator9889.securepass.data.entry.QRCode;
import javinator9889.securepass.data.secret.Field;
import javinator9889.securepass.data.secret.SecurityCode;
import javinator9889.securepass.util.cipher.FileCipherOld;

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
        readIv();
        FileCipherOld cipher = FileCipherOld.newInstance(password, iv);
        System.out.println(Arrays.toString(iv));
        InputStream stream = new FileInputStream(filename);
        ClassContainer restoredBackup = (ClassContainer) cipher.decrypt(stream);
        for (Category category : restoredBackup.getCategories())
            System.out.println(category.toString());
        for (Entry entry : restoredBackup.getEntries())
            System.out.println(entry.toString());
        for (QRCode qrCode : restoredBackup.getQrCodes())
            System.out.println(qrCode.toString());
        for (Field field : restoredBackup.getFields())
            System.out.println(field.toString());
        for (SecurityCode code : restoredBackup.getSecurityCodes())
            System.out.println(code.toString());
        System.out.println(restoredBackup.getUserSharedPreferences().toString());
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
