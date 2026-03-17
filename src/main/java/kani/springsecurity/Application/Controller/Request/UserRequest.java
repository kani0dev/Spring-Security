package kani.springsecurity.Application.Controller.Request;

import lombok.Builder;

@Builder
public record UserRequest(
        String username,
        String password,
        String bio,
        String favorite_animal,
        String magic_place,
        Integer age
) {
}
