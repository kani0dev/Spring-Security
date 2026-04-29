package kani.springsecurity.Domain.Embeding;

import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbedingService {
    private final WebClient client;
    private final EmbedingRepository repo;

    public Mono<EmbedingResponse> getEmbeding(Long id, ProfileResponse profile) {
        return client.post()
                .uri("/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(profile)
                .retrieve()
                .bodyToMono(EmbedingResponse.class);
    }

    public List<Embeding> getAll() {
    return repo.findAll();
    }
}
