package hu.bearmaster.tracing_demo.controller;

import hu.bearmaster.tracing_demo.connector.ApiConnector;
import hu.bearmaster.tracing_demo.model.GreetingResponse;
import hu.bearmaster.tracing_demo.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/api")
public class ApiController {
    private static final Logger LOG = LoggerFactory.getLogger(ApiConnector.class);

    private final ApiService service;

    private final String appName;

    public ApiController(ApiService service, @Value("${spring.application.name}") String appName) {
        this.service = service;
        this.appName = appName;
    }

    @GetMapping("/greetings")
    public GreetingResponse greetings() {
        LOG.info("Received greeting request, processing");
        service.processResponse();
        Random random = new Random();
        try {
            LOG.info("Sleeping for random interval");
            Thread.sleep(random.nextInt(100) + 50);
        } catch (InterruptedException e) {
            LOG.error("Sleep interrupted!");
        }
        GreetingResponse response = new GreetingResponse("Greeting from " + appName);
        LOG.info("Returning response: {}", response);
        return response;
    }

}
