package com.junyharang.jwtstudy.auth;

/*
Login 처리를 위한 Class
 */

import com.junyharang.jwtstudy.model.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {

    private Member member;

    public PrincipalDetails(Member member) {
        this.member = member;
    } // 생성자

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        member.getRoleList().forEach(memberRole -> {
            authorities.add(() -> memberRole);
        });

        return authorities;
    } // getAuthorities() 끝

    @Override
    public String getPassword() {
        return member.getPassword();
    } // getPassword() 끝

    @Override
    public String getUsername() {
        return member.getUsername();
    } // getUsername() 끝

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // isAccountNonExpired() 끝

    @Override
    public boolean isAccountNonLocked() {
        return true;
    } // isAccountNonLocked() 끝

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // isCredentialsNonExpired() 끝

    @Override
    public boolean isEnabled() {
        return true;
    } // isEnabled() 끝
} // class 끝
