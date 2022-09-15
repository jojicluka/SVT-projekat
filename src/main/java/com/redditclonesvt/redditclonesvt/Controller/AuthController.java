package com.redditclonesvt.redditclonesvt.Controller;

import com.redditclonesvt.redditclonesvt.DTO.*;
import com.redditclonesvt.redditclonesvt.Model.User;
import com.redditclonesvt.redditclonesvt.Service.AuthService;
import com.redditclonesvt.redditclonesvt.Service.RefreshTokenService;
import com.redditclonesvt.redditclonesvt.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO){
        authService.register(registerDTO);
        return new ResponseEntity<>("Uspesna registracija", HttpStatus.OK);
    }

    @PostMapping("/login")
    public TokenDTO login(@RequestBody LoginDTO loginDTO) throws Exception {
        return authService.login(loginDTO);
    }

    @PostMapping("refresh/token")
    public TokenDTO refresh(@RequestBody RefreshTokenDTO refreshTokenDTO) throws Exception {
        return authService.refresh(refreshTokenDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody RefreshTokenDTO refreshTokenDTO){
        refreshTokenService.deleteRefreshToken(refreshTokenDTO.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Logout uspesan!");
    }

    @PutMapping("/password/{username}")
    public ResponseEntity<User> changePassword(@PathVariable("username") String username, @RequestBody PasswordDTO passwordDTO){
        User user = userService.changePassword(passwordDTO, username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
