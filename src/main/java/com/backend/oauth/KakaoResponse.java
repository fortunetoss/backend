package com.backend.oauth;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class KakaoResponse implements OAuth2Response {

    private final Map<String, Object> attribute;
    private Map<String, Object> kakaoAccount;
    private Map<String, Object> profile;

    public KakaoResponse(Map<String, Object> attribute) {
        kakaoAccount = (Map<String, Object>) attribute.get("kakao_account");
        profile = (Map<String, Object>) kakaoAccount.get("profile");
        this.attribute = attribute;
    }

    @Override
    public String getProvider() {
        return "kakao";
    }

    @Override
    public String getProviderId() {
        return attribute.get("id").toString();
    }

    @Override
    public String getEmail() {
        return kakaoAccount.get("email").toString();
    }

    @Override
    public String getName() {
        return profile.get("nickname").toString();
    }

    @Override
    public String getImageUrl() {
        return profile.get("profile_image_url").toString();
    }
}
