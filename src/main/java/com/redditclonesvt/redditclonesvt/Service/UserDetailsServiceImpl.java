package com.redditclonesvt.redditclonesvt.Service;

import com.redditclonesvt.redditclonesvt.Model.User;
import com.redditclonesvt.redditclonesvt.Repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        User user = optionalUser.orElseThrow(()-> new UsernameNotFoundException("Korisnik nije pronadjen!"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), fetchAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> fetchAuthorities(String role){
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
