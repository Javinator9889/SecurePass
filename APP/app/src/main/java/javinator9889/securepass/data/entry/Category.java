package javinator9889.securepass.data.entry;

import java.io.Serializable;

/**
 * Class for containing categories
 * Created by Javinator9889 on 29/03/2018.
 */
public class Category implements Serializable {
    private int mId;
    private String mName;

    /**
     * Public available constructor for generating Category
     *
     * @param id   category ID
     * @param name category name
     */
    public Category(int id, String name) {
        this.mId = id;
        this.mName = name;
    }

    /**
     * @deprecated Default constructor - deprecated. Use {@link #Category(int, String)} instead
     */
    @Deprecated
    public Category() {
        this.mId = 0;
        this.mName = "Global";
    }

    /**
     * Obtains current category ID
     *
     * @return <code>long</code> with the ID
     */
    public int getId() {
        return mId;
    }

    /**
     * Obtains current category name
     *
     * @return <code>String</code> with the name
     */
    public String getName() {
        return mName;
    }

    /**
     * Updates current category name
     *
     * @param name new name
     */
    public void setName(String name) {
        this.mName = name;
    }

    @Override
    public String toString() {
        return "Category ID: " + mId + "\n" +
                "Category name: " + mName + "\n";
    }
}
