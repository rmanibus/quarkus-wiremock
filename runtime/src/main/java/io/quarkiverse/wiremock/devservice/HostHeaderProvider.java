package io.quarkiverse.wiremock.devservice;

import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.client.impl.ClientRequestContextImpl;
import org.jboss.resteasy.reactive.client.spi.ResteasyReactiveClientRequestContext;
import org.jboss.resteasy.reactive.client.spi.ResteasyReactiveClientRequestFilter;

@Provider
public class HostHeaderProvider implements ResteasyReactiveClientRequestFilter {
    @Override
    public void filter(ResteasyReactiveClientRequestContext requestContext) {
        String authority = RestClientAuthorityRecorder.getRestClientAuthority(((ClientRequestContextImpl) requestContext)
                .getRestClientRequestContext().getInvokedMethod().getDeclaringClass().getName());
        if (authority != null) {
            requestContext.getHeaders().add("Host", authority);
        }
    }
}
