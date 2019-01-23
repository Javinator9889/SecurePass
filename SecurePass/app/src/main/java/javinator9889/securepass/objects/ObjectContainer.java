package javinator9889.securepass.objects;

import android.util.SparseArray;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Custom implementation of {@link SparseArray} by accessing values same as in an
 * {@link java.util.ArrayList}
 * Created by Javinator9889 on 25/04/2018.
 */
public class ObjectContainer<ObjectType> implements GeneralObjectContainer<ObjectType> {
    private SparseArray<ObjectType> mObjectsList;

    /**
     * Public default constructor - it uses {@link #ObjectContainer(int)} with a default capacity
     * of 10 elements (the same as in {@link SparseArray#SparseArray() SparseArray constructor}
     *
     * @see #ObjectContainer(int)
     * @see SparseArray#SparseArray()
     */
    public ObjectContainer() {
        this(10);
    }

    /**
     * Public constructor by using the specified capacity
     *
     * @param initialCapacity how many items are going to be stored
     */
    public ObjectContainer(int initialCapacity) {
        mObjectsList = new SparseArray<>(initialCapacity);
    }

    /**
     * Public constructor by giving the objects that will be stored first - uses
     * {@link #ObjectContainer(int)} with the amount of provided objects and
     * {@link #addAll(Object[])} for storing the objects
     *
     * @param objects non null objects which will be stored
     */
    @SafeVarargs
    public ObjectContainer(@NonNull ObjectType... objects) {
        this(objects.length);
        addAll(objects);
    }

    /**
     * Saves in the <code>SparseArray</code> the objects provided at
     * {@link #ObjectContainer(Object[])} constructor
     *
     * @param objects objects provided at the constructor
     * @see SparseArray#put(int, Object)
     */
    private void addAll(@NonNull ObjectType[] objects) {
        for (int i = 0; i < objects.length; ++i)
            mObjectsList.put(i, objects[i]);
    }

    /**
     * Obtains the stored object at the specified index
     *
     * @param index position where the object is stored
     * @return <code>ObjectType</code> if found, else null
     */
    @Override
    @Nullable
    public ObjectType getStoredObjectAtIndex(int index) {
        return mObjectsList.get(index, null);
    }

    /**
     * Obtains the latest stored object (latest position)
     *
     * @return {@code ObjectType} with the object
     * @see SparseArray#size()
     * @see SparseArray#get(int)
     */
    @Override
    public ObjectType getLatestStoredObject() {
        int latest = mObjectsList.size() - 1;
        return mObjectsList.get(latest);
    }

    /**
     * Stores the object at the latest position by using {@link #storeObject(Object, int)}
     *
     * @param newObject object to store
     */
    @Override
    public void storeObject(@NonNull ObjectType newObject) {
        int position = mObjectsList.size() - 1;
        storeObject(newObject, position);
    }

    /**
     * Inserts the object at the given position
     *
     * @param newObject object to store
     * @param index     position where the object will be inserted
     */
    @Override
    public void storeObject(@NonNull ObjectType newObject, int index) {
        mObjectsList.put(index, newObject);
    }

    /**
     * Checks if the provided {@code ObjectType} is stored
     *
     * @param objectToSearch the object that will be searched
     * @return <code>boolean</code> matching 'true' if found, else 'false'
     * @see SparseArray#indexOfValue(Object)
     */
    @Override
    public boolean isObjectStored(@NonNull ObjectType objectToSearch) {
        return getIndexForObject(objectToSearch) >= 0;
    }

    /**
     * Removes the object that is stored at the specified index
     *
     * @param index position where the object will be removed
     */
    @Override
    public void removeObjectAtIndex(int index) {
        if (index >= 0)
            mObjectsList.removeAt(index);
    }

    /**
     * Removes the provided object if exists
     *
     * @param objectToRemove the object that will be deleted from the <code>SparseArray</code>
     * @see #removeObjectAtIndex(int)
     * @see #getIndexForObject(Object)
     */
    @Override
    public void removeObjectStored(@NonNull ObjectType objectToRemove) {
        removeObjectAtIndex(getIndexForObject(objectToRemove));
    }

    /**
     * Returns an iterator over elements of type {@code ObjectType}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<ObjectType> iterator() {
        return new ObjectIterator();
    }

    /**
     * Gets the number of elements stored starting at one
     *
     * @return {@code int} with the number of elements
     */
    @Override
    public int getObjectCountFromOne() {
        return mObjectsList.size();
    }

    /**
     * Gets the number of elements stored starting at zero
     *
     * @return {@code int} with the number of elements
     * @see #getObjectCountFromOne()
     */
    @Override
    public int getObjectCountFromZero() {
        return getObjectCountFromOne() - 1;
    }

    /**
     * Clears all the objects
     */
    @Override
    public void removeAllObjects() {
        mObjectsList.clear();
    }

    /**
     * Obtains the index of a given value
     *
     * @param object object to obtain the index from
     * @return <code>int</code> with the index - if not found, it will be negative
     */
    @Override
    public int getIndexForObject(@NonNull ObjectType object) {
        return mObjectsList.indexOfValue(object);
    }

    /**
     * Determines whether there is any object stored
     *
     * @return 'true' if there is almost one, else 'false'
     */
    @Override
    public boolean isAnyObjectStored() {
        return mObjectsList.size() > 0;
    }

    /**
     * Uses {@link SparseArray#toString()} custom implementation
     *
     * @return <code>String</code> with the result
     * @see SparseArray#toString()
     */
    @NotNull
    @Override
    public String toString() {
        return mObjectsList.toString();
    }

    /**
     * Custom class for making <code>ObjectContainer</code> iterable and applicable for "foreach"
     */
    private class ObjectIterator implements Iterator<ObjectType> {
        private int mCurrentPosition = -1;
        private boolean mHasBeenNextCalled = false;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return mCurrentPosition >= mObjectsList.size();
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public synchronized ObjectType next() {
            ++mCurrentPosition;
            mHasBeenNextCalled = true;
            return mObjectsList.get(mCurrentPosition, null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public synchronized void remove() {
            if (!mHasBeenNextCalled)
                throw new IllegalStateException("You must first call \"next()\" for using this " +
                        "function");
            mObjectsList.removeAt(mCurrentPosition);
            mHasBeenNextCalled = false;
        }
    }
}
