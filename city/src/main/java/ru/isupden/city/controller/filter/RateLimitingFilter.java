package ru.isupden.city.controller.filter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.isupden.city.controller.dto.Error;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final Cache<String, ClientRequestInfo> requestCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.SECONDS)
            .build();

    private static final int MAX_REQUESTS = 50;
    private static final Duration TIME_WINDOW = Duration.ofSeconds(10);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var clientIp = request.getRemoteAddr();
        var now = Instant.now();

        var clientRequestInfo = requestCache.get(clientIp, key -> new ClientRequestInfo(now, new AtomicInteger(0)));

        if (Duration.between(clientRequestInfo.getFirstRequestTime(), now).compareTo(TIME_WINDOW) > 0) {
            clientRequestInfo.getRequestCount().set(1);
            clientRequestInfo.setFirstRequestTime(now);
        } else {
            clientRequestInfo.getRequestCount().incrementAndGet();
        }

        if (clientRequestInfo.getRequestCount().get() > MAX_REQUESTS) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType(MediaType.APPLICATION_XML.getType());
            try {
                var context = JAXBContext.newInstance(Error.class);
                var marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(
                        Error.newBuilder().setMessage("Rate limit exceeded. Try again later.").build(),
                        response.getWriter()
                );
            } catch (JAXBException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        filterChain.doFilter(request, response);
    }


    @Getter
    @Setter
    private static class ClientRequestInfo {
        private Instant firstRequestTime;
        private final AtomicInteger requestCount;

        public ClientRequestInfo(Instant firstRequestTime, AtomicInteger requestCount) {
            this.firstRequestTime = firstRequestTime;
            this.requestCount = requestCount;
        }

    }
}