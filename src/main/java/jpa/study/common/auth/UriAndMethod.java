package jpa.study.common.auth;

import org.springframework.http.HttpMethod;

public record UriAndMethod(
        String uri,
        HttpMethod method
) {

    public boolean isMatch(String mappingUrl, HttpMethod method) {
        return uri.equals(mappingUrl) && this.method.equals(method);
    }
}
