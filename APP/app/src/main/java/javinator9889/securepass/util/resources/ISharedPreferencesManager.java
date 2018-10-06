package javinator9889.securepass.util.resources;

/**
 * Created by Javinator9889 on 04/04/2018.
 */
public interface ISharedPreferencesManager {
    boolean isApplicationInitialized();
    void applicationInitialized(boolean isInitialized);
    boolean isDatabaseInitialized();
    void databaseInitialized(boolean isInitialized);
    boolean isPrivacyAccepted();
    void setPrivacyAccepted(boolean isAccepted);
    boolean areTermsOfServiceAccepted();
    void setTermsOfServiceAccepted(boolean isAccepted);
    boolean isSoftwareLicenseAccepted();
    void setSoftwareLicenseAccepted(boolean isAccepted);
    boolean isApplicationLicenseAccepted();
}
