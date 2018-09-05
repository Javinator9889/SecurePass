package javinator9889.securepass;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.CipherOutputStream;
import javax.crypto.SealedObject;

import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.data.entry.Entry;
import javinator9889.securepass.data.entry.QRCode;
import javinator9889.securepass.data.secret.Field;
import javinator9889.securepass.data.secret.SecurityCode;
import javinator9889.securepass.util.cipher.FileCipherOld;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private final String password = "random_password";
    private final String filename = "class.bck";
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void storeAClassInFile() throws Exception {
        FileCipherOld cipher = FileCipherOld.newInstance(password, null);
        ClassContainer container = new ClassContainer();
        List<Category> categories = new ArrayList<>();
        List<Entry> entries = new ArrayList<>();
        List<QRCode> qrCodes = new ArrayList<>();
        List<Field> fields = new ArrayList<>();
        List<SecurityCode> securityCodes = new ArrayList<>();
        Map<String, Object> preferences = new HashMap<>();

        categories.add(new Category(1, "Category_1"));
        categories.add(new Category(2, "Category_2"));

        entries.add(new Entry(1, "Account_1", "password_1",
                "icon_1", "description_1", new Category()));
        entries.add(new Entry(2, "Account_2", "password_2",
                "icon_2", "description_2", new Category()));

        qrCodes.add(new QRCode(1, "qrcode_1", "description_1", "qrdata",
                entries.get(0)));
        qrCodes.add(new QRCode(2, "qrcode_2", "description_2", "qrdata2",
                entries.get(1)));

        fields.add(new Field(1, "field_1", false,
                new SecurityCode(1, "account_1")));
        fields.add(new Field(2, "field_2", true,
                new SecurityCode(2, "account_2")));

        securityCodes.add(new SecurityCode(1, "account_1"));
        securityCodes.add(new SecurityCode(2, "account_2"));

        preferences.put("test_val", true);

        container.setCategories(categories);
        container.setEntries(entries);
        container.setQrCodes(qrCodes);
        container.setFields(fields);
        container.setSecurityCodes(securityCodes);
        container.setUserSharedPreferences(preferences);

        OutputStream stream = new FileOutputStream(filename);
        Map<SealedObject, CipherOutputStream> encrypted = cipher.encrypt(container, stream);
        CipherOutputStream createdStream = encrypted.values().iterator().next();
        SealedObject createdObject = encrypted.keySet().iterator().next();

        ObjectOutputStream outputStream = new ObjectOutputStream(createdStream);
        outputStream.writeObject(createdObject);
        outputStream.close();
        stream.close();
    }

    @Test
    public void readContentsFromFile() throws Exception {
        FileCipherOld cipher = FileCipherOld.newInstance(password, null);
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
}