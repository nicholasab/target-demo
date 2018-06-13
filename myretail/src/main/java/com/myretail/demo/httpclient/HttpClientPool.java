package com.myretail.demo.httpclient;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.myretail.demo.exception.HttpClientException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * ApacheHttpClient pool to manage outbound http connections
 */
public class HttpClientPool {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static PoolingHttpClientConnectionManager cm;
    public static final HttpClientPool INSTANCE = new HttpClientPool();

    /**
     * Configure default maximum connections and maximum per route
     */
    private HttpClientPool() {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(20);
        cm.setDefaultMaxPerRoute(20);
    }

    /**
     * @return A new instance of an HttpClient from the connection pool
     */
    private CloseableHttpClient getHttpClientInstance() {
        return HttpClients.custom().setConnectionManager(cm).build();
    }

    /**
     * Return a JsonObject from the requested URI and throw an exception on non 200 responses
     * @param uri URI of the requested resource
     * @return JsonObject returned by 200 responses
     */
    public @Nullable
    JsonObject requestURI(String uri) {
        log.debug("Requesting GET of uri: {}",uri);
        CloseableHttpClient client = INSTANCE.getHttpClientInstance();
        HttpGet request = new HttpGet(uri);
        JsonObject json = null;

        try (CloseableHttpResponse response = client.execute(request)) {
            int status = response.getStatusLine().getStatusCode();
            if (status != 200) {
                log.debug("Non 200 status returned from GET of uri: {}",uri);
                throw new HttpClientException(HttpStatus.valueOf(status), "Error retrieving product name information");
            }
            json = getJson(response.getEntity());
        } catch (IOException e) {
            log.debug("IOException caught from GET of uri: {}",uri);
            throw new HttpClientException(HttpStatus.valueOf(500), "Server error retrieving product name information");
        }
        return json;
    }

    /**
     * @param entity HttpEntity from an HttpClient response
     * @return JsonObject parsed from the response
     */
    private @Nullable
    JsonObject getJson(HttpEntity entity) {
        JsonObject json = null;
        try {
            String jsonString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            JsonParser parser = new JsonParser();
            json = parser.parse(jsonString).getAsJsonObject();
        } catch (JsonSyntaxException | IOException e) {
            log.debug("Error parsing JSON from http request");
            throw new HttpClientException(HttpStatus.valueOf(500), "Error parsing response JSON for product information");
        }
        return json;
    }
}
