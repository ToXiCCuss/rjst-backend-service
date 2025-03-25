package de.rjst.bes.controller;

import de.rjst.bes.adapter.FinanceBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("batch")
public class BatchController {

    private final FinanceBatchService financeBatchService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("triggerBatch")
    public ResponseEntity<Void> triggerBatch() {
        financeBatchService.triggerComdirectJob();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
