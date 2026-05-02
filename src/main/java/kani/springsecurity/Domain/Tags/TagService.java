package kani.springsecurity.Domain.Tags;

import jakarta.transaction.Transactional;
import kani.springsecurity.Application.Controller.Request.TagRequest;
import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Profile.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final ProfileRepository PFrepo;
    private  final TagRepository repo;

    @Transactional
    public Profile addTagToProfile(Long id, TagRequest request) throws Exception {
        var thisprofile = PFrepo.findById(id);
        if (!thisprofile.isPresent()){throw  new  Exception("Profile not found");}

        Profile profile = thisprofile.get();
        Tag tag = (Tag) TagRequest.ToEntity(request);

       if(profile.getTags().contains(tag)){throw  new Exception("Perfil ja posui Tag");}

        profile.addTagToProfile(tag);

        return  PFrepo.save(profile);
    }

    public List<Tag> getAllTags(){
        return repo.findAll();
    }

    public Tag getTagId(String nome){
        Optional<Tag> byNome = repo.findByNome(nome);

        if (byNome.isPresent()){
            Long tagId = byNome.get().getId();
            return Tag.builder()
                    .id(tagId).nome(nome).build();
        }
        throw new RuntimeException("Tag nao encontrada");
    }

}
