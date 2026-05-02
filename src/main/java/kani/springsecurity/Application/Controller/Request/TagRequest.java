package kani.springsecurity.Application.Controller.Request;

import kani.springsecurity.Domain.Tags.Tag;
import lombok.Builder;

import java.util.Set;
@Builder
public record TagRequest(
        String tag
) {
        public static Tag ToEntity(TagRequest request) {
            return Tag.builder().nome(request.tag()).build();
        }
}
