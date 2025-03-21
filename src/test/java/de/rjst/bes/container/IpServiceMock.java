package de.rjst.bes.container;


import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@UtilityClass
public class IpServiceMock {

    private final String URL = "/%s";

    public void getIpQueryResponse(final String ip) {
        final var client = JUnitContainersConfiguration.getMockServerClient();
        client.when(
                request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath(URL.formatted(ip))
        ).respond(
                response()
                        .withStatusCode(HttpStatus.OK.value())
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(TestContainerUtil.getResponse()
                        ));
    }

}
