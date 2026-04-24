package kani.springsecurity.Domain.Profile;

import kani.springsecurity.Application.Controller.Request.ProfileRequest;
import kani.springsecurity.Domain.Tags.Tag;
import kani.springsecurity.Domain.Tags.TagRepository;
import kani.springsecurity.Domain.Tags.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository repo;
    private  final TagService tagService;

    public Profile findById(long id) throws Exception {
        Optional<Profile> byId = repo.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new Exception("user id profile not found");
    }

    @Transactional
    public Profile alterProfile(Long id, ProfileRequest request) throws Exception {
        try {
            Optional<Profile> byId = repo.findById(id);
            if (byId.isPresent()){
                Profile altered = Profile.builder()
                        .user(byId.get().getUser())
                        .userId(request.userId())
                        .bio(request.bio())
                        .location(request.location())
                        .ocupation(request.occupation())
                        .interests(request.interests())
                        .build();
                return repo.save(altered);
            }
           throw new Exception("usuario não encontrado");
            //eu nao sei se vai ativar o catch de baixo nao entendi mt bem como funciona expections tenho que dar uma estudada melhor
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteProfile(Long id) throws Exception {
        try{
            findById(id);
            repo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
