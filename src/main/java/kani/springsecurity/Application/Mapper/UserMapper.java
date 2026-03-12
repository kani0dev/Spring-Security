package kani.springsecurity.Application.Mapper;

import jakarta.persistence.GeneratedValue;
import kani.springsecurity.Application.Controller.Request.UserRequest;
import kani.springsecurity.Application.Controller.Response.UserResponse;
import kani.springsecurity.Domain.Users.Model.Users;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Builder
@Component
public class UserMapper{
    public Users ToEntity(UserRequest requestT){
       return  Users.builder()
               .username(requestT.username())
                .password(requestT.password())
                .build();
    }
    public Users ToEntity(UserResponse response){
        return Users.builder()
                .id(response.id())
                .username(response.username())
                .build();
    }

    public UserResponse ToResponse(Users entity){
        return UserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .build();
    }

}
