package com.croquis.documentapproval.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Date;

@Getter
public class GeneratedToken {

    private String grantType;
    private String accessToken;
    private Long accessTokenExpiresIn;
    private String refreshToken;

    @Builder
    public GeneratedToken(String grantType, String accessToken, Long accessTokenExpiresIn, String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
        this.refreshToken = refreshToken;
    }

}
