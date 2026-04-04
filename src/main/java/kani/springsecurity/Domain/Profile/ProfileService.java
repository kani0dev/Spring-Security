package kani.springsecurity.Domain.Profile;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileRepository repo;

    public ProfileService(ProfileRepository repo) {
        this.repo = repo;
    }

    public Profile findById(long id) throws Exception {
        Optional<Profile> byId = repo.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        throw new Exception("user id profile not found");
    }

    public Profile alterProfile(Long id, Profile request) throws Exception {
        try {
            request.setUserId(id);
            Profile byId = findById(id);
            byId=request;
            return repo.save(byId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteProfile(Long id) throws Exception {
        try{
            findById(id);
            repo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
