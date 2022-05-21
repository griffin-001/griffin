package com.griffin.insightsapi;

import com.griffin.insightsdb.model.Dependency;
import com.griffin.insightsdb.service.InsightDBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InsightsController {

    private final Logger logger = LoggerFactory.getLogger(InsightsController.class);
    private final InsightDBService insightDBService;

    public InsightsController(InsightDBService insightDBService) {
        this.insightDBService = insightDBService;
    }


    @GetMapping("/basicinfo")
    public String getSomething() {
        return "Something";
    }

    @GetMapping("/allDeps")
    public String allDeps() {
        logger.info("Getting all deps...");
        List<Dependency> dependensies = insightDBService.findAllDependencies();

        return dependensies.toString();
    }

}
