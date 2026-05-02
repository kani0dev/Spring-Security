package kani.springsecurity.Domain.Users;

import jakarta.transaction.Transactional;
import kani.springsecurity.Application.Controller.Request.UserRequest;
import kani.springsecurity.Application.Controller.Response.ProfileResponse;
import kani.springsecurity.Application.Events.SendSavedProfileToEmbedding;
import kani.springsecurity.Application.Exceptions.AlreadyExist;
import lombok.RequiredArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final ApplicationEventPublisher publisher;

    public List<Users> findall(){
        return repo.findAll();
    }

    public Users findById(Long id)throws RuntimeException {
        Optional<Users> thisUsers = repo.findById(id);
        if (thisUsers.isPresent()){
            return thisUsers.get();
        }
        throw  new RuntimeException("Id nao econtrado");
    }

    public Users saveuser(Users request) {
        Optional<Users> isUserPresent = repo.findByUsername(request.getUsername());
        if(isUserPresent.isPresent()){
            throw new AlreadyExist("user with this username already in exists");
        }
        request.setPassword(encoder.encode(request.getPassword()));
        request.setRole(Role.USER);

        Users saved = repo.save(request);
        System.out.println("perfil pra embeding: "+ saved.thisuserprofile.toString());
        publisher.publishEvent(
                SendSavedProfileToEmbedding.builder()
                        .profileResponse(ProfileResponse.ToResponse(saved.thisuserprofile))
                        .build()
        );
        return request;
    }

    @Override
    public UserDetails loadUserByUsername( String username) throws UsernameNotFoundException {
        Optional<Users> byUsername = repo.findByUsername(username);
        if(byUsername.isPresent()){
            var user = byUsername.get();
            return User.withUsername(
                        user.getUsername()
                    )
                    .roles(user.getRole().name())
                    .password(user.getPassword())
                    .build();
        }
        throw  new UsernameNotFoundException("not found user");
    }


    @Transactional
    public Users alterUser(Long id, UserRequest request) throws RuntimeException {
        try{
            var oldUser = findById(id);
            Users newUser = Users.builder()
                    .id(oldUser.getId())
                    .password(encoder.encode(request.password()))
                    .username(request.username())
                    .role(oldUser.getRole())
                    .thisuserprofile(oldUser.getThisuserprofile())
                    .build();

            return repo.save(newUser);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void deleteUser(Long id) throws Exception{
        try {
            findById(id);
            repo.deleteById(id);
        }catch (Exception e ){
            throw new Exception("usuario nao encontrado");
        }
    }

}
