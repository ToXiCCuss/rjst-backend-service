package de.rjst.rjstbackendservice.controller;

import de.rjst.rjstbackendservice.database.LockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/advisory")
public class AdvisoryLockController {

    private final LockRepository lockRepository;

    @PostMapping("/lock")
    public Boolean lock() {
        return lockRepository.tryAdvisoryLock();
    }

    @PostMapping("/unlock")
    public void unlock() {
        lockRepository.releaseAdvisoryLock();
    }


}
