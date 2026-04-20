package kani.springsecurity.Domain.Tags;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository repo;

    public Map<String, List<String>> getTagsByCategorie() {
        return repo.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Tag::getCategory,
                        LinkedHashMap::new,
                        Collectors.mapping(Tag::getNome, Collectors.toList())
                ));
    }

}
