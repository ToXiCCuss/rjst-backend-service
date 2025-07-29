package de.rjst.bes.container;


import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import lombok.RequiredArgsConstructor;
import org.mockserver.client.MockServerClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Profile("container")
@Component
public class IpServiceMock {

    private static final String URL = "/{ip}";

    private final MockServerClient mockServerClient;

    public void getIpQueryResponse(final String ip) {
        final var requestDefinition = request()
            .withMethod(HttpMethod.GET.name())
            .withPath(URL)
            .withPathParameter("ip", ip);
        mockServerClient
            .clear(requestDefinition)
            .when(requestDefinition)
            .respond(
                response()
                    .withStatusCode(HttpStatus.OK.value())
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .withBody(TestContainerUtil.getResponse()
                    )
            );
    }

}
