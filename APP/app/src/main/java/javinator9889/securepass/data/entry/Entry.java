package javinator9889.securepass.data.entry;

import java.io.Serializable;

import androidx.annotation.NonNull;
import javinator9889.securepass.data.entry.fields.IImage;
import javinator9889.securepass.data.entry.fields.IPassword;
import javinator9889.securepass.data.entry.fields.IText;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class Entry implements Serializable {
    private long mId;
    private GeneralObjectContainer<IImage> mImages;
    private GeneralObjectContainer<IPassword> mPasswords;
    private GeneralObjectContainer<IText> mSmallTexts;
    private GeneralObjectContainer<IText> mLongTexts;
    private String mIcon;
    private long mCategoryId;
    private long mConfigId;
    private String mName;

    /**
     * Public Entry constructor which contains some different fields
     *
     * @param id         entry ID
     * @param images     array containing {@link IImage} classes
     * @param passwords  array containing {@link IPassword} classes
     * @param smallTexts array containing {@link IText} classes - see
     *                   {@link javinator9889.securepass.data.entry.fields.SmallText}
     * @param longTexts  array containing {@link IText} classes - see
     *                   {@link javinator9889.securepass.data.entry.fields.LongText}
     * @param icon       entry icon
     * @param category   entry category
     * @param name       entry name
     * @see javinator9889.securepass.data.entry.fields.Image
     * @see javinator9889.securepass.data.entry.fields.Password
     * @see javinator9889.securepass.data.entry.fields.Text
     * @see javinator9889.securepass.data.entry.fields.SmallText
     * @see javinator9889.securepass.data.entry.fields.LongText
     * @see Category
     * @deprecated use {@link #Entry(long, String, String, long, long)} instead
     */
    @Deprecated
    public Entry(long id,
                 @NonNull IImage[] images,
                 @NonNull IPassword[] passwords,
                 @NonNull IText[] smallTexts,
                 @NonNull IText[] longTexts,
                 @NonNull String icon,
                 @NonNull Category category,
                 @NonNull String name) {
        this.mId = id;
        this.mIcon = icon;
//        this.mCategoryId = category;
        this.mName = name;
        this.mImages = new ObjectContainer<>(images);
        this.mPasswords = new ObjectContainer<>(passwords);
        this.mSmallTexts = new ObjectContainer<>(smallTexts);
        this.mLongTexts = new ObjectContainer<>(longTexts);
    }

    /**
     * Public constructor declaring only the needed entry params
     *
     * @param id              entry ID
     * @param name            entry name
     * @param icon            entry icon
     * @param categoryId      entry parent category ID
     * @param configurationId entry parent configuration ID
     */
    public Entry(long id,
                 @NonNull String name,
                 @NonNull String icon,
                 long categoryId,
                 long configurationId) {
        mId = id;
        mName = name;
        mIcon = icon;
        mCategoryId = categoryId;
        mConfigId = configurationId;
    }

    /**
     * Obtains current entry ID
     *
     * @return <code>long</code> with the ID
     */
    public long getId() {
        return mId;
    }

    /**
     * Obtains current entry name
     *
     * @return <code>String</code> with the name
     */
    public String getName() {
        return mName;
    }

    /**
     * Updates entry name
     *
     * @param name new name
     */
    public void setName(String name) {
        this.mName = name;
    }

    /**
     * Obtains all available images inside entry
     *
     * @return a {@link GeneralObjectContainer} with the images
     * @see IImage
     * @see javinator9889.securepass.data.entry.fields.Image
     * @see GeneralObjectContainer
     * @see ObjectContainer
     */
    public GeneralObjectContainer<IImage> getImages() {
        return mImages;
    }

    /**
     * Obtains all available long texts inside entry
     *
     * @return a {@link GeneralObjectContainer} with texts
     * @see IText
     * @see javinator9889.securepass.data.entry.fields.Text
     * @see javinator9889.securepass.data.entry.fields.LongText
     * @see GeneralObjectContainer
     * @see ObjectContainer
     */
    public GeneralObjectContainer<IText> getLongTexts() {
        return mLongTexts;
    }

    /**
     * Obtains all available passwords inside entry
     *
     * @return a {@link GeneralObjectContainer} with passwords
     * @see IPassword
     * @see javinator9889.securepass.data.entry.fields.Password
     * @see GeneralObjectContainer
     * @see ObjectContainer
     */
    public GeneralObjectContainer<IPassword> getPasswords() {
        return mPasswords;
    }

    /**
     * Obtains all available small texts inside entry
     *
     * @return a {@link GeneralObjectContainer} with texts
     * @see IText
     * @see javinator9889.securepass.data.entry.fields.Text
     * @see javinator9889.securepass.data.entry.fields.SmallText
     * @see GeneralObjectContainer
     * @see ObjectContainer
     */
    public GeneralObjectContainer<IText> getSmallTexts() {
        return mSmallTexts;
    }

    /**
     * Add a new image to the entry
     *
     * @param image new image
     * @see IImage
     * @see javinator9889.securepass.data.entry.fields.Image
     */
    public void addImage(IImage image) {
        this.mImages.storeObject(image);
    }

    /**
     * Add a new password to the entry
     *
     * @param password new password
     * @see IPassword
     * @see javinator9889.securepass.data.entry.fields.Password
     */
    public void addPassword(IPassword password) {
        this.mPasswords.storeObject(password);
    }

    /**
     * Add a new small text to the entry
     *
     * @param smallText new text
     * @see IText
     * @see javinator9889.securepass.data.entry.fields.Text
     * @see javinator9889.securepass.data.entry.fields.SmallText
     */
    public void addSmallText(IText smallText) {
        this.mSmallTexts.storeObject(smallText);
    }

    /**
     * Add a new long text to the entry
     *
     * @param longText new text
     * @see IText
     * @see javinator9889.securepass.data.entry.fields.Text
     * @see javinator9889.securepass.data.entry.fields.LongText
     */
    public void addLongText(IText longText) {
        this.mLongTexts.storeObject(longText);
    }

    /**
     * Obtains current entry icon
     *
     * @return <code>String</code> with the icon
     */
    public String getIcon() {
        return mIcon;
    }

    /**
     * Sets a new icon for the entry
     *
     * @param icon new icon
     */
    public void setIcon(String icon) {
        this.mIcon = icon;
    }

    /**
     * Gets current entry category
     *
     * @return <code>Category</code> in which this entry is stored
     * @see Category
     */
    /*public Category getCategoryId() {
        return mCategoryId;
    }*/

    /**
     * Updates current entry categoryId
     *
     * @param categoryId new categoryId
     * @see Category
     */
    public void setCategoryId(Category categoryId) {
        this.mCategoryId = categoryId;
    }

    /*@Override
    public String toString() {
        return "Entry ID: " + mId +
                "\nEntry mPasswords: " + mPasswords.toString() +
                "\nEntry mIcon: " + mIcon +
                "\nEntry mName: " + mName +
                "\nEntry mCategoryId: " + mCategoryId.toString() +
                "\nEntry small texts: " + mSmallTexts.toString() +
                "\nEntry long texts: " + mLongTexts.toString() +
                "\nEntry mImages: " + mImages.toString();
    }*/
}
