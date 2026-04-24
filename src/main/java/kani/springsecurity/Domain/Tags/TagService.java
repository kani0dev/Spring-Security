package kani.springsecurity.Domain.Tags;

import jakarta.transaction.Transactional;
import kani.springsecurity.Application.Controller.Request.TagRequest;
import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Profile.ProfileRepository;
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
    private final ProfileRepository PFrepo;

    public Map<String, List<String>> getTagsByCategorie() {
        return repo.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        Tag::getCategory,
                        LinkedHashMap::new,
                        Collectors.mapping(Tag::getNome, Collectors.toList())
                ));
    }

    @Transactional
    public Profile addTagToProfile(Long id, TagRequest request) throws Exception {
        var thisprofile = PFrepo.findById(id);
        if (!thisprofile.isPresent()){throw  new  Exception("Profile not found");}

        Profile profile = thisprofile.get();

        var istagpresent = repo.findByCategoryAndNomeContaining(request.category(), request.tag());
        if (istagpresent.isEmpty()){throw new Exception("Tag not found in profile");}


        Tag tag = istagpresent.getFirst();

       if(profile.getTags().contains(tag)){throw  new Exception("Perfil ja posui Tag");}

        profile.addTagToProfile(tag);

        return  PFrepo.save(profile);
    }

}
