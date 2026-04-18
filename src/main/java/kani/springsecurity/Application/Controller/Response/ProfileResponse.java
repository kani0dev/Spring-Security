package kani.springsecurity.Application.Controller.Response;

import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Tags.Tag;
import lombok.Builder;

import java.util.Set;

@Builder
public record ProfileResponse(
        String bio,
        String favoriteAnimal,
        String magicPlace,
        Integer age,
        Set<Tag> tags
) {

    public static ProfileResponse ToEntity(Profile response){
        return ProfileResponse.builder()
                .bio(response.getBio())
                .favoriteAnimal(response.getFavoriteAnimal())
                .magicPlace(response.getMagicPlace())
                .age(response.getAge())
                .tags(response.getTags())
                .build();

    }
}
