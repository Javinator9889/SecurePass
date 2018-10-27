package javinator9889.securepass.objects;

/**
 * Class for containing a <code>ByteArray</code>
 * Created by Javinator9889 on 21/09/2018.
 */
public class ByteArrayKeeper {
    private byte[] mArray;
    private boolean mIsAnyArrayStored;

    /**
     * Default constructor creates an empty array
     *
     * @see #ByteArrayKeeper(byte[])
     */
    public ByteArrayKeeper() {
        this(new byte[]{});
    }

    /**
     * Stores the given array in the class
     *
     * @param array object to store
     */
    public ByteArrayKeeper(byte[] array) {
        this.mArray = array;
        this.mIsAnyArrayStored = array.length > 0;
    }

    /**
     * Checks if there is any array stored
     *
     * @return <code>boolean</code> 'true' if there is anyone stored, else 'false'
     */
    public boolean isAnyArrayStored() {
        return mIsAnyArrayStored;
    }

    /**
     * Stores an array in class
     *
     * @param source the array to store
     */
    public void storeArray(byte[] source) {
        this.mArray = source;
        mIsAnyArrayStored = source.length > 0;
    }

    /**
     * Gets the stored array in class
     *
     * @return <code>byte[]</code> array if anyone stored, else {@code null}
     * @see #isAnyArrayStored()
     */
    public byte[] getArray() {
        return isAnyArrayStored() ? mArray : null;
    }
}
