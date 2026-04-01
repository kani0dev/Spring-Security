package kani.springsecurity.Infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
/*
    @Value("${https://api.openai.com/v1/models}")
    private String ia_api;

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl(ia_api).build();
    }
*/
}

