package com.griffin.insightsapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InsightsController {

    @GetMapping("/basicinfo")
    public String getSomething() {
        return "Something";
    }
}
