package kani.springsecurity.Application.Controller;

import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import kani.springsecurity.Domain.Embeding.Embeding;
import kani.springsecurity.Domain.Embeding.EmbedingRepository;
import kani.springsecurity.Domain.Embeding.EmbedingService;
import kani.springsecurity.Domain.Profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/embed")
@RequiredArgsConstructor
public class EmbediControler {
    private final ProfileService PfService;
    private final EmbedingService service;
    private final EmbedingRepository repo;

    /*
    -------- when profile is save, a event is sent to the embedding model HOWEVER if the embeding fail
    when creating a profile, this endpoint serv as a manual retrial for the users
    */
    @PostMapping("/{id}")
    public Mono<ResponseEntity<Embeding>> getProfileEmbeding(@PathVariable Long id ) throws Exception {
        ProfileResponse profile  = ProfileResponse.ToResponse(PfService.findById(id));

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


