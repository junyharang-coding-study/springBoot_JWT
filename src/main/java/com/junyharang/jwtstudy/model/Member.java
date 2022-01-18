package com.junyharang.jwtstudy.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity @Data // Setter가 들어 있는 @Data는 Entity에서 사용하면 안된다.
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String username;
    private String password;

    // USER, ADMIN
    private String rolse;

    public List<String> getRoleList() {
        if (this.rolse.length() > 0) {  // rolse Filed의 값이 0보다 크다면?
            // Array List를 만들어 문자열을 콤마로 구분하여 반환하여 준다.
            return Arrays.asList(this.rolse.split(","));
        } // if문 끝

        // rolse의 값이 0보다 작다면 Array List만 생성해서 반환한다.
        return new ArrayList<>();
    } // getRoleList() 끝
} // class 끝
