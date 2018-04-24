package javinator9889.securepass.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by Javinator9889 on 24/04/2018.
 */
public class Cloner {
    public static InputStream clone(final InputStream inputStream) {
        try {
            inputStream.mark(0);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int readLength = 0;
            while ((readLength = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readLength);
            }
//            inputStream.reset();
            outputStream.flush();
            return new ByteArrayInputStream(outputStream.toByteArray());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
