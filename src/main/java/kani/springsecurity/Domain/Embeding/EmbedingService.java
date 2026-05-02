package kani.springsecurity.Domain.Embeding;

import kani.springsecurity.Application.Controller.Response.EmbedingResponse;
import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import kani.springsecurity.Application.Events.SendSavedProfileToEmbedding;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
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
    public void EmbedSavedProfile(SendSavedProfileToEmbedding profileToEmbedding) {
        var profile = profileToEmbedding.profileResponse();

        EmbedingResponse response = client.post()
                .uri("/" + profile.user_id())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(profile)
                .retrieve()
                .bodyToMono(EmbedingResponse.class)
                .block();

        assert response != null;
        Embeding embeding = Embeding.builder()
                .user_id(response.user_id())
                .embedding(response.embedding())
                .build();

        repo.save(embeding);
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
