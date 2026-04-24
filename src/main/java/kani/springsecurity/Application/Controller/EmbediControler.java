package kani.springsecurity.Application.Controller;

import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import kani.springsecurity.Domain.Embeding.EmbedingService;
import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Profile.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/embed")
@RequiredArgsConstructor
public class EmbediControler {
    private final ProfileService PfService;
    private final EmbedingService service;

    @PostMapping("/{id}")
    public Mono<ResponseEntity<?>> getProfileEmbeding(@PathVariable Long id ) throws Exception {
        Profile byId = PfService.findById(id);
        ProfileResponse profile = ProfileResponse.ToResponse(byId);

        return service.getEmbeding(id, profile).map(ResponseEntity::ok);
    }

}
