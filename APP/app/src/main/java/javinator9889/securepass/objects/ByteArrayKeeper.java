package javinator9889.securepass.objects;

/**
 * Created by Javinator9889 on 21/09/2018.
 */
public class ByteArrayKeeper {
    private byte[] array;
    private int size;

    public ByteArrayKeeper() {
        this.size = -1;
        this.array = new byte[]{};
    }

    public ByteArrayKeeper(byte[] array) {
        this.array = array;
        this.size = array.length;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void storeArray(byte[] source) {
        this.array = source;
    }

    public byte[] getArray() {
        return array;
    }
}
