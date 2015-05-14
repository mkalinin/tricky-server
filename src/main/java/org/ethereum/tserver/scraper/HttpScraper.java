package org.ethereum.tserver.scraper;

import org.apache.http.client.fluent.Request;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
public class HttpScraper {

    private static final String DEFAULT_SOURCE = "http://cnn.com"; //TODO move to .properties
    private static final HttpScraper INSTANCE = new HttpScraper();

    private String source;

    private HttpScraper() {
        this.source = DEFAULT_SOURCE;
    }

    public static HttpScraper getInstance() {
        return INSTANCE;
    }

    public synchronized String getSource() {
        return source;
    }

    public synchronized void setSource(String source) {
        this.source = source;
    }

    public synchronized String getContent() {
        try {
            return Request.Get(source).execute().returnContent().asString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
