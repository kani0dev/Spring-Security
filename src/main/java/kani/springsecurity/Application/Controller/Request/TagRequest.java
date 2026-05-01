package kani.springsecurity.Application.Controller.Request;

import kani.springsecurity.Domain.Tags.Tag;

public record TagRequest(
        String category,
        String tag
) {
    public static Tag ToEntity(TagRequest request){

    }
}
