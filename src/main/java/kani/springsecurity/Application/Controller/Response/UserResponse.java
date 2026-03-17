package kani.springsecurity.Application.Controller.Response;

import lombok.Builder;

@Builder
public record UserResponse(
        String username,
        Long id,
        String bio,
        String favorite_animal,
        String magic_place,
        Integer age) {
}
