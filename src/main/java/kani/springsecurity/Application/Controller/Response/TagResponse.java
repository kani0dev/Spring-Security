package kani.springsecurity.Application.Controller.Response;

import kani.springsecurity.Domain.Tags.Tag;
import lombok.Builder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record TagResponse(
        String tag,
        String category
) {

}
