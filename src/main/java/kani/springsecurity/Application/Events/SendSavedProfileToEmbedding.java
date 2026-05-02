package kani.springsecurity.Application.Events;

import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import lombok.Builder;

@Builder
public record SendSavedProfileToEmbedding(
        ProfileResponse profileResponse
) {}
