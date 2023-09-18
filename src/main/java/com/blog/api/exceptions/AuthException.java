package com.blog.api.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthException extends RuntimeException {

    private String sms;
    public AuthException(String sms) {
        super(sms);
    }
}
