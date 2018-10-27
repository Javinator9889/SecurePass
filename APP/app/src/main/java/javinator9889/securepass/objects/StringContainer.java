package javinator9889.securepass.objects;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;

/**
 * Created by Javinator9889 on 02/10/2018.
 */
@AutoValue
public abstract class StringContainer implements Parcelable {
    public static Builder builder() {
        return new AutoValue_StringContainer.Builder();
    }

    public abstract String licenseText();

    public abstract String privacyText();

    public abstract String termsText();

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setLicenseText(String licenseText);

        public abstract Builder setPrivacyText(String privacyText);

        public abstract Builder setTermsText(String termsText);

        public abstract StringContainer build();
    }
}
