package kani.springsecurity.Application.Controller.Request;

import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Tags.Tag;
import kani.springsecurity.Domain.Users.UserRepository;
import kani.springsecurity.Domain.Users.Users;
import lombok.Builder;

import java.util.Set;
import java.util.stream.Collectors;


@Builder
public record ProfileRequest(
        String bio,
        String location,
        String occupation,
        String interests,
        Set<TagRequest> tags
        ) {
    public static Profile ToEntity(ProfileRequest request){

        Set<Tag> requestTags = request.tags.stream().map(TagRequest::ToEntity).collect(Collectors.toSet());
        return Profile.builder()
                .bio(request.bio())
                .location(request.location())
                .ocupation(request.occupation())
                .tags(requestTags)
                .interests(request.interests())
                .build();
    }
}
