package jpa.study.auth.application.domain;

import io.jsonwebtoken.Jwts;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.base64.Base64;
import jpa.study.common.util.TimeUtils;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static jpa.study.common.util.TimeUtils.convertTimeUnit;
import static org.springframework.util.StringUtils.hasText;

@Getter
public class JwtManager {

    private static final String PREFIX = "Bearer ";

    private final SecretKey secretKey;
    private final String issuer;
    private final int accessTokenValidTimeDuration;
    private final int refreshTokenValidTimeDuration;
    private final TimeUnit accessTokenValidTimeUnit;
    private final TimeUnit refreshTokenValidTimeUnit;

    public JwtManager(
            String plainSecretKey,
            String issuer,
            int accessTokenValidTimeDuration,
            int refreshTokenValidTimeDuration,
            TimeUnit accessTokenValidTimeUnit,
            TimeUnit refreshTokenValidTimeUnit
    ) {
        this.secretKey = new SecretKeySpec(
                plainSecretKey.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
        this.issuer = issuer;
        this.accessTokenValidTimeDuration = accessTokenValidTimeDuration;
        this.refreshTokenValidTimeDuration = refreshTokenValidTimeDuration;
        this.accessTokenValidTimeUnit = accessTokenValidTimeUnit;
        this.refreshTokenValidTimeUnit = refreshTokenValidTimeUnit;
    }

    public String createAccessToken(Long userId) {
        Date now = new Date();
        return Jwts.builder()
                .header().type("jwt").and()
                .signWith(secretKey)
                .issuer(issuer)
                .issuedAt(now)
                .expiration(
                        new Date(now.getTime()
                                + convertTimeUnit(accessTokenValidTimeDuration, accessTokenValidTimeUnit, MILLISECONDS))
                )
                .issuedAt(Date.from(Instant.now()))
                .compact();
    }

    public String createRefreshToken(Long userId) {
        Date now = new Date();
        return Jwts.builder()
                .header().type("jwt").and()
                .signWith(secretKey)
                .issuer(issuer)
                .issuedAt(now)
                .subject(String.valueOf(userId))
                .expiration(
                        new Date(now.getTime()
                                + convertTimeUnit(refreshTokenValidTimeDuration, refreshTokenValidTimeUnit, MILLISECONDS))
                )
                .issuedAt(Date.from(Instant.now()))
                .compact();
    }

    public Long readTokenWithPrefix(String token) {
        if (hasText(token) || !token.startsWith(PREFIX)) {
            throw new RuntimeException("유효하지 않는 토큰입니다.");
        }
        token = token.substring(PREFIX.length());

        String subject = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return Long.valueOf(subject);
    }

    public Long readTokenWithoutPrefix(String token) {
        if (hasText(token)) {
            throw new RuntimeException("유효하지 않는 토큰입니다.");
        }

        String subject = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        return Long.valueOf(subject);
    }
}
