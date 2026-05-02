package kani.springsecurity.Domain.Embeding;

import kani.springsecurity.Application.Controller.Response.EmbedingResponse;
import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import kani.springsecurity.Application.Events.SendSavedProfileToEmbedding;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmbedingService {
    private final WebClient client;
    private final EmbedingRepository repo;

    @Async
    @EventListener
    public EmbedingResponse EmbedSavedProfile(SendSavedProfileToEmbedding profileToEmbedding) {
        System.out.println("perfil que vai ser embedado " +profileToEmbedding);
        var profile = profileToEmbedding.profileResponse();

        EmbedingResponse response = client.post()
                .uri("/" + profile.user_id())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(profile)
                .retrieve()
                .bodyToMono(EmbedingResponse.class)
                .block();

        assert response != null;
        repo.save(EmbedingResponse.ToEntity(response));
        return response;
    }

    public List<Embeding> getAll() {
    return repo.findAll();
    }


    public Mono<EmbedingResponse> getEmbeding(Long id, ProfileResponse profile) {
        return client.post()
                .uri("/" + profile.user_id())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(profile)
                .retrieve()
                .bodyToMono(EmbedingResponse.class);
    }

}
