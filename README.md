# Tracing demo

### Build image
```shell
docker build -t tracing-demo .
```

### Start applications
```shell
docker compose up
```

### Execute test
```shell
curl http://localhost:8080/api/greetings
```

### Inspect tracing results
The greetings test response should contain a header called `X-Trace-Id`, 
its value should be used to track the request in Zipkin. Open http://localhost:9411/zipkin/ 
in a browser and enter the trace id at the top right `Search by trace ID`
input box.

Additionally, the individual container logs should be observed for trace id
propagation (recommended to use Docker desktop, since docker compose outputs
all console logs into one window).

### How this demo works?
Docker compose starts 4 separate containers: 3 instances of a Spring boot
application, exposing a singular `/api/greetings` endpoint, and a Zipkin
instance, where the Spring apps reporting their traces (with 100% sample 
probability).

The Spring boot apps are named `layer1`, `layer2` and `layer3`. When 
`layer1` application receives a request, logs it and calls `layer2` 
applications same endpoint with a WebClient, which in order will call 
`layer3`, where the chain ends. 

The WebClient instances are configured with a wiretap enabled http client
(see `TracingDemoApplication.webClientCustomizer()` bean).

The application has a configured `HttpExchangeRepository` instance as well 
(see `LoggingHttpExchangeRepository` class), which logs the request and response
details after each request is processed and committed by Spring MVC. Because of this
the log entries are always after the fact, closest to the end of the logs.

Spring logging is configured to include application name, `traceId` and `spanId` values in the logs 
messages, e.g. `[layer2,66f070931a1dfd200a5a82b1c6c1a37b,c08eb0f1774f2f81]`. I did not
manage to figure out if the parent span ID value can be logged.

Spring tracing does not include the trace id into the response by default, so there
is an explicit filter `TraceIdObservationFilter` added to the context, which provides
the `X-Trace-Id` header for every response. This does not change the default tracing 
behaviour by any means, Micrometer and Brave is only looking for the `traceparent` header
out of the box.

