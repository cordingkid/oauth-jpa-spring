package jpa.study.auth.application.domain;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.base64.Base64;
import lombok.Getter;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static java.nio.charset.StandardCharsets.*;

@Getter
public class JwtManager {

    private static final String PREFIX = "Bearer ";
//    private final SecretKey secretKey;
    private final String issuer;
    private final int accessTokenValidTimeDuration;
    private final TimeUnit accessTokenValidTimeUnit;

    public JwtManager(
            String plainSecretKey,
            String issuer,
            int accessTokenValidTimeDuration,
            TimeUnit accessTokenValidTimeUnit
    ) {
//        this.secretKey = secretKey;
        this.issuer = issuer;
        this.accessTokenValidTimeDuration = accessTokenValidTimeDuration;
        this.accessTokenValidTimeUnit = accessTokenValidTimeUnit;
    }
}
