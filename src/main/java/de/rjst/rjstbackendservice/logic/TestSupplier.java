package de.rjst.rjstbackendservice.logic;

import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service("testSupplier")
public class TestSupplier implements Supplier<String> {

    @Override
    public String get() {
        return "test";
    }
}
