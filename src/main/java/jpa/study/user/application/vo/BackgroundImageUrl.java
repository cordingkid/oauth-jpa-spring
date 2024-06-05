package jpa.study.user.application.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jpa.study.common.exception.CustomException;
import jpa.study.common.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jpa.study.common.exception.ErrorCode.LENGTH_EXCEEDED_EXCEPTION;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = PROTECTED, force = true)
public class BackgroundImageUrl {

    private static final int LENGTH_LIMIT = 2048;

    @Column(name = "background_image_url", nullable = false, length = LENGTH_LIMIT)
    private final String value;

    public BackgroundImageUrl(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value.length() > LENGTH_LIMIT) {
            throw new CustomException(LENGTH_EXCEEDED_EXCEPTION, "배경 이미지 URL은 " + LENGTH_LIMIT + "를 넘을 수 없습니다.");
        }
    }
}
