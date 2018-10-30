package javinator9889.securepass.util.threading.thread;

import androidx.annotation.NonNull;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;

/**
 * Copyright © 2018 - present | APP by Javinator9889
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see https://www.gnu.org/licenses/.
 *
 * Created by Javinator9889 on 30/10/2018 - APP.
 */
public abstract class NotifyingThread extends Thread implements Runnable {
    private GeneralObjectContainer<ThreadCompleteListener> mSubscribedClasses =
            new ObjectContainer<>();

    public final void addListener(@NonNull ThreadCompleteListener listener) {
        mSubscribedClasses.storeObject(listener);
    }

    private void notifyListeners(final Thread thread) {
        for (ThreadCompleteListener listener : mSubscribedClasses)
            listener.onThreadCompleteListener(thread);
    }

    @Override
    public void run() {
        try {
            doRun();
        } finally {
            notifyListeners(this);
        }
    }

    public abstract void doRun();
}
