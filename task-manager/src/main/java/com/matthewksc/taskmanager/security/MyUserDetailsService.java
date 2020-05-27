package com.matthewksc.taskmanager.security;

import com.matthewksc.taskmanager.dao.UserRepository;
import com.matthewksc.taskmanager.dao.entity.MyUserDetails;
import com.matthewksc.taskmanager.dao.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        user.orElseThrow(()-> new RuntimeException("No such user with username: "+ username+" in database"));
        return user.map(MyUserDetails::new).get();
    }
}
