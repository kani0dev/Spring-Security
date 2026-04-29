package kani.springsecurity.Application.Controller;

import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import kani.springsecurity.Domain.Embeding.Embeding;
import kani.springsecurity.Domain.Embeding.EmbedingRepository;
import kani.springsecurity.Domain.Embeding.EmbedingService;
import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.data.metrics.DefaultRepositoryTagsProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/embed")
@RequiredArgsConstructor
public class EmbediControler {
    private final ProfileService PfService;
    private final EmbedingService service;
    private final EmbedingRepository repo;

    @PostMapping("/{id}")
    public Mono<ResponseEntity<Embeding>> getProfileEmbeding(@PathVariable Long id ) throws Exception {
        Profile byId = PfService.findById(id);
        ProfileResponse profile = ProfileResponse.ToResponse(byId);

         return service.getEmbeding(id, profile)
                 .map(response -> Embeding.builder()
                         .id(response.user_id())
                         .user_id(response.user_id())
                         .embedding(response.embedding())
                         .build()
                 ).flatMap(embeding ->  Mono.fromCallable(() -> (repo.save(embeding))
                         )
                 ).subscribeOn(Schedulers.boundedElastic())
                 .map(ResponseEntity::ok);
    }
    @GetMapping()
    public ResponseEntity<?> findAllEmbedingsFromDB(){
        return ResponseEntity.ok(
                service.getAll()
        );
    }

}


