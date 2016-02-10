package com.sunzequn.geo.data.crawler.wrapper;

import com.sunzequn.geo.data.utils.PropertiesUtils;

/**
 * Created by Sloriac on 15/12/20.
 * <p>
 * The wrapper class for the queue of urls,
 * which contains two parts: unvisited urls and visited urls.
 * This class does not provide the access to specific implements of unvisited urls and visited urls.
 * It`s should be taken as a independent data structure.
 */
public class UrlQueue {

    /**
     * The Singleton instance of this class.
     */
    private static final UrlQueue instance = new UrlQueue();

    /**
     * The queue for unvisited urls.
     */
    private LimitedQueue<String> unvisitedUrls;

    /**
     * The collection for visited urls.
     * It has a good search performance to use <code>Set</code>.
     */
    private LimitedSet<String> visitedUrls;

    /**
     * The maximum number of unvisited urls.
     * It is initialized from the config file if defined.
     */
    private int maxVisitedUrlNumber = 10000;
    private int maxUnvisitedUrlNumber = 100;

    /**
     * Default constructor for <code>UrlQueue</code> with variable initializations.
     */
    private UrlQueue() {

        String number;
        number = PropertiesUtils.getValue("maxVisitedUrlNumber");
        if (number != null) {
            maxVisitedUrlNumber = Integer.parseInt(number);
        }
        number = PropertiesUtils.getValue("maxUnvisitedUrlNumber");
        if (number != null) {
            maxUnvisitedUrlNumber = Integer.parseInt(number);
        }
        unvisitedUrls = new LimitedQueue<>(maxUnvisitedUrlNumber);
        visitedUrls = new LimitedSet<>(maxVisitedUrlNumber);
    }

    /**
     * Returns the Singleton instance of this class.
     *
     * @return singleton instance
     */
    public static UrlQueue instance() {
        return instance;
    }

    /**
     * Add a url to the queue of unvisited urls.
     *
     * @param url element to be added to this queue
     */
    public void enUnvisitedUrlsQueue(String url) {
        unvisitedUrls.enQueue(url);
    }

    /**
     * Get the first element of the queue of unvisited urls.
     * This method also checks whether the url has already visited.
     *
     * @return the first element this queue
     */
    public String deUnvisitedUrlsQueue() {
        String url = unvisitedUrls.deQueue();
        while (visitedUrls.contains(url)) {
            url = unvisitedUrls.deQueue();
        }
        return url;
    }

    /**
     * Add the visited url to a set.
     *
     * @param url element to be added to this set
     */
    public void markVisited(String url) {
        visitedUrls.add(url);
    }

    /**
     * Get the number of unvisited urls.
     *
     * @return the number of unvisited urls
     */
    public int unVisitedSize() {
        return unvisitedUrls.size();
    }

    /**
     * Get the number of visited urls.
     *
     * @return number of visited urls
     */
    public int visitedSize() {
        return visitedUrls.size();
    }

    /**
     * Determine whether it can continue to parse urls.
     *
     * @return true if it can continue otherwise false
     */
    public boolean ifContinue() {
        return (unvisitedUrls.ifContinue() && visitedUrls.ifContinue());
    }

}
