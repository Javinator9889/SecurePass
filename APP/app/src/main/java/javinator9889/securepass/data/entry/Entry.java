package javinator9889.securepass.data.entry;

import androidx.annotation.NonNull;

import java.io.Serializable;

import javinator9889.securepass.data.entry.fields.IImage;
import javinator9889.securepass.data.entry.fields.IPassword;
import javinator9889.securepass.data.entry.fields.IText;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;

/**
 * Created by Javinator9889 on 29/03/2018.
 */
public class Entry implements Serializable {
    private int id;
    private GeneralObjectContainer<IImage> images;
    private GeneralObjectContainer<IPassword> passwords;
    private GeneralObjectContainer<IText> smallTexts;
    private GeneralObjectContainer<IText> longTexts;
    private String icon;
    private Category category;
    private String name;
    //    private String description;
    //    private String accountName;
    //    private String accountPassword;

//    public Entry(int id, @NonNull String accountName, @NonNull String accountPassword,
//                 @NonNull String icon, @Nullable String description, @NonNull Category category) {
//        this.id = id;
//        this.accountName = accountName;
//        this.accountPassword = accountPassword;
//        this.icon = icon;
//        this.description = description;
//        this.category = category;
//    }

    public Entry(int id, @NonNull IImage[] images, @NonNull IPassword[] passwords,
                 @NonNull IText[] smallTexts, @NonNull IText[] longTexts,
                 @NonNull String icon, @NonNull Category category, @NonNull String name) {
        this.id = id;
        this.icon = icon;
        this.category = category;
        this.name = name;
        this.images = new ObjectContainer<>(images);
        this.passwords = new ObjectContainer<>(passwords);
        this.smallTexts = new ObjectContainer<>(smallTexts);
        this.longTexts = new ObjectContainer<>(longTexts);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GeneralObjectContainer<IImage> getImages() {
        return images;
    }

    public GeneralObjectContainer<IText> getLongTexts() {
        return longTexts;
    }

    public GeneralObjectContainer<IPassword> getPasswords() {
        return passwords;
    }

    public GeneralObjectContainer<IText> getSmallTexts() {
        return smallTexts;
    }

    public void addImage(IImage image) {
        this.images.storeObject(image);
    }

    public void addPassword(IPassword password) {
        this.passwords.storeObject(password);
    }

    public void addSmallText(IText smallText) {
        this.smallTexts.storeObject(smallText);
    }

    public void addLongText(IText longText) {
        this.longTexts.storeObject(longText);
    }

    //    public String getAccountName() {
//        return accountName;
//    }

//    public void setAccountName(String accountName) {
//        this.accountName = accountName;
//    }

//    public String getAccountPassword() {
//        return accountPassword;
//    }

//    public void setAccountPassword(String accountPassword) {
//        this.accountPassword = accountPassword;
//    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

//    public String getDescription() {
//        return description;
//    }

//    public void setDescription(String description) {
//        this.description = description;
//    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Entry ID: " + id + "\nEntry passwords: " + passwords.toString() + "\nEntry icon: " +
                icon + "\nEntry name: " + name + "\nEntry category: " + category.toString() +
                "\nEntry small texts: " + smallTexts.toString() + "\nEntry long texts: " +
                longTexts.toString() + "\nEntry images: " + images.toString();
//        return "Entry ID: " + id + "\nEntry account name: " + accountName + "\nEntry password: " +
//                accountPassword + "\nEntry icon: " + icon + "\nEntry description: " + description +
//                "\nEntry category: " + category.toString();
    }
}
