package kani.springsecurity.Domain.Embeding;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmbedingService {
    private final WebClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();



    public Mono<?> getEmbeding(Long id, ProfileResponse profile) {
             var request = client.post()
                .uri("/" + id)
                     .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(profile)
                .retrieve()
                .bodyToMono(String.class);
            return request;
    }

}
