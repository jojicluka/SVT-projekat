package com.redditclonesvt.redditclonesvt.Service;

import com.redditclonesvt.redditclonesvt.DTO.LoginDTO;
import com.redditclonesvt.redditclonesvt.DTO.RefreshTokenDTO;
import com.redditclonesvt.redditclonesvt.DTO.TokenDTO;
import com.redditclonesvt.redditclonesvt.Model.User;
import com.redditclonesvt.redditclonesvt.DTO.RegisterDTO;
import com.redditclonesvt.redditclonesvt.Repo.UserRepository;
import com.redditclonesvt.redditclonesvt.Token.JwtUtility;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

import org.modelmapper.ModelMapper;

@Service
public class AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtility jwtUtility;
    @Autowired
    private RefreshTokenService refreshTokenService;

    public void register(RegisterDTO registerDTO){
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setDescription(registerDTO.getDescription());
        LocalDate registrationTime = LocalDate.now();
        user.setRegistrationDate(registrationTime);
        userRepository.save(user);
    }

    public TokenDTO login(LoginDTO loginDTO) throws Exception {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtUtility.generateToken(authenticate);
        System.out.println("Login successful");
        TokenDTO tokencina = TokenDTO.builder()
                .token(token)
                .refresh(refreshTokenService.generateToken().getToken())
                .expiration(Instant.now().plusMillis(jwtUtility.getTokenExpirationTime()))
                .username(loginDTO.getUsername())
                .build();
        System.out.println(tokencina);
        return tokencina;
    }

    public TokenDTO refresh(RefreshTokenDTO refreshTokenDTO) throws Exception {
        refreshTokenService.validateRefreshToken(refreshTokenDTO.getRefreshToken());
        String token = jwtUtility.generateTokenWithUsername(refreshTokenDTO.getUsername());
        return TokenDTO.builder()
                .token(token)
                .refresh(refreshTokenDTO.getRefreshToken())
                .expiration(Instant.now().plusMillis(jwtUtility.getTokenExpirationTime()))
                .username(refreshTokenDTO.getUsername())
                .build();
    }

}
