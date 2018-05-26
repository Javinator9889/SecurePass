package javinator9889.securepass;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Map;

import javinator9889.securepass.io.database.DatabaseManager;
import javinator9889.securepass.io.database.DatabaseOperations;

/**
 * Created by Javinator9889 on 20/05/2018.
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private Context context = InstrumentationRegistry.getTargetContext();

    private DatabaseOperations op;

    @Before
    public void initParams() {
        DatabaseManager manager = DatabaseManager.newInstance(context, "1234");
        this.op = DatabaseOperations.newInstance(manager);
    }

    @Test
    public void insertIntoDB() {
        long defaultCategoryId = op.registerDefaultCategory();
        long newAccountId = op.registerNewAccount("cuenta", "password",
                "icono", "descripción", defaultCategoryId);
        long newCategoryId = op.registerNewCategory("cat2");
        long newQRCodeId = op.registerQRCode(newAccountId, "qr1", "desc",
                "1234");
        long newSecurityCodeId = op.registerNewSecurityCodeSource("sec1");
        long newFieldForSecCodeId = op.registerNewFieldForSecurityCodeSource("12345",
                false,
                newSecurityCodeId);
        op.updateInformationForCategory("cat3", newCategoryId);
        op.updateInformationForEntry("cuent", "pass", "icon",
                "desc", newCategoryId, newAccountId);
        op.updateInformationForQRCode(newAccountId, "qr1,2", "descq", "data",
                newQRCodeId);
        op.updateInformationForSecurityCode("secCode", newSecurityCodeId);
        op.updateInformationForField("12321", true, newFieldForSecCodeId,
                newSecurityCodeId);
        printer();
        op.registerNewAccount("cuenta", "password", "icono",
                "descripción", defaultCategoryId);
        op.registerNewCategory("cat2");
        op.registerQRCode(newAccountId, "qr1", "desc",
                "1234");
        op.registerNewSecurityCodeSource("sec1");
        op.registerNewFieldForSecurityCodeSource("12345",false,
                newSecurityCodeId);
        printer();
        op.deleteCategory(newCategoryId);
        op.deleteAccount(newAccountId);
        op.deleteQRCode(newQRCodeId);
        op.deleteSecurityCode(newSecurityCodeId);
        op.deleteField(newFieldForSecCodeId);
        printer();
    }

    private void printer() {
        for (Map<String, Object> objectMap : op.getAllCategories())
            System.out.println(objectMap);
        for (Map<String, Object> objectMap : op.getAllEntries())
            System.out.println(objectMap);
        for (Map<String, Object> objectMap : op.getAllQRCodes())
            System.out.println(objectMap);
        for (Map<String, Object> objectMap : op.getAllSecurityCodes())
            System.out.println(objectMap);
        for (Map<String, Object> objectMap : op.getAllFields())
            System.out.println(objectMap);
    }

    @After
    public void closeConnection() {
        op.finishConnection();
    }
}
