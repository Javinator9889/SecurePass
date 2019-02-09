/*
 * Copyright © 2018 - present | SecurePass by Javinator9889
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.  If
 * not, see https://www.gnu.org/licenses/.
 *
 * Created by Javinator9889 on 03/11/2018 - SecurePass.
 */
package javinator9889.securepass.objects;

import java.util.concurrent.Future;

/**
 * Class for containing <code>Future</code> objects that will be used on each app first start.
 */
public class SingletonFutureContainer {
    private static SingletonFutureContainer INSTANCE;
    private Future<CharSequence> mPrivacyText;
    private Future<CharSequence> mLicenseText;
    private Future<CharSequence> mToSText;

    /**
     * Default synchronized constructor which returns a generated instance (or a new one)
     *
     * @return {@link #INSTANCE} if existing, else new one
     */
    public static synchronized SingletonFutureContainer getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SingletonFutureContainer();
        return INSTANCE;
    }

    /**
     * Obtains the privacy text stored
     *
     * @return <code>Future</code> with the text
     * @throws NullPointerException when there is no text stored
     */
    public Future<CharSequence> getPrivacyText() {
        if (mPrivacyText == null)
            throw new NullPointerException("There is no privacy text future object stored");
        return mPrivacyText;
    }

    /**
     * Sets the privacy text future object
     *
     * @param privacyText future text to store
     * @throws NullPointerException when the <code>privacyText</code> is <code>null</code>
     */
    public void setPrivacyText(Future<CharSequence> privacyText) {
        if (privacyText == null)
            throw new NullPointerException("privacyText is null");
        this.mPrivacyText = privacyText;
    }

    /**
     * Obtains the license text stored
     *
     * @return <code>Future</code> with the text
     * @throws NullPointerException when there is no text stored
     */
    public Future<CharSequence> getLicenseText() {
        if (mPrivacyText == null)
            throw new NullPointerException("There is no license text future object stored");
        return mLicenseText;
    }

    /**
     * Sets the license text future object
     *
     * @param licenseText future text to store
     * @throws NullPointerException when the <code>licenseText</code> is <code>null</code>
     */
    public void setLicenseText(Future<CharSequence> licenseText) {
        if (licenseText == null)
            throw new NullPointerException("licenseText is null");
        this.mLicenseText = licenseText;
    }

    /**
     * Obtains the terms of service text stored
     *
     * @return <code>Future</code> with the text
     * @throws NullPointerException when there is no text stored
     */
    public Future<CharSequence> getToSText() {
        if (mPrivacyText == null)
            throw new NullPointerException("There is no terms of service text future object " +
                    "stored");
        return mToSText;
    }

    /**
     * Sets the license text future object
     *
     * @param toSText future text to store
     * @throws NullPointerException when the <code>toSText</code> is <code>null</code>
     */
    public void setToSText(Future<CharSequence> toSText) {
        if (toSText == null)
            throw new NullPointerException("toSText is null");
        this.mToSText = toSText;
    }
}
