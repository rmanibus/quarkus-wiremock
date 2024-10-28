package io.quarkiverse.wiremock.devservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.ext.Provider;

import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.client.impl.ClientRequestContextImpl;
import org.jboss.resteasy.reactive.client.spi.ResteasyReactiveClientRequestContext;
import org.jboss.resteasy.reactive.client.spi.ResteasyReactiveClientRequestFilter;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusUnitTest;

import java.net.URI;

class WireMockProxyRestClientTest {

    private static final String APP_PROPERTIES = "application.properties";

    @RegisterExtension
    static final QuarkusUnitTest UNIT_TEST = new QuarkusUnitTest().withConfigurationResource(APP_PROPERTIES)
            .setArchiveProducer(
                    () -> ShrinkWrap.create(JavaArchive.class).addClasses(QuarkusClient.class));

    @Inject
    @RestClient
    QuarkusClient quarkusClient;

    @Test
    void testWireMockMappingsFolder() {

        QuarkusClient.User result = quarkusClient.test();
        assertEquals("John Doe", result.name());
    }

    @Path("/mock-rest-client")
    @RegisterRestClient(baseUri = "https://imaginary-host.io/", configKey = "quarkus-client")
    @RegisterProvider(HostHeaderProvider.class) // this should be done automatically !
    public interface QuarkusClient {

        record User(String name, String email) {
        }

        @GET
        @Consumes("application/json")
        User test();
    }

}
