package jpa.study.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class AuditorConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
        // auditor 테스트 나중에 사용자 정보 넣어줘야함
        return () -> Optional.of(UUID.randomUUID().toString());
    }
}
