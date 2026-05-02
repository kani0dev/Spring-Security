package kani.springsecurity.Application.Controller;

import jakarta.transaction.Transactional;
import kani.springsecurity.Application.Controller.Request.FullUserRequest;
import kani.springsecurity.Application.Controller.Request.ProfileRequest;
import kani.springsecurity.Application.Controller.Request.UserRequest;
import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import kani.springsecurity.Application.Controller.Response.UserResponse;
import kani.springsecurity.Application.Exceptions.AlreadyExist;
import kani.springsecurity.Application.Exceptions.EmptyProfile;
import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Tags.Tag;
import kani.springsecurity.Domain.Tags.TagService;
import kani.springsecurity.Domain.Users.Users;
import kani.springsecurity.Domain.Profile.ProfileService;
import kani.springsecurity.Domain.Users.UserService;
import lombok.RequiredArgsConstructor;

import org.apache.coyote.BadRequestException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private  final ProfileService PfService;

    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getall(){
        List<UserResponse> findall = service.findall().stream().map(UserResponse::ToResponse).toList();
        return ResponseEntity.ok(findall);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getbyid(@PathVariable Long id) {
        try{
            UserResponse reponse = UserResponse.ToResponse(service.findById(id));
            return ResponseEntity.ok(reponse);
        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }
    }

    private final TagService Tservice;
    @PostMapping("")
    @Transactional
    public ResponseEntity createFullUser(@RequestBody FullUserRequest request) {
        try {
            var built = FullUserRequest.Build(request);
            var tags = request.tags().stream().map(Tservice::getTagId).collect(Collectors.toSet());
            var user  = (Users)   built.get("user");
            var profile = (Profile) built.get("profile");

            profile.setUser(user);
            profile.setUserId(user.getId());
            profile.setTags(tags);
            user.setThisuserprofile(profile);
            Users saved = service.saveuser(user);

            return ResponseEntity.ok(saved);

        } catch (AlreadyExist e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        } catch (EmptyProfile e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Empty profile try re-writing the profile\n"
            );
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Request Body is not right heres a exemple"
            );
        }
    }
    /*
        user can be created as a detached user that won't have a profile, I think this type of profile can be used for
        users that are executors like admin,manager and etc., where a profile is not needed.
    */
    @PostMapping("detached/")
    public ResponseEntity<Void> postuser(@RequestBody  UserRequest users) throws Exception {
        service.saveuser(UserRequest.ToEntity(users));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public  ResponseEntity<UserResponse> edituser(@RequestBody UserRequest request,@PathVariable Long id){
        try{
            Users user = service.alterUser(id, request);
            return ResponseEntity.ok(UserResponse.ToResponse(user));
        }  catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id, Authentication auth) throws Exception {
        service.deleteUser(id);
        PfService.deleteProfile(id);
        return ResponseEntity.ok().build();
    }

    // User Profile operations -------------------------------------
    // the profile is iniciated at the moment that a user is created.



    @PutMapping("/profile/{id}")
    public ResponseEntity<ProfileResponse> PutUserPRofile(@PathVariable Long id, @RequestBody ProfileRequest request) throws Exception {
        try{
            Profile profile = PfService.alterProfile(id, request);
            return ResponseEntity.ok(ProfileResponse.ToResponse(profile));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<ProfileResponse> getProfileByid(@PathVariable Long id){
        try{
            Profile byId = PfService.findById(id);
            ProfileResponse profileResponse = ProfileResponse.ToResponse(byId);

            return ResponseEntity.ok(profileResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
