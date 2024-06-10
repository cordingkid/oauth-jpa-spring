package jpa.study.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    REQUIRED_ACCESS_TOKEN(UNAUTHORIZED, "인증 정보가 필요합니다."),
    REQUIRED_REFRESH_TOKEN(UNAUTHORIZED, "리프레시 토큰이 포함되지 않았습니다."),
    INVALID_ACCESS_TOKEN(UNAUTHORIZED, "유효하지 않은 AccessToken 입니다. 다시 로그인 해주세요."),
    INVALID_ACCESS(FORBIDDEN, "접근 권한이 존재하지 않습니다."),
    DELETED_USER_EXCEPTION(BAD_REQUEST, "탈퇴한 회원입니다."),
    RESOURCE_NOT_FOUND(NOT_FOUND, "존재하지 않습니다."),
    ALREADY_LOGOUT_EXCEPTION(BAD_REQUEST, "이미 로그아웃 처리가 된 상태입니다."),

    LENGTH_EXCEEDED_EXCEPTION(BAD_REQUEST, "최대 글자 길이를 초과하였습니다."),
    ;

    private final HttpStatus status;
    private final String detail;
}
