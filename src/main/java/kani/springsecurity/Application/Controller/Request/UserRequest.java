package kani.springsecurity.Application.Controller.Request;

import kani.springsecurity.Domain.Users.UserService;
import kani.springsecurity.Domain.Users.Users;
import lombok.Builder;
import org.springframework.security.core.userdetails.User;

@Builder
public record UserRequest(
        String username,
        String password) {
    public static Users ToEntity(
            UserRequest request
    ){
        return Users.builder()
                .username(request.username())
                .password(request.password())
                .build();
    }
}
