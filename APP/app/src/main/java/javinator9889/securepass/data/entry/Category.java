package javinator9889.securepass.data.entry;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class for containing categories
 * Created by Javinator9889 on 29/03/2018.
 */
public class Category implements Serializable {
    private long mId;
    private String mName;

    /**
     * Public available constructor for generating Category
     *
     * @param id   category ID
     * @param name category name
     */
    public Category(long id, String name) {
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
    public long getId() {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Category ID: " + mId + "\n" +
                "Category name: " + mName + "\n";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return mId == category.mId &&
                Objects.equals(mName, category.mName);
    }

    /**
     * {@inheritDoc}
     *
     * @see Objects#hash(Object...)
     */
    @Override
    public int hashCode() {
        return Objects.hash(mId, mName);
    }
}
