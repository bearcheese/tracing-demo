package hu.bearmaster.tracing_demo.connector;

import hu.bearmaster.tracing_demo.model.GreetingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class ApiConnector {

    private static final Logger LOG = LoggerFactory.getLogger(ApiConnector.class);

    private final WebClient webClient;

    public ApiConnector(WebClient.Builder webClientBuilder, @Value("${downstream.uri}") String downstreamUri) {
        this.webClient = !downstreamUri.isBlank()
                ? webClientBuilder
                    .baseUrl("http://" + downstreamUri + "/api")
                    .build()
                : null;
    }

    public Optional<GreetingResponse> callDownstream() {
        if (webClient != null) {
            LOG.info("Calling downstream service");
            return webClient.get()
                    .uri("/greetings")
                    .retrieve()
                    .bodyToMono(GreetingResponse.class)
                    .map(Optional::ofNullable)
                    .block();
        }
        LOG.info("No downstream service available");
        return Optional.empty();
    }

}
