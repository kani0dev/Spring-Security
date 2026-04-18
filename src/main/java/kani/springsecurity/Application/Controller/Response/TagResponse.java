package kani.springsecurity.Application.Controller.Response;

import kani.springsecurity.Domain.Tags.Tag;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder
public record TagResponse(String tag) {

    public static Tag toEntity(Set<String> tags){
        return Tag.builder().nome(tags.stream().toString()).build();
    }
}
