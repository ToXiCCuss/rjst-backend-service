package de.rjst.bes.container;

import org.springframework.lang.NonNull;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

public class RestDocsUtil {

    @NonNull
    public static  RestDocumentationFilter getDocument() {
        final var stackTrace = Thread.currentThread().getStackTrace();
        final var callerMethodName = stackTrace[2].getMethodName();
        return document(callerMethodName,
                preprocessRequest(modifyUris()
                        .scheme("https")
                        .host("rjst-backend-service.rjst.de")
                        .removePort()));
    }

}
