package org.depromeet.spot.application.common.jwt;

import java.util.Collection;
import java.util.List;

import org.depromeet.spot.domain.member.enums.MemberRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtToken implements Authentication {
    // TODO : Authentication을 상속받고 UserDetail을 상속받은 커스텀 유저 정보 객체 생성해줘야함.
    private String memberId;
    private MemberRole memberRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}

    @Override
    public String getName() {
        return "";
    }
}
