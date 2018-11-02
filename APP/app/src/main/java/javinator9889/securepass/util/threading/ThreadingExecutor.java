package javinator9889.securepass.util.threading;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import androidx.annotation.NonNull;
import javinator9889.securepass.SecurePass;
import javinator9889.securepass.objects.GeneralObjectContainer;
import javinator9889.securepass.objects.ObjectContainer;
import javinator9889.securepass.util.threading.thread.NotifyingThread;
import javinator9889.securepass.util.threading.thread.ThreadCompleteListener;

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
public class ThreadingExecutor implements ThreadCompleteListener, Thread.UncaughtExceptionHandler {
    private volatile GeneralObjectContainer<ThreadExceptionListener> mSubscribedClasses;
    private volatile GeneralObjectContainer<Thread> mInExecutionThreads;
    private volatile LinkedList<NotifyingThread> mThreads;
    private volatile AtomicInteger mActiveThreads;
    private volatile AtomicInteger mMaxActiveThreads;
    private volatile AtomicBoolean mShouldContinueExecuting;

    /**
     * Default constructor only visible for {@link Builder}
     */
    private ThreadingExecutor() {}

    /**
     * Obtains the {@link Builder} for generating a {@code ThreadingExecutor} instance
     *
     * @return {@code Builder} instance
     */
    public static Builder Builder() {
        return new Builder(new ThreadingExecutor());
    }

    /**
     * Sets the active threads
     *
     * @param activeThreads active threads - default: 0
     */
    private void setActiveThreads(int activeThreads) {
        mActiveThreads = new AtomicInteger(activeThreads);
    }

    /**
     * Sets the maximum active threads
     *
     * @param maxActiveThreads maximum active threads - default: number of processor / 2
     */
    private void setMaxActiveThreads(int maxActiveThreads) {
        mMaxActiveThreads = new AtomicInteger(maxActiveThreads);
        mInExecutionThreads = new ObjectContainer<>(maxActiveThreads);
    }

    /**
     * Sets the subscribed classes that implements {@link ThreadExceptionListener}
     *
     * @param subscribedClasses subscribed classes
     */
    private void setSubscribedClasses(GeneralObjectContainer<ThreadExceptionListener>
                                              subscribedClasses) {
        mSubscribedClasses = subscribedClasses;
    }

    /**
     * Sets user active threads
     *
     * @param threads threads to execute
     */
    private void setThreads(LinkedList<NotifyingThread> threads) {
        mThreads = threads;
    }

    /**
     * When an exception occurs, this param determines if the execution of the pending
     * threads must stop (by interrupting them)
     *
     * @param shouldContinueExecuting {@code true} when the threads can be executed normally, {@code
     *                                false} when they must stop - default: true
     */
    private void setShouldContinueExecuting(boolean shouldContinueExecuting) {
        mShouldContinueExecuting = new AtomicBoolean(shouldContinueExecuting);
    }

    /**
     * Adds a new thread after creating the {@code ThreadingExecutor} instance
     *
     * @param newThread new thread to execute
     */
    public void add(@NonNull NotifyingThread newThread) {
        mThreads.add(newThread);
    }

    /**
     * Updates the maximum active threads that can be running at the same time
     *
     * @param newValue new max active threads value
     * @throws IllegalArgumentException when {@code maxActiveThreads} is lower than 1
     */
    public void updateMaxActiveThreads(int newValue) {
        if (newValue <= 0)
            throw new IllegalArgumentException("Maximum Active Threads cannot be lower than 1");
        mMaxActiveThreads.set(newValue);
    }

    /**
     * Updates the policy when a exception occurs so the pending threads finish their execution
     * or they must be interrupted
     *
     * @param shouldContinueExecuting {@code true} when the threads can be executed normally, {@code
     *                                false} when they must stop - default: true
     */
    public void updateExecutionPolicyOnException(boolean shouldContinueExecuting) {
        mShouldContinueExecuting.set(shouldContinueExecuting);
    }

    /**
     * Prepares pending threads for executing and running at the correspondent addition order.
     *
     * <p>
     * Iterates starting at {@link #mActiveThreads current active threads} until the
     * {@link #mMaxActiveThreads maximum active threads}.
     *
     * <ul>
     * <li>
     * Obtains the {@link LinkedList#remove() head of the queue}.
     * </li>
     * <li>
     * Adds the head of the queue to the
     * {@link GeneralObjectContainer#storeObject(Object) in execution threads} list.
     * </li>
     * <li>
     * Starts the retrieved thread running in the background and updates the
     * {@link #mActiveThreads count of active threads}.
     * </li>
     * </ul>
     * </p>
     */
    private void doPlanning() {
        for (int i = mActiveThreads.get(); i < mMaxActiveThreads.get(); ++i) {
            try {
                NotifyingThread thread = mThreads.remove();
                mInExecutionThreads.storeObject(thread);
                thread.run();
                mActiveThreads.incrementAndGet();
            } catch (NoSuchElementException e) {
                break;
            }
        }
    }

