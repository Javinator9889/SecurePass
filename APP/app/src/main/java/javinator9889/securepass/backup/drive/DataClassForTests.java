package javinator9889.securepass.backup.drive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javinator9889.securepass.data.container.ClassContainer;
import javinator9889.securepass.data.entry.Category;
import javinator9889.securepass.data.entry.Entry;
import javinator9889.securepass.data.entry.QRCode;
import javinator9889.securepass.data.secret.Field;
import javinator9889.securepass.data.secret.SecurityCode;

/**
 * Created by Javinator9889 on 21/04/2018.
 */
public class DataClassForTests {
    // DO NOT LET ANYONE INIT THIS CLASS
    private DataClassForTests(){}

    public static ClassContainer CONTAINER_TEST_CLASS() {
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
        return container;
    }
}
