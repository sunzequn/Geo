package com.sunzequn.geocities.data.crawler.simple.wrapper;


import com.sunzequn.geocities.data.exception.QueueException;

import java.util.LinkedList;

/**
 * Created by Sloriac on 15/12/20.
 * <p>
 * The wrapper for <code>LinkedList</code>, named <code>LimitedQueue</code>.
 */
public class LimitedQueue<T> {

    private int maxNumber;

    /**
     * Use <code>LinkedList</code> to simulate <code>LimitedQueue</code>.
     */
    private LinkedList<T> queue = new LinkedList<T>();

    /**
     * Constructor for <code>LimitedQueue</code> without parameters.
     */
    public LimitedQueue() {
    }

    /**
     * Default Constructor for <code>LimitedQueue</code> that takes <code>maxNumber</code> to use.
     * Methods always use this constructor to initialize <code>LimitedQueue</code>.
     *
     * @param maxNumber the maximum number of objects in the <code>LimitedQueue</code>.
     */
    public LimitedQueue(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    /**
     * Add an element to the tail of <code>LimitedQueue</code>.
     *
     * @param t element to be added to this <code>LimitedQueue</code>
     */
    public void enQueue(T t) {
        try {
            if (queue.size() < maxNumber) {
                if (!contains(t)) {
                    queue.addLast(t);
                } else {
                    System.out.println("The queue already contains the element: " + t);
                }
            } else {
                throw new QueueException("The size of the queue comes to it`s maximum.");
            }
        } catch (QueueException e) {
            e.printStackTrace();
        }

    }

    /**
     * Get the first element of <code>LimitedQueue</code>.
     *
     * @return the first element.
     */
    public T deQueue() {
        try {
            if (!queue.isEmpty()) {
                return queue.removeFirst();
            } else {
                throw new QueueException("The queue is empty, has no object to pop.");
            }
        } catch (QueueException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * Determine whether the <code>LimitedQueue</code> is empty.
     *
     * @return true if the <code>LimitedQueue</code> is empty otherwise false.
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Determine whether the <code>LimitedQueue</code> contains the object.
     *
     * @param t The object you want to determine whether the <code>LimitedQueue</code> contains.
     * @return true if the <code>LimitedQueue</code> contains it otherwise false.
     */
    public boolean contains(T t) {
        return queue.contains(t);
    }

    /**
     * Get the size of <code>LimitedQueue</code>
     *
     * @return the size
     */
    public int size() {
        return queue.size();
    }

    /**
     * Determine whether it can continue to handle <code>LimitedQueue</code>.
     *
     * @return true if it can continue otherwise false
     */
    public boolean ifContinue() {
        return (!isEmpty() && size() <= maxNumber);
    }

}
