package hu.bearmaster.tracing_demo.filter;

import brave.Span;
import brave.Tracer;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TraceIdObservationFilter extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(TraceIdObservationFilter.class);

	private final Tracer tracer;

	public TraceIdObservationFilter(Tracer tracer) {
		this.tracer = tracer;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
									FilterChain filterChain) throws ServletException, IOException {
		Span currentSpan = tracer.currentSpan();
		if (currentSpan != null) {
			String traceId = currentSpan.context().traceIdString();
			response.setHeader("X-Trace-Id", traceId);
		} else {
			LOG.info("Current span is null");
		}
		filterChain.doFilter(request, response);
	}
}