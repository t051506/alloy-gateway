package com.alloy.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

/**
 * @Author: tankechao
 * @Date: 2020/10/9 16:44
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebFilter corsFilter() {
        return (ctx, chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                HttpHeaders requestHeaders = request.getHeaders();
                ServerHttpResponse response = ctx.getResponse();
                HttpMethod requestMethod = requestHeaders.getAccessControlRequestMethod();
                HttpHeaders headers = response.getHeaders();
                headers.add("Access-Control-Allow-Origin", requestHeaders.getOrigin());
                headers.addAll("Access-Control-Allow-Headers", requestHeaders
                        .getAccessControlRequestHeaders());
                if (requestMethod != null) {
                    headers.add("Access-Control-Allow-Methods", requestMethod.name());
                }
                headers.add("Access-Control-Allow-Credentials", "true");
                headers.add("Access-Control-Expose-Headers", "*");
                headers.add("Access-Control-Max-Age", "18000L");
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }

            return chain.filter(ctx);
        };
    }

}
