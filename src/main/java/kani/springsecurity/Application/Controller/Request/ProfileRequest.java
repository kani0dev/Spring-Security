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
    public static UserRepository repo ;

    public static Profile ToEntity(ProfileRequest request){
        Set<Tag> processedTags = request.tags().stream().map(TagRequest::ToEntity).collect(Collectors.toSet());
        System.out.println(processedTags);
        return Profile.builder()
                .bio(request.bio())
                .location(request.location())
                .ocupation(request.occupation())
                .interests(request.interests())
                .tags(processedTags)
                .build();
    }
    public static Profile ToEntityWithUser(ProfileRequest request, Users user){
        return Profile.builder()
                .user(user)
                .userId(user.getId())
                .bio(request.bio())
                .location(request.location())
                .ocupation(request.occupation())
                .interests(request.interests())
                .tags(
                        request.tags().stream()
                                .map(TagRequest::ToEntity).collect(Collectors.toSet())
                )
                .build();
    }
}
