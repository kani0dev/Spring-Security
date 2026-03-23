package kani.springsecurity.Application.Controller;

import kani.springsecurity.Application.Controller.Request.UserRequest;
import kani.springsecurity.Application.Controller.Response.UserResponse;
import kani.springsecurity.Application.Mapper.UserMapper;
import kani.springsecurity.Domain.Users.Model.Users;
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
    public ResponseEntity<List<UserResponse>> getall(){
        List<UserResponse> findall = service.findall().stream().map(mapper::ToResponse).toList();
        return ResponseEntity.ok(findall);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getbyid(@PathVariable Long id) throws Exception {
        UserResponse reponse = mapper.ToResponse(service.findById(id));
        return ResponseEntity.ok(reponse);
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
            throw new RuntimeException(e);
        }
    }
}
