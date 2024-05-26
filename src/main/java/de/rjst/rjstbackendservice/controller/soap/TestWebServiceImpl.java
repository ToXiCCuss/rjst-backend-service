package de.rjst.rjstbackendservice.controller.soap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class TestWebServiceImpl implements TestWebService {

    private final Supplier<String> testSupplier;

    @Override
    public String getHelloWorld(String name) {
        return testSupplier.get() + name;
    }
}
