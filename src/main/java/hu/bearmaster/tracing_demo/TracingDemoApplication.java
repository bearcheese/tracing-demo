package hu.bearmaster.tracing_demo;

import io.netty.handler.logging.LogLevel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@SpringBootApplication
public class TracingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TracingDemoApplication.class, args);
	}

	@Bean
	public WebClientCustomizer webClientCustomizer() {
		HttpClient httpClient = HttpClient.create()
				.wiretap(this.getClass().getCanonicalName(), LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
		return builder -> builder.clientConnector(new ReactorClientHttpConnector(httpClient));
	}

}