    /**
     * Method that will be called when a Thread has finished
     *
     * @param thread the thread that has finished
     */
    @Override
    public void onThreadCompleteListener(Thread thread) {
        mInExecutionThreads.removeObjectStored(thread);
        mActiveThreads.decrementAndGet();
        doPlanning();
    }

    /**
     * Method invoked when the given thread terminates due to the
     * given uncaught exception.
     * <p>Any exception thrown by this method will be ignored by the
     * Java Virtual Machine.
     *
     * @param t the thread
     * @param e the exception
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        for (ThreadExceptionListener listener : mSubscribedClasses) {
            listener.onThreadExceptionListener(e, t);
        }
        mInExecutionThreads.removeObjectStored(t);
        mActiveThreads.decrementAndGet();
        if (mShouldContinueExecuting.get())
            doPlanning();
        else {
            for (int i = mInExecutionThreads.getObjectCountFromZero(); i >= 0; --i) {
                NotifyingThread executingThread = (NotifyingThread) mInExecutionThreads
                        .getStoredObjectAtIndex(i);
                mThreads.addFirst(executingThread);
                mInExecutionThreads.removeObjectAtIndex(i);
                mActiveThreads.decrementAndGet();
                executingThread.interrupt();
            }
        }
    }

    /**
     * Starts the threading execution
     */
    public void run() {
        doPlanning();
    }

    /**
     * Checks if there is any pending thread waiting for being executed. For that purpose, it
     * checks if the {@link LinkedList#size() size of the queue} is higher than 0 (if it is zero
     * means that there is no pending thread at the queue)
     *
     * @return {@code boolean} {@code true} when there is at least one pending thread, else {@code
     * false}
     */
    public boolean isAnyPendingThread() {
        return mThreads.size() > 0;
    }

    /**
     * Generates a new ThreadingExecutor instance
     */
    public static final class Builder {
        private GeneralObjectContainer<ThreadExceptionListener> mSubscribedClasses;
        private LinkedList<NotifyingThread> mThreads;
        private ThreadingExecutor mExecutor;
        private int mActiveThreads;
        private int mMaxActiveThreads;
        private boolean mShouldContinueExecutingAfterException;

        /**
         * Private constructor only accessible by {@link ThreadingExecutor}
         */
        private Builder(@NonNull ThreadingExecutor executor) {
            mSubscribedClasses = new ObjectContainer<>(1);
            mThreads = new LinkedList<>();
            mExecutor = executor;
            mActiveThreads = 0;
            mMaxActiveThreads = SecurePass.getNumberOfProcessors() / 2;
            mShouldContinueExecutingAfterException = true;
        }

        /**
         * Sets thread exception listener for executing threads
         *
         * @param listener class that implements {@link ThreadExceptionListener}
         * @return {@code Builder} class (itself)
         */
        public Builder addOnThreadExceptionListener(@NonNull ThreadExceptionListener listener) {
            mSubscribedClasses.storeObject(listener);
            return this;
        }

        /**
         * Sets the maximum active threads that can be run simultaneously
         *
         * @param maxActiveThreads max active threads - by default it is the number of processors
         * @return {@code Builder} class (itself)
         * @throws IllegalArgumentException when {@code maxActiveThreads} is lower than 1
         */
        public Builder setMaximumActiveThreads(int maxActiveThreads) {
            if (maxActiveThreads <= 0)
                throw new IllegalArgumentException("Maximum Active Threads cannot be lower than 1");
            mMaxActiveThreads = maxActiveThreads;
            return this;
        }

        /**
         * When an exception occurs, this param determines if the execution of the pending
         * threads must stop (by interrupting them)
         *
         * @param shouldExecute {@code true} when the threads can be executed normally, {@code
         *                      false} when they must stop - default: true
         * @return {@code Builder} class (itself)
         */
        public Builder shouldExecuteOtherThreadsWhenExceptionOccurs(boolean shouldExecute) {
            mShouldContinueExecutingAfterException = shouldExecute;
            return this;
        }

        /**
         * Adds a new {@link NotifyingThread} to the executing threads
         *
         * @param newThread class that implements {@link NotifyingThread#doRun()} method
         * @return {@code Builder} class (itself)
         */
        public Builder add(@NonNull NotifyingThread newThread) {
            newThread.addListener(mExecutor);
            newThread.setUncaughtExceptionHandler(mExecutor);
            mThreads.add(newThread);
            return this;
        }

        /**
         * Generates the {@link ThreadingExecutor} instance
         *
         * @return {@code ThreadingExecutor} instance
         */
        public ThreadingExecutor build() {
            mExecutor.setActiveThreads(mActiveThreads);
            mExecutor.setMaxActiveThreads(mMaxActiveThreads);
            mExecutor.setShouldContinueExecuting(mShouldContinueExecutingAfterException);
            mExecutor.setSubscribedClasses(mSubscribedClasses);
            mExecutor.setThreads(mThreads);
            return mExecutor;
        }
    }
}
