package io.quarkiverse.wiremock.devservice;

import io.quarkus.runtime.annotations.Recorder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Recorder
public class RestClientAuthorityRecorder {

    public static final Map<String, String> REST_CLIENTS_AUTHORITIES = new HashMap<>();

    public void record(String className, String baseUrl) {
        REST_CLIENTS_AUTHORITIES.put(className, URI.create(baseUrl).getAuthority());
    }

    public static String getRestClientAuthority(String className) {
        return REST_CLIENTS_AUTHORITIES.get(className);
    }
}
