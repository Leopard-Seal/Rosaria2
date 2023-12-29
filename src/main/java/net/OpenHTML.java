package net;

import util.Security;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.net.URL;

public final class OpenHTML {

    private String htmlContent;

    public OpenHTML(String url) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }
        this.htmlContent = fetchHtmlContent(url);
    }

    private String fetchHtmlContent(String url) {
        if (Security.isUrlSafe(url) && Security.verifySSLCertificate(url)) {
            try {
                URI uri = new URI(url);
                URL urlObj = uri.toURL();
                this.htmlContent = Crawler.crawl(urlObj);
                return this.htmlContent;
            } catch (Exception e) {
                throw new RuntimeException("Error fetching HTML content", e);
            }
        } else {
            throw new IllegalArgumentException("URL is not safe or SSL verification failed");
        }
    }

    public Document getDocument() {
        return Jsoup.parse(this.htmlContent);
    }

    public String getHtml() {
        return getDocument().html();
    }


    @Override
    public String toString() {
        return this.htmlContent;
    }
}

