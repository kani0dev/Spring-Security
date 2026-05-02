package kani.springsecurity.Domain.Profile;

import kani.springsecurity.Application.Controller.Request.ProfileRequest;
import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import kani.springsecurity.Application.Events.SendSavedProfileToEmbedding;
import kani.springsecurity.Application.Exceptions.AlreadyExist;
import kani.springsecurity.Application.Exceptions.EmptyProfile;
import kani.springsecurity.Domain.Tags.Tag;
import kani.springsecurity.Domain.Tags.TagRepository;
import kani.springsecurity.Domain.Tags.TagService;
import kani.springsecurity.Domain.Users.UserService;
import kani.springsecurity.Domain.Users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileRepository repo;

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
                        .userId(id)
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
    public void deleteProfile(Long id)  {
        try{
            findById(id);
            repo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Transactional
    public Profile saveProfile(Profile profile) {
        if (repo.findById(profile.getUserId()).isPresent()) {
            throw new AlreadyExist("Profile already exists");
        }
        if (profile.isEmpty(profile)) {
            throw new EmptyProfile("can't save a empty profile");
        }
        Profile saved = repo.save(profile);
        return saved;
    }
}
