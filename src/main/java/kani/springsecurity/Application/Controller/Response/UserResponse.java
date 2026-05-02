package kani.springsecurity.Application.Controller.Response;

import kani.springsecurity.Domain.Profile.Profile;
import kani.springsecurity.Domain.Users.Users;
import lombok.Builder;
import org.springframework.security.core.userdetails.User;

@Builder
public record UserResponse(
        String username,
        ProfileResponse profile
        ){
        public static UserResponse ToResponse(Users user){
                return  UserResponse.builder()
                        .username(user.getUsername())
                        .profile(ProfileResponse.ToResponse(user.getThisuserprofile()))
                        .build();
        }

}
