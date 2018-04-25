package javinator9889.securepass.objects;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Javinator9889 on 25/04/2018.
 */
public class ObjectContainer<ObjectType> implements GeneralObjectContainer<ObjectType> {
    private List<ObjectType> mObjectsList;

    @SafeVarargs
    public ObjectContainer(@NonNull ObjectType... objects) {
        int initialCapacity = objects.length;
        mObjectsList = new ArrayList<>(initialCapacity);
        Collections.addAll(mObjectsList, objects);
    }

    @Override
    public ObjectType getStoredObjectAtIndex(int index) {
        return mObjectsList.get(index);
    }

    @Override
    public ObjectType getLatestStoredObject() {
        int size = mObjectsList.size() - 1;
        return mObjectsList.get(size);
    }

    @Override
    public void storeObject(@NonNull ObjectType newObject) {
        mObjectsList.add(newObject);
    }

    @Override
    public boolean isObjectStored(@NonNull ObjectType objectToSearch) {
        return mObjectsList.contains(objectToSearch);
    }

    @Override
    public void removeObjectAtIndex(int index) {
        mObjectsList.remove(index);
    }

    @Override
    public void removeObjectStored(@NonNull ObjectType objectToRemove) {
        mObjectsList.remove(objectToRemove);
    }

    @Override
    public Iterator<ObjectType> getStoredObjectIterator() {
        return mObjectsList.iterator();
    }

    @Override
    public int getObjectCountFromOne() {
        return mObjectsList.size();
    }

    @Override
    public int getObjectCountFromZero() {
        return mObjectsList.size() - 1;
    }

    @Override
    public void removeAllObjects() {
        mObjectsList.clear();
    }

    @Override
    public int getIndexForObject(@NonNull ObjectType object) {
        return mObjectsList.indexOf(object);
    }

    @Override
    public boolean isAnyObjectStored() {
        return !mObjectsList.isEmpty();
    }
}
