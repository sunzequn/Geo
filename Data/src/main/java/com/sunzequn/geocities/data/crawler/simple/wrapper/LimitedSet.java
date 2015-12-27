package com.sunzequn.geocities.data.crawler.simple.wrapper;

import com.sunzequn.geocities.data.exception.QueueException;

import java.util.HashSet;

/**
 * Created by Sloriac on 15/12/20.
 * <p>
 * A subclass of <code>HashSet</code>, named <code>LimitedSet</code>.
 * This is designed for controlling the size of the set.
 */
public class LimitedSet<T> extends HashSet<T> {

    /**
     * The maximum size of <code>LimitedSet</code>.
     */
    private int maxNumber;

    /**
     * Constructor for <code>LimitedSet</code> without parameters.
     */
    public LimitedSet() {
        super();
    }

    /**
     * Default Constructor for <code>LimitedSet</code> that takes <code>LimitedSet</code> to use.
     * Methods always use this constructor to initialize <code>LimitedQueue</code>.
     *
     * @param maxNumber
     */
    public LimitedSet(int maxNumber) {
        this.maxNumber = maxNumber;
    }

    /**
     * Override the method in order to control the size of <code>LimitedSet</code>.
     *
     * @param t element to be added to this set
     * @return true if this set did not already contain the specified element
     */
    @Override
    public boolean add(T t) {
        try {
            if (super.size() < maxNumber) {
                return super.add(t);
            } else {
                throw new QueueException("The size of the set comes to it`s maximum.");
            }
        } catch (QueueException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get the size of <code>LimitedSet</code>
     *
     * @return the size
     */
    public int size() {
        return super.size();
    }

    /**
     * Determine whether it can continue to handle <code>LimitedSet</code>.
     *
     * @return true if it can continue otherwise false
     */
    public boolean ifContinue() {
        return (size() <= maxNumber);
    }
}
