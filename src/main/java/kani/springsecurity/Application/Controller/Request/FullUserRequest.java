package kani.springsecurity.Application.Controller.Request;

import kani.springsecurity.Application.Controller.Response.UserResponse;
import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Tags.Tag;
import kani.springsecurity.Domain.Users.Users;
import lombok.Builder;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record FullUserRequest (
        String username,
        String password,

        String bio,
        String location,
        String ocupation,
        String interests,
        Set<String> tags

        ) {

    public static FullUserRequest EmptyExemple (){
        return FullUserRequest.builder().username("").password("").bio("").location("").ocupation("").interests("").tags(Set.of()).build();
    }

    public static Map Build(FullUserRequest request){

        UserRequest userReq = UserRequest.builder().username(request.username()).password(request.password()).build();

        Set<TagRequest> tagReq = request.tags.stream()
                .map(tag -> TagRequest.builder().tag(tag).build())
                .collect(Collectors.toSet());

        ProfileRequest profileReq = ProfileRequest.builder().bio(request.bio()).location(request.location()).occupation(request.ocupation()).interests(request.interests())
                .tags(tagReq)
                .build();

        return  Map.of(
                "user", UserRequest.ToEntity(userReq),

                "profile",  ProfileRequest.ToEntity(profileReq)
                );
    }

}
