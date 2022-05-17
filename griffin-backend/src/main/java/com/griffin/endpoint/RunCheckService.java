package com.griffin.endpoint;

import org.springframework.web.bind.annotation.GetMapping;

public class RunCheckService {

    @GetMapping("/runcheck")
    public void runCheck() {
        //TODO Maybe pass in User ID to fetch which repo corresponds to that user and check

    }
}
