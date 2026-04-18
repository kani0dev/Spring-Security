package kani.springsecurity.Application.Controller.Response;

import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Tags.Tag;
import lombok.Builder;

import java.util.Set;

@Builder
public record ProfileResponse(
        String bio,
        String location,
        String occupation,
        String interests,
        Set<Tag> tags
) {

    public static ProfileResponse ToEntity(Profile response){
        return ProfileResponse.builder()
                .bio(response.getBio())
                .location(response.getLocation())
                .occupation(response.getOcupation())
                .interests(response.getInterests())
                .tags(response.getTags())
                .build();

    }
}
