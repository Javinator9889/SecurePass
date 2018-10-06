package javinator9889.securepass.objects;

import java.util.concurrent.Future;

/**
 * Created by Javinator9889 on 03/10/2018.
 */
public class SingletonFutureContainer {
    private Future<CharSequence> mPrivacyText;
    private Future<CharSequence> mLicenseText;
    private Future<CharSequence> mToSText;
    private static SingletonFutureContainer INSTANCE;

    public static synchronized SingletonFutureContainer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SingletonFutureContainer();
        return INSTANCE;
    }

    public Future<CharSequence> getPrivacyText() {
        return mPrivacyText;
    }

    public Future<CharSequence> getLicenseText() {
        return mLicenseText;
    }

    public Future<CharSequence> getToSText() {
        return mToSText;
    }

    public void setPrivacyText(Future<CharSequence> privacyText) {
        this.mPrivacyText = privacyText;
    }

    public void setLicenseText(Future<CharSequence> licenseText) {
        this.mLicenseText = licenseText;
    }

    public void setToSText(Future<CharSequence> toSText) {
        this.mToSText = toSText;
    }
}
