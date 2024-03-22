package com.example.study;

import java.util.Map;

public class NaverMemberInfo implements OAuth2MemberInfo {
    public NaverMemberInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    private Map<String, Object> attributes;

    public String getProviderId() {
        return (String) attributes.get("sub");
    }

    public String getProvider() {
        return "naver";
    }

    public String getName() {
        return (String) attributes.get("name");
    }

    public String getEmail() {
        return (String) attributes.get("email");
    }
}
