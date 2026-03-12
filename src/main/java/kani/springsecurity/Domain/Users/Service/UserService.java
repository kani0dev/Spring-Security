package kani.springsecurity.Domain.Users.Service;

import kani.springsecurity.Application.Controller.Request.UserRequest;
import kani.springsecurity.Domain.Users.Model.Users;
import kani.springsecurity.Domain.Users.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public List<Users> findall(){
        return repo.findAll();
    }

    public Users findById(Long id)throws Exception {
        Optional<Users> thisUsers = repo.findById(id);
        if (thisUsers.isPresent()){
            return thisUsers.get();
        }
        throw new Exception();
    }

    public void saveuser(Users request) throws Exception {
        if(repo.findByUsername(request.getUsername()).isPresent()){
            throw new RuntimeException("Usuario Já cadastrado");
        }
        request.setPassword(encoder.encode(request.getPassword()));

        repo.save(request);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetails> byUsername = repo.findByUsername(username);
        if(byUsername.isPresent()){
            var user = byUsername.get();
            return User.withUsername(user
                    .getUsername())
                    .roles("USER")
                    .password(user.getPassword())
                    .build();
        }
        throw  new UsernameNotFoundException("not found user");
    }


}
