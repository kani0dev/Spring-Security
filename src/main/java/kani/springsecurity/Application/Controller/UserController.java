package kani.springsecurity.Application.Controller;

import kani.springsecurity.Application.Controller.Request.UserRequest;
import kani.springsecurity.Application.Controller.Response.UserResponse;
import kani.springsecurity.Application.Mapper.UserMapper;
import kani.springsecurity.Domain.Users.Model.Profile;
import kani.springsecurity.Domain.Users.Model.Users;
import kani.springsecurity.Domain.Users.Service.ProfileService;
import kani.springsecurity.Domain.Users.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @GetMapping("/")
    /*
    public ResponseEntity<List<UserResponse>> getall(){
        List<UserResponse> findall = service.findall().stream().map(mapper::ToResponse).toList();
        return ResponseEntity.ok(findall);
    }
    */
    public ResponseEntity<List<Users>> getall(){

        List<Users> findall = service.findall();
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
            Users user = mapper.ToEntity(request);
            Users users = service.alterUser(id, user);
            return ResponseEntity.ok(mapper.ToResponse(users));
        }  catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUser(@PathVariable Long id) throws Exception {
        /*
        service.deleteUser(id);
        PfService.deleteProfile(id);
        */
        System.out.println("funciona plmds");
        return ResponseEntity.ok().build();
    }

    // User Profile operations
    private  final ProfileService PfService;

    @GetMapping("/profile/{id}")
    public ResponseEntity<Profile> GetUserProfile(@PathVariable Long id) throws Exception {
        try{
            Profile byId = PfService.findById(id);
            return ResponseEntity.ok(byId);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<Profile> PutUserPRofile(@PathVariable Long id, @RequestBody Profile request) throws Exception {
        return ResponseEntity.ok(PfService.alterProfile(id,request));
    }
}
