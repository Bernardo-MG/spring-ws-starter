
package com.bernardomg.ws.test.springframework.request.config;

import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bernardomg.ws.springframework.request.SortingArgumentResolver;

@TestConfiguration
public class SortingTestConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(new SortingArgumentResolver());
            }
        };
    }

}
