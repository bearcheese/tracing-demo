package hu.bearmaster.tracing_demo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.boot.actuate.web.exchanges.HttpExchangeRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LoggingHttpExchangeRepository implements HttpExchangeRepository {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingHttpExchangeRepository.class);

    @Override
    public List<HttpExchange> findAll() {
        return List.of();
    }

    @Override
    public void add(HttpExchange httpExchange) {
        HttpExchange.Request request = httpExchange.getRequest();
        LOG.debug("Request {} {}", request.getMethod(), request.getUri());
        for (Map.Entry<String, List<String>> entry : request.getHeaders().entrySet()) {
            LOG.debug("Request header {} {}", entry.getKey(), entry.getValue());
        }
        HttpExchange.Response response = httpExchange.getResponse();
        LOG.debug("Response status {}", response.getStatus());
        for (Map.Entry<String, List<String>> entry : response.getHeaders().entrySet()) {
            LOG.debug("Response header {} {}", entry.getKey(), entry.getValue());
        }
    }
}
