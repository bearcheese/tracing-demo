package hu.bearmaster.tracing_demo.service;

import hu.bearmaster.tracing_demo.connector.ApiConnector;
import hu.bearmaster.tracing_demo.model.GreetingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ApiService {
    private static final Logger LOG = LoggerFactory.getLogger(ApiService.class);

    private final ApiConnector connector;

    public ApiService(ApiConnector connector) {
        this.connector = connector;
    }

    public void processResponse() {
        String message = connector.callDownstream()
                .map(GreetingResponse::getMessage)
                .orElse("n/a");
        LOG.info("Received response: {}", message);
    }

}
