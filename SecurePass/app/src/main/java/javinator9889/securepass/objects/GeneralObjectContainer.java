package javinator9889.securepass.objects;

import android.util.SparseArray;

import java.util.Iterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Interface for accessing {@link ObjectContainer}
 * Created by Javinator9889 on 25/04/2018.
 */
public interface GeneralObjectContainer<ObjectType> extends Iterable<ObjectType> {
    /**
     * Obtains the stored object at the specified index
     *
     * @param index position where the object is stored
     * @return <code>ObjectType</code> if found, else null
     */
    @Nullable
    ObjectType getStoredObjectAtIndex(int index);

    /**
     * Obtains the latest stored object (latest position)
     *
     * @return {@code ObjectType} with the object
     * @see SparseArray#size()
     * @see SparseArray#get(int)
     */
    ObjectType getLatestStoredObject();

    /**
     * Stores the object at the latest position by using {@link #storeObject(Object, int)}
     *
     * @param newObject object to store
     */
    void storeObject(@NonNull ObjectType newObject);

    /**
     * Inserts the object at the given position
     *
     * @param newObject object to store
     * @param index     position where the object will be inserted
     */
    void storeObject(@NonNull ObjectType newObject, int index);

    /**
     * Checks if the provided {@code ObjectType} is stored
     *
     * @param objectToSearch the object that will be searched
     * @return <code>boolean</code> matching 'true' if found, else 'false'
     * @see SparseArray#indexOfValue(Object)
     */
    boolean isObjectStored(@NonNull ObjectType objectToSearch);

    /**
     * Removes the object that is stored at the specified index
     *
     * @param index position where the object will be removed
     */
    void removeObjectAtIndex(int index);

    /**
     * Removes the provided object if exists
     *
     * @param objectToRemove the object that will be deleted from the <code>SparseArray</code>
     * @see #removeObjectAtIndex(int)
     * @see #getIndexForObject(Object)
     */
    void removeObjectStored(@NonNull ObjectType objectToRemove);

    /**
     * Returns an iterator over elements of type {@code ObjectType}.
     *
     * @return an Iterator.
     */
    @Override
    Iterator<ObjectType> iterator();

    /**
     * Gets the number of elements stored starting at one
     *
     * @return {@code int} with the number of elements
     */
    int getObjectCountFromOne();

    /**
     * Gets the number of elements stored starting at zero
     *
     * @return {@code int} with the number of elements
     * @see #getObjectCountFromOne()
     */
    int getObjectCountFromZero();

    /**
     * Clears all the objects
     */
    void removeAllObjects();

    /**
     * Obtains the index of a given value
     *
     * @param object object to obtain the index from
     * @return <code>int</code> with the index - if not found, it will be negative
     */
    int getIndexForObject(@NonNull ObjectType object);

    /**
     * Determines whether there is any object stored
     *
     * @return 'true' if there is almost one, else 'false'
     */
    boolean isAnyObjectStored();
}
