package de.rjst.bes.adapter;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "finance")
public interface FinanceBatchService {

    @PostMapping("/jobs/comdirect")
    void triggerComdirectJob();

}
