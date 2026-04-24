package kani.springsecurity.Application.Controller.Request;

import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Tags.Tag;
import kani.springsecurity.Domain.Users.UserRepository;
import lombok.Builder;

import java.util.Set;


@Builder
public record ProfileRequest(
        Long userId,
        String bio,
        String location,
        String occupation,
        String interests,
        Set<Tag> tags
        ) {
    public static UserRepository repo ;

    public static Profile ToEntity(ProfileRequest request){
        return Profile.builder()
                .userId(request.userId())
                .user(repo.getById(request.userId()))
                .bio(request.bio())
                .location(request.location())
                .ocupation(request.occupation())
                .interests(request.interests())
                .tags(request.tags())
                .build();
    }
}
