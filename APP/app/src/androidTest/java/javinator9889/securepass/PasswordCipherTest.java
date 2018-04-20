package javinator9889.securepass;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import javinator9889.securepass.util.cipher.PasswordCipher;
import javinator9889.securepass.util.cipher.PasswordSaver;

/**
 * Created by Javinator9889 on 20/04/2018.
 */
public class PasswordCipherTest {
    @Test
    public void saveARandomPassword() throws Exception {
        //SecurePass app = SecurePass.getApplicationInstance();
        Context context = InstrumentationRegistry.getTargetContext();
        PasswordCipher cipher = PasswordSaver.instantiate(context);
        cipher.putPassword("test_password");
        System.out.println(cipher.getPassword());
        cipher.changeCurrentExistingPassword("new_password");
        System.out.println(cipher.getPassword());
    }
}
