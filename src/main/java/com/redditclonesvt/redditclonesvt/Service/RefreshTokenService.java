package com.redditclonesvt.redditclonesvt.Service;

import com.redditclonesvt.redditclonesvt.Model.RefreshToken;
import com.redditclonesvt.redditclonesvt.Repo.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());
        return refreshTokenRepository.save(refreshToken);
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    void validateRefreshToken(String token) throws Exception {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new Exception("Refresh token nije u funkciji!"));
    }
}
