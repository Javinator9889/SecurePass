package javinator9889.securepass.objects;

import androidx.annotation.NonNull;

import java.util.Iterator;

/**
 * Created by Javinator9889 on 25/04/2018.
 */
public interface GeneralObjectContainer<ObjectType> {
    ObjectType getStoredObjectAtIndex(int index);
    ObjectType getLatestStoredObject();
    void storeObject(@NonNull ObjectType newObject);
    void storeObject(@NonNull ObjectType newObject, int index);
    boolean isObjectStored(@NonNull ObjectType objectToSearch);
    void removeObjectAtIndex(int index);
    void removeObjectStored(@NonNull ObjectType objectToRemove);
    Iterator<ObjectType> getStoredObjectIterator();
    int getObjectCountFromOne();
    int getObjectCountFromZero();
    void removeAllObjects();
    int getIndexForObject(@NonNull ObjectType object);
    boolean isAnyObjectStored();
}
