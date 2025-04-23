package com.base.users.service;

import com.base.users.model.BaseUser;
import org.springframework.security.core.userdetails.User;
import com.base.users.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BaseUser baseUser = userRepository.findByEmail(email).
                orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));


        return new User(
              baseUser.getEmail(),
                baseUser.getPassword(),
                Collections.emptyList()
        );
    }

    public BaseUser save(BaseUser user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }
}
