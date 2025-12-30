package com.project.todoappbackend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RateLimitingFilter implements Filter {

    private static final int MAX_REQUESTS_PER_MINUTE = 40;
    private final Map<String, AtomicInteger> requestsCounts = new ConcurrentHashMap<>();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String clientIP = httpRequest.getRemoteAddr();
        requestsCounts.putIfAbsent(clientIP, new AtomicInteger(0));
        int currentCount = requestsCounts.get(clientIP).incrementAndGet();

        if (currentCount > MAX_REQUESTS_PER_MINUTE) {
            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            httpResponse.getWriter().write("Too many requests. Please try again later.");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
