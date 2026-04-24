package kani.springsecurity.Application.Controller.Response;

import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Tags.Tag;
import lombok.Builder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record ProfileResponse(
        String bio,
        String location,
        String occupation,
        String interests,
        Map<String, List<String>> tags
) {
    public static ProfileResponse ToResponse(Profile response){

        return ProfileResponse.builder()
                .bio(response.getBio())
                .location(response.getLocation())
                .occupation(response.getOcupation())
                .interests(response.getInterests())
                .tags(response.getTags().stream().collect(Collectors.groupingBy(
                        Tag::getCategory,
                        LinkedHashMap::new,
                        Collectors.mapping(Tag::getNome, Collectors.toList())
                )))
                .build();

    }
}
