package com.redditclonesvt.redditclonesvt.Service;

import com.redditclonesvt.redditclonesvt.DTO.PasswordDTO;
import com.redditclonesvt.redditclonesvt.Model.User;
import com.redditclonesvt.redditclonesvt.Repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User changePassword(PasswordDTO passwordDTO, String username){
        User user = userRepository.findByUsername(username).orElseGet(null);
        String password = passwordEncoder.encode(passwordDTO.changedPassword);

        user.setPassword(password);
        userRepository.save(user);

        return user;
    }
}
