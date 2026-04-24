package kani.springsecurity.Application.Controller;

import kani.springsecurity.Application.Controller.Request.ProfileRequest;
import kani.springsecurity.Application.Controller.Request.UserRequest;
import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import kani.springsecurity.Application.Controller.Response.UserResponse;
import kani.springsecurity.Application.Mapper.UserMapper;
import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Tags.TagService;
import kani.springsecurity.Domain.Users.Users;
import kani.springsecurity.Domain.Profile.ProfileService;
import kani.springsecurity.Domain.Users.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserMapper mapper;
    private  final ProfileService PfService;
    private final TagService TAGservice;

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getall(){
        List<UserResponse> findall = service.findall().stream().map(mapper::ToResponse).toList();
        return ResponseEntity.ok(findall);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getbyid(@PathVariable Long id) throws Exception {
        try{
            UserResponse reponse = mapper.ToResponse(service.findById(id));
            return ResponseEntity.ok(reponse);
        }catch (Exception e){
            return  ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Void> postuser(@RequestBody  UserRequest users) throws Exception {
        service.saveuser(mapper.ToEntity(users));
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
