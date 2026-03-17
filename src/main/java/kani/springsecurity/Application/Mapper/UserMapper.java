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
               .bio(requestT.bio())
               .favorite_animal(requestT.favorite_animal())
               .magic_place(requestT.magic_place())
               .age(requestT.age())
               .build();
    }
    public Users ToEntity(UserResponse response){
        return Users.builder()
                .id(response.id())
                .username(response.username())
                .bio(response.bio())
                .favorite_animal(response.favorite_animal())
                .magic_place(response.magic_place())
                .age(response.age())
                .build();
    }

    public UserResponse ToResponse(Users entity){
        return UserResponse.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .bio(entity.getBio())
                .favorite_animal(entity.getFavorite_animal())
                .magic_place(entity.getMagic_place())
                .age(entity.getAge())
                .build();
    }

}
