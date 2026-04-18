package kani.springsecurity.Application.Controller.Response;

import kani.springsecurity.Domain.Profile.Profile;
import lombok.Builder;

@Builder
public record UserResponse(
        String username,
        ProfileResponse profile
        ){
}
